package com.tekken.template;

import java.io.*;
import java.net.URL;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

public class TemplateEngine {

    private final String root;
    private final ClassLoader classLoader = getClass().getClassLoader();
    private final Map<String, String> templateFilesCache = new HashMap<>();

    public TemplateEngine(String root) {
        this.root = root;
        this.scanRoot();
    }

    private void scanRoot() {

        System.out.println("Update temlate file cache ...");
        int i = getFolderFile(root);
        System.out.println("Update finish ! "+i+" file(s) up to date ");

    }

    private int getFolderFile(String directory) {
        int updateInt = 0;
        File directoryFile = getRessourceFile(directory);
        if (directoryFile != null && directoryFile.exists()) {
            for (File file : directoryFile.listFiles()) {
                if (file.isDirectory()) {
                    getFolderFile(file.getPath());
                    continue;
                }
                getTemplateFilesCache().put(file.getName(), readToString(file));
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
        URL resource = classLoader.getResource(file);
        try {
            String path = URLDecoder.decode(resource.getPath(), "UTF-8");
            if (path != null)
                return new File(path);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        throw new IllegalArgumentException("Template is not found !");

    }

    public Map<String, String> getTemplateFilesCache() {
        return templateFilesCache;
    }
}
