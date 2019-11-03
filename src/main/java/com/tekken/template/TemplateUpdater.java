package com.tekken.template;

import com.tekken.Option;
import com.tekken.support.Logs;
import com.tekken.template.build.Builder;
import com.tekken.template.impl.FileUtils;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

public class TemplateUpdater extends FileUtils implements Runnable {

    private String path;
    private final TemplateEngine templateEngine;

    private final ClassLoader classLoader = getClass().getClassLoader();

    public TemplateUpdater(String root, TemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
        try {
            path = URLDecoder.decode(classLoader.getResource(root).getPath(), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        new Thread(this).start();
    }

    @Override
    public void run() {

        while (Thread.interrupted() != true) {

            if(path == null)
                throw new IllegalArgumentException("Template absent !");

            updater();
            try { Thread.sleep(Option.TEMPLATE_UPDATER_DELAY); } catch (InterruptedException e) { }

        }
    }
    public void updater() {
        int i = getFolderFile(getRessourceFile(path));
        if(i != 0)
            Logs.info("Update finish ! "+i+" file(s) up to date ");

    }

    private int getFolderFile(File directoryFile) {
        int updateInt = 0;
        TemplateCache cache = templateEngine.getTemplateCache();
        if (directoryFile != null && directoryFile.exists()) {
            for (File file : directoryFile.listFiles()) {
                if (file.isDirectory()) {
                    updateInt += getFolderFile(file);
                    continue;
                }
                if(!hasExtension(file.getName(), "html"))
                    continue;
                if(cache.getCache().containsKey(file.getName())){
                    TemplatePage templatePage = cache.getCache().get(file.getName());
                    if(templatePage.getLastModified() == file.lastModified())
                        continue;
                    cache.getCache().remove(file.getName());
                }

                templateEngine.getTemplateCache().updateFileCache(file.getName(), new Builder(file.getName(), readToString(file), file.lastModified()).build());
                updateInt++;
            }
            return updateInt;
        }
        return 0;
    }

    private File getRessourceFile(String path){
        if (path != null)
            return new File(path);
        throw new IllegalArgumentException("Template is not found !");

    }


}
