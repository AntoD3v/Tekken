package com.tekken.modules;

import com.tekken.support.Logs;
import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;

public class ModuleManager {



    public ModuleManager() {

    }

    public void loadAllModule(){
        File modules = new File("modules/");
        if(modules != null && modules.exists() && modules.isDirectory()) {
            for(File file : modules.listFiles()){
                if(getExtensionByApacheCommonLib(file.getPath()).equalsIgnoreCase("jar")){
                    List clazzs = getClassFromFile(file.getPath());
                }
            }
        }else
            Logs.warn("Cannot load module");
    }

    public void loadModule(String clazz) {
        try {
            Class<?> c = Class.forName(clazz);
            JavaModule javaModule = (JavaModule)c.newInstance();
            javaModule.onLoad();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
    }

    public void unloadModule(String clazz) {
        try {
            Class<?> c = Class.forName(clazz);
            JavaModule javaModule = (JavaModule)c.newInstance();
            javaModule.onStop();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
    }

    public List getClassFromFile(String jarName){
        ArrayList classes = new ArrayList();

        try{
            JarInputStream jarFile = new JarInputStream
                    (new FileInputStream(jarName));
            JarEntry jarEntry;

            while((jarEntry=jarFile.getNextJarEntry()) != null) {

                if(jarEntry.getName ().endsWith (".class"))
                    classes.add (jarEntry.getName().replaceAll("/", "\\."));

            }
        }
        catch( Exception e){
            e.printStackTrace ();
        }
        return classes;
    }

    public String getExtensionByApacheCommonLib(String filename) {
        return FilenameUtils.getExtension(filename);
    }

}
