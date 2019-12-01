package com.tekken.template.build;

import com.tekken.support.Logs;
import org.apache.commons.io.FilenameUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;

public class ClassLoaderExternal extends ClassLoader {

    public Class loadClass(String clazz, String url) {
        Logs.debug("Loading class " + clazz);
        try {
            InputStream input = new URL(url).openConnection().getInputStream();
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            int data = input.read();

            while (data != -1) {
                buffer.write(data);
                data = input.read();
            }

            input.close();

            byte[] classData = buffer.toByteArray();
            return defineClass(clazz, classData, 0, classData.length);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public Class loadClass(String clazz, URLClassLoader url) {
        Class result;
        String className;
        String[] clazzSplit = clazz.split("\\.");
        className = (clazzSplit.length == 0) ? clazz : clazzSplit[clazzSplit.length - 1];

        for (URL urlURL : url.getURLs()) {
            if (className.equalsIgnoreCase(new File(urlURL.getPath()).getName().replace(".class", ""))) {
                if ((result = loadClass(clazz, urlURL.toString())) != null)
                    return result;
                else
                    Logs.warn("Cannot load the class " + clazz);
            }

        }
        return null;
    }

    public URLClassLoader fileArrayToUrlClassLoader(File[] listFiles) throws MalformedURLException {
        if(listFiles == null) return new URLClassLoader(new URL[0]);
        List<URL> urlArrayList = new ArrayList<>();
        for (int i = 0; i < listFiles.length; i++) {
            if (FilenameUtils.getExtension(listFiles[i].getName()).equals("class"))
                urlArrayList.add(listFiles[i].toURI().toURL());
        }
        URL[] url = new URL[urlArrayList.size()];
        return new URLClassLoader(urlArrayList.toArray(url));
    }
}
