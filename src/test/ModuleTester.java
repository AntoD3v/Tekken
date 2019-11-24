import com.tekken.Tekken;
import com.tekken.template.impl.FileUtils;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class ModuleTester extends FileUtils {

    public ModuleTester() throws IOException, NoSuchMethodException, ClassNotFoundException, InvocationTargetException, IllegalAccessException, InstantiationException {

        String path = "tekken/modules/AuthTekken.jar";

        JarFile jarFile = new JarFile(path);
        Enumeration<JarEntry> e = jarFile.entries();

        while (e.hasMoreElements()) {
            JarEntry je = e.nextElement();
            if(je.isDirectory() || !je.getName().endsWith(".class"))
                continue;

            byte[] data = readClass("jar:file:" + path+"!/"+je.getName());

            Method method = ClassLoader.class.getDeclaredMethod("defineClass", String.class, byte[].class, int.class, int.class);
            method.setAccessible(true);
            method.invoke(Tekken.class.getClassLoader(), je.getName().replace(".class", "").replace("/", "."), data, 0, data.length);


        }

        System.out.println(Class.forName("com.tekken.auth.backends.Login").newInstance().toString());
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException, InstantiationException {
        new ModuleTester();
    }

}
