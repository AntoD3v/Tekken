package com.tekken.template.impl;

import com.tekken.support.Logs;
import org.apache.commons.io.FilenameUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

public interface FileUtils {

    ClassLoader classLoader = FileUtils.class.getClassLoader();

    default boolean hasExtension(String filename, String extension){
        return (FilenameUtils.getExtension(filename).equalsIgnoreCase(extension));
    }

    default String readToString(File file) {
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

    default String getPathRessource(String folder){
        try {
            return URLDecoder.decode(classLoader.getResource(folder).getPath(), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

}
