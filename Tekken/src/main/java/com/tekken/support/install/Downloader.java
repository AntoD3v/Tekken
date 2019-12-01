package com.tekken.support.install;

import com.tekken.Option;
import com.tekken.support.Logs;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import static com.tekken.Option.TEKKEN_FOLDER;

public class Downloader {

    private List<String> folders = Arrays.asList("templates", "backends", "modules", "design");
    private final ClassLoader classLoader = getClass().getClassLoader();
    private FileUtil fileUtil = new FileUtil();

    public Downloader() {
        Logs.info("Tekken is going be installed. Please wait 2 minutes ...");
        createFolder();
        Option.createConfig();
    }

    private void createFolder() {
        new File(TEKKEN_FOLDER).mkdir();
        for (String folder : folders)
            new File(TEKKEN_FOLDER+folder).mkdir();
    }
}
