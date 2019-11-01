package com.tekken.modules;

import com.tekken.Start;
import com.tekken.support.Logs;

import java.net.URL;
import java.net.URLClassLoader;
import java.util.HashMap;
import java.util.Map;

public class ModuleManager {

    private final Start start;
    private ModuleScanner moduleScanner = new ModuleScanner();
    private Map<String, JavaModule> modules = new HashMap<>();

    public ModuleManager(Start start) {
        this.start = start;
        loadAllModule();
    }

    public void loadAllModule(){
        Logs.info("Loading modules ...");
        moduleScanner.moduleScannerDir();
        moduleScanner.getMaps().entrySet().forEach(entry -> {
            loadModule(entry.getKey(), entry.getValue());
        });
    }

    public void loadModule(String clazz, URL url) {
        try {
            Class<?> moduleClass = Class.forName(clazz, true, new URLClassLoader(new URL[]{url}));
            JavaModule javaModule = (JavaModule) moduleClass.newInstance();
            Logs.info("Module ["+javaModule.getModuleName()+"] is loaded !");
            javaModule.setMain(start);
            javaModule.onLoad();
            modules.put(javaModule.getModuleName(), javaModule);
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

    public void executeReady(){
        modules.values().forEach(javaModule -> {
            javaModule.onReady();
        });
    }


}
