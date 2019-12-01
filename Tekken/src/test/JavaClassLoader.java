import com.tekken.site.Website;
import com.tekken.support.Logs;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class JavaClassLoader extends ClassLoader{

    public JavaClassLoader(ClassLoader parent) {
        super(parent);
    }

    public Class loadClass(String name) throws ClassNotFoundException {
        if(!"com.tekken.example.Header".equals(name))
            return super.loadClass(name);

        try {
            String url = "file:/H:/Développement/Tekken/backends/Header.class";
            URL myUrl = new URL(url);
            URLConnection connection = myUrl.openConnection();
            InputStream input = connection.getInputStream();
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            int data = input.read();

            while(data != -1){
                buffer.write(data);
                data = input.read();
            }

            input.close();

            byte[] classData = buffer.toByteArray();

            return defineClass("com.tekken.example.Header",
                    classData, 0, classData.length);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static void main(String[] args) throws ClassNotFoundException, IllegalAccessException, InstantiationException {


        JavaClassLoader classLoader = new JavaClassLoader(JavaClassLoader.class.getClassLoader());
        Website website = (Website) classLoader.loadClass("com.tekken.example.Header").newInstance();
        Logs.debug("test: "+website.toString());
    }

}
