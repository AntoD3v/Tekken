package com.tekken.modules;

import com.tekken.support.Logs;
import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.jar.Attributes;
import java.util.jar.JarFile;

import static com.tekken.Option.TEKKEN_FOLDER;
import static com.tekken.Option.TEKKEN_MODULE;

public class ModuleScanner {

    private URLClassLoader urlClassLoader;
    private List<String> clazzs = new ArrayList<>();
    private List<URL> urls = new ArrayList<>();
    private File moduleDirectory = new File(TEKKEN_FOLDER+TEKKEN_MODULE);
    private Map<String, URL> maps = new HashMap<>();

    public void moduleScannerDir(){
        JarFile jarFile;
        for (File file : moduleDirectory.listFiles()) {
            if(!file.exists() || (file.exists() && file.isDirectory()) || !FilenameUtils.getExtension(file.getName()).equals("jar"))
                continue;

            try {
                jarFile = new JarFile(file);
                Attributes main = jarFile.getManifest().getMainAttributes();
                String moduleClass;
                if((moduleClass = main.getValue("Module-Class")) != null){
                    maps.put(moduleClass, file.toURI().toURL());
                }else
                    Logs.warn("Cannot load "+file.getName()+" because there are a invalid manifest");
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }

    public Map<String, URL> getMaps() {
        return maps;
    }

    public List<String> getClazzs() {
        return clazzs;
    }

    public URLClassLoader getUrlClassLoader() {
        return urlClassLoader;
    }
}
