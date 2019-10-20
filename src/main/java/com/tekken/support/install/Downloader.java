package com.tekken.support.install;

import com.tekken.support.Logs;
import sun.rmi.runtime.Log;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Downloader {

    private List<String> folders = Arrays.asList("templates", "modules", "config");
    private final ClassLoader classLoader = getClass().getClassLoader();
    private FileUtil fileUtil = new FileUtil();

    public Downloader() {
        Logs.info("Tekken is going be install. Please wait 2 minuts ...");
        createFolder();
    }

    private void createFolder() {
        for (String folder : folders)
            new File(folder).mkdir();
    }
}
