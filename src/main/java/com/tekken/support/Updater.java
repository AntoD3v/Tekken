package com.tekken.support;

import com.tekken.support.install.Downloader;

import java.io.File;

import static com.tekken.Option.TEKKEN_FOLDER;

public class Updater {

    public static void upgrade(){
        if(!(new File(TEKKEN_FOLDER).exists()))
            new Downloader();
    }

}
