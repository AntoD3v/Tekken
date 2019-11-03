package com.tekken.modules;

import com.tekken.Start;
import com.tekken.Tekken;
import com.tekken.support.Logs;
import com.tekken.template.impl.FileUtils;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.jar.Attributes;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import static com.tekken.Option.TEKKEN_FOLDER;
import static com.tekken.Option.TEKKEN_MODULE;

public class ModuleManager extends FileUtils {

    private final Start start;
    private ModuleScanner moduleScanner = new ModuleScanner();
    private Map<String, JavaModule> modules = new HashMap<>();

    public ModuleManager(Start start) {
        this.start = start;
        loadAllModule();
    }

    public void loadAllModule(){
        Logs.info("Loading modules ...");
       /* moduleScanner.moduleScannerDir();
        moduleScanner.getMaps().entrySet().forEach(entry -> {
            loadModule(entry.getKey(), entry.getValue());
        });*/

        File moduleDirectory = new File(TEKKEN_FOLDER+TEKKEN_MODULE);
        for (File file : moduleDirectory.listFiles()) {
            loadModule(file);
        }

    }

    public void loadModule(File file) {
        try {

            JarFile jarFile = new JarFile(file);
            String clazz;
            Attributes main = jarFile.getManifest().getMainAttributes();
            if((clazz = main.getValue("Module-Class")) == null){
                Logs.warn("Cannot load "+file.getName()+" because there are a invalid manifest");
                return;
            }

            Enumeration<JarEntry> e = jarFile.entries();

            while (e.hasMoreElements()) {
                JarEntry je = e.nextElement();
                if(je.isDirectory() || !je.getName().endsWith(".class"))
                    continue;

                byte[] data = readClass("jar:file:" + file.getPath()+"!/"+je.getName());

                String clazzsimple = (je.getName().replace(".class", "")).replace("/", ".");
                Method method = ClassLoader.class.getDeclaredMethod("defineClass", String.class, byte[].class, int.class, int.class);
                method.setAccessible(true);
                method.invoke(Tekken.class.getClassLoader(), clazzsimple, data, 0, data.length);
            }

            Class<?> moduleClass = Class.forName(clazz);
            JavaModule javaModule = (JavaModule) moduleClass.newInstance();
            Logs.info("Module ["+javaModule.getModuleName()+"] is loaded !");
            javaModule.setMain(start);
            javaModule.onLoad();
            modules.put(javaModule.getModuleName(), javaModule);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
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
