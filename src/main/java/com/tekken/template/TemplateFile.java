package com.tekken.template;

import com.tekken.Option;
import com.tekken.exception.BackendNotFoundException;
import com.tekken.site.Response;
import com.tekken.site.Website;
import com.tekken.support.Logs;
import com.tekken.template.build.ClassLoaderExternal;
import com.tekken.template.impl.FileUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Iterator;

import static com.tekken.Option.TEKKEN_FOLDER;

public class TemplateFile extends FileUtils {

    private final String name;
    private final ClassLoaderExternal classLoaderExternal = new ClassLoaderExternal();
    private URLClassLoader urlClassLoader;
    private String htmlCode;
    private Response response;
    private ArrayList<String> getters = new ArrayList<>();
    private ArrayList<Website> backends = new ArrayList<>();
    private long lastModified;

    public TemplateFile(File file, String name, String htmlCode) {
        try {
            this.urlClassLoader = classLoaderExternal.fileArrayToUrlClassLoader(new File(TEKKEN_FOLDER+"backends/").listFiles());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        this.lastModified = file.lastModified();
        this.name = name;
        htmlCode = parseConfigurationTekken(htmlCode);
        this.htmlCode = parseImportTekken(Jsoup.parse(htmlCode));
        this.response = new Response(this.htmlCode);

    }

    private String parseImportTekken(Document document) {
        Iterator<Element> it = document.select("tekken-import").iterator();
        Element imp; String template, backend, html; File file;
        while(it.hasNext()){
            imp = it.next();
            if((template = imp.attr("template")) != null && (backend = imp.attr("backend")) != null){
                if(!(file = new File(getPathRessource(Option.TEMPLATE_WEBROOT)+"/"+template)).exists()){
                    Logs.warn("Cannot import template in "+getName());
                    continue;
                }
                html = document.toString().replace(imp.toString(), readToString(file));
                document = Jsoup.parse(html);

                try {
                    Class clazz = classLoaderExternal.loadClass(backend, urlClassLoader);
                    if(clazz != null){
                        Website website = (Website) clazz.newInstance();
                        backends.add(website);
                    }else
                        throw new BackendNotFoundException(backend);
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (BackendNotFoundException e) {
                    Logs.error("Backend not found", e);
                }

                continue;
            }
            Logs.warn("Attributes missing to import in "+getName());
        }
        return document.toString();
    }

    private String parseConfigurationTekken(String htmlCode){
        Document document = Jsoup.parse(htmlCode);
        Elements terrex = document.select("tekken");
        if(!terrex.isEmpty()){
            for (Element e : terrex.get(0).children()){
                switch (e.tagName()){
                    case "getter":
                        getters.add(e.text());
                        break;
                    case "compile":

                        break;
                    case "backend":
                        Class clazz;
                        try {
                             clazz = classLoaderExternal.loadClass(e.text(), urlClassLoader);
                            if(clazz != null){
                                Website website = (Website) clazz.newInstance();
                                backends.add(website);
                            }else{
                                 clazz = classLoaderExternal.loadClass(e.text(), urlClassLoader);
                                if(clazz != null) {
                                    Website website = (Website) clazz.newInstance();
                                    backends.add(website);
                                }
                            }
                                throw new BackendNotFoundException(e.text());
                        } catch (InstantiationException e1) {
                            e1.printStackTrace();
                        } catch (IllegalAccessException e1) {
                            e1.printStackTrace();
                        } catch (BackendNotFoundException e1) {
                            Logs.error("Backend not found", e1);
                        }

                        break;
                    default:
                        Logs.warn("Properties <" + e.tagName() + "> not exist in " + getName());
                }
            }
            return htmlCode.replaceAll("(?s)<tekken>.*</tekken>", "");

        }
        return document.html();
    }

    public Response getResponse() {
        return response;
    }

    public String getName() {
        return name;
    }

    public String getHtmlCode() {
        return htmlCode;
    }

    public ArrayList<String> getGetters() {
        return getters;
    }

    public ArrayList<Website> getBackends() {
        return backends;
    }

    public long getLastModified() {
        return lastModified;
    }

    public void setLastModified(long lastModified) {
        this.lastModified = lastModified;
    }

    public void setHtmlCode(String htmlCode) {
        this.htmlCode = htmlCode;
    }
}
