package com.tekken.template.impl;

import com.tekken.support.Logs;
import org.apache.commons.io.FilenameUtils;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

public abstract class FileUtils {

    ClassLoader classLoader = FileUtils.class.getClassLoader();

    public boolean hasExtension(String filename, String extension){
        return (FilenameUtils.getExtension(filename).equalsIgnoreCase(extension));
    }

    public String readToString(File file) {
        String returnString = "";
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            String line;
            while ((line = bufferedReader.readLine()) != null)
                returnString += line;
        } catch (Exception e){
            Logs.error("Cannot read file "+file.getName(), e);
        }
        return returnString;
    }

    public String getPathRessource(String folder){
        try {
            return URLDecoder.decode(classLoader.getResource(folder).getPath(), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<URL> getBackendsURL(File dir){
        ArrayList<URL> list = new ArrayList<URL>();
        for (File file : dir.listFiles()) {
            try {
                list.add(file.toURI().toURL());
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }
        return list;
    }

    public byte[] readClass(String url) throws IOException {

        InputStream input = new URL(url).openConnection().getInputStream();
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        int data = input.read();

        while (data != -1) {
            buffer.write(data);
            data = input.read();
        }

        input.close();

        return buffer.toByteArray();

    }


}
