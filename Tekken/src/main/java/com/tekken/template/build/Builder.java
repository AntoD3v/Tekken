package com.tekken.template.build;

import com.tekken.Option;
import com.tekken.Tekken;
import com.tekken.exception.BackendNotFoundException;
import com.tekken.site.Website;
import com.tekken.support.Logs;
import com.tekken.template.TemplatePage;
import com.tekken.template.impl.FileUtils;
import com.tekken.template.impl.TemplateBuilder;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.tekken.Option.TEKKEN_FOLDER;

public class Builder extends FileUtils implements TemplateBuilder {

    private final TemplatePage templatePage;

    public Builder(String name, String htmlCode, long lastModified) {
        templatePage = new TemplatePage(name, htmlCode, lastModified);
    }

    @Override
    public void configurationBuilder() {
        Document document = Jsoup.parse(templatePage.getHtmlCode());
        if(document.select("tekken").size() == 0) return;
        document.select("tekken").get(0).children().forEach(element -> {
            switch (element.tagName()){
                case "getter":
                    templatePage.addRouters(element.text());
                    break;
                case "backend":
                    templatePage.addBackend(loadClass(element.text()));
                    break;

                default:
                    Logs.warn("Configuration parameters <"+element.tagName()+"> is useless in "+templatePage.getName());
                    break;
            }

        });
    }

    @Override
    public void importationBuilder() {
        String htmlCode = templatePage.getHtmlCode();
        Document parser = Jsoup.parse(htmlCode);
        parser.select("tekken-import").forEach(element -> {
            String template, backend; File file;
            if((template = element.attr("template")) != null){
                if((file = new File(TEKKEN_FOLDER+Option.TEMPLATE_WEBROOT+template)).exists()){
                    if((backend = element.attr("backend")) != null && !backend.equals(""))
                        templatePage.addBackend(loadClass(backend));
                    element.html(readToString(file));
                }else
                    Logs.warn("Cannot import the template for "+templatePage.getName());

            }
        });
        templatePage.updateHtmlCode(parser.toString());
    }

    @Override
    public void cleanup() {
        String htmlCode = templatePage.getHtmlCode();
        htmlCode = htmlCode.replaceAll("(?s)<tekken>.*</tekken>", "");
        htmlCode = htmlCode.replaceAll("(?s)<tekken-import .*?>", "");
        templatePage.updateHtmlCode(htmlCode);
    }

    @Override
    public TemplatePage build() {
        configurationBuilder();
        importationBuilder();
        cleanup();
        return templatePage;
    }


    private Website loadClass(String backend){
        String url;
        try {
            Class clazz;
            try{
                clazz = Class.forName(backend);
            }catch (ClassNotFoundException e){
                if((url = findUrl(backend)) == null)
                    throw new BackendNotFoundException(backend);

                byte[] data = readClass(url);
                Method method = ClassLoader.class.getDeclaredMethod("defineClass", String.class, byte[].class, int.class, int.class);
                method.setAccessible(true);
                clazz = (Class) method.invoke(Tekken.class.getClassLoader(), backend, data, 0, data.length);
            }
            if(clazz == null)
                throw new BackendNotFoundException(backend);

            return (Website) clazz.newInstance();

        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (BackendNotFoundException e) {
            Logs.error("Backend not found "+backend+" in "+templatePage.getName());
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }

    private String findUrl(String backend) {
        Pattern pattern = Pattern.compile("/?([A-Za-z0-9]+).class$");
        String className;
        String[] clazzSplit = backend.split("\\.");
        className = (clazzSplit.length == 0) ? backend : clazzSplit[clazzSplit.length - 1];
        for (URL url : getBackendsURL(new File(TEKKEN_FOLDER + "backends/"))) {
            Matcher matcher = pattern.matcher(url.getPath());
            if (matcher.find()) {
                if (matcher.group(1).equalsIgnoreCase(className))
                    return url.toString();
            }
        }
        return null;
    }
}
