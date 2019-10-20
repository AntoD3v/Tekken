package com.tekken.support;

import com.tekken.support.install.Downloader;

import java.io.File;

public class Updater {

    public static void upgrade(){
        if(!(new File("config/config.xml").exists()))
            new Downloader();
    }

}
