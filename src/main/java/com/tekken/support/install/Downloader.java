package com.tekken.support.install;

import com.tekken.Option;
import com.tekken.support.Logs;

import java.io.File;
import java.util.Arrays;
import java.util.List;

public class Downloader {

    private List<String> folders = Arrays.asList("templates", "backends", "modules", "config");
    private final ClassLoader classLoader = getClass().getClassLoader();
    private FileUtil fileUtil = new FileUtil();

    public Downloader() {
        Logs.info("Tekken is going be installed. Please wait 2 minuts ...");
        createFolder();
        Option.createConfig();
    }

    private void createFolder() {
        for (String folder : folders)
            new File(folder).mkdir();
    }
}
