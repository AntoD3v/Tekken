package com.tekken.template;

import com.tekken.support.Logs;

import java.io.*;
import java.net.URLDecoder;

public class TemplateUpdater extends Thread {

    private final int delay;
    private final String path;
    private final TemplateEngine templateEngine;

    private final ClassLoader classLoader = getClass().getClassLoader();

    public TemplateUpdater(int delay, String root, TemplateEngine templateEngine) throws UnsupportedEncodingException {
        this.delay = delay;
        this.templateEngine = templateEngine;
        path = URLDecoder.decode(classLoader.getResource(root).getPath(), "UTF-8");
    }

    @Override
    public void run() {

        while (Thread.interrupted() != true) {

            try {
                Thread.sleep(delay);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if (path != null) {
                updater();
                continue;
            }
            throw new IllegalArgumentException("Template absent !");

        }
    }
    public void updater() {
        int i = getFolderFile(templateEngine.getRoot());
        if(i != 0)
            Logs.info("Update finish ! "+i+" file(s) up to date ");

    }

    private int getFolderFile(String directory) {
        int updateInt = 0;
        TemplateCache cache = templateEngine.getTemplateCache();
        File directoryFile = getRessourceFile(directory);
        if (directoryFile != null && directoryFile.exists()) {
            for (File file : directoryFile.listFiles()) {
                if (file.isDirectory()) {
                    getFolderFile(file.getPath());
                    continue;
                }
                if(cache.getTemplateFilesCache().containsKey(file.getName())){
                    TemplateFile templateFile = cache.getTemplateFilesCache().get(file.getName());
                    if(templateFile.getLastModified() == file.lastModified()){
                        continue;
                    }else {
                        templateFile.setHtmlCode(readToString(file));
                        templateFile.setLastModified(file.lastModified());
                        templateEngine.getTemplateCache().updateFileCache(file.getName(), templateFile);
                    }
                }else
                    templateEngine.getTemplateCache().updateFileCache(file.getName(), new TemplateFile(file, file.getName(), readToString(file)));
                updateInt++;
            }
            return updateInt;
        }
        return 0;
    }

    private String readToString(File file) {
        String returnString = "";
        try {
            FileReader reader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(reader);

            String line;
            while ((line = bufferedReader.readLine()) != null) {
                returnString += line;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return returnString;
    }

    private File getRessourceFile(String file){
        if (path != null)
            return new File(path);
        throw new IllegalArgumentException("Template is not found !");

    }


}
