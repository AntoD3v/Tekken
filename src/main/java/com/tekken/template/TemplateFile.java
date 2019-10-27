package com.tekken.template;

import com.tekken.Option;
import com.tekken.support.Logs;
import com.tekken.template.impl.FileUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Iterator;

public class TemplateFile implements FileUtils {

    private final String name;
    private String htmlCode;
    private ArrayList<String> getters = new ArrayList<>();
    private ArrayList<String> backends = new ArrayList<>();
    private long lastModified;

    public TemplateFile(File file, String name, String htmlCode) {
        this.lastModified = file.lastModified();
        this.name = name;
        htmlCode = parseConfigurationTekken(Jsoup.parse(htmlCode));
        this.htmlCode = parseImportTekken(Jsoup.parse(htmlCode));
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
                backends.add(backend);
                continue;
            }
            Logs.warn("Attributes missing to import in "+getName());
        }
        return document.toString();
    }

    private String parseConfigurationTekken(Document document){
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
                        backends.add(e.text());
                        break;
                    default:
                        Logs.warn("Properties <" + e.tagName() + "> not exist in " + getName());
                }
            }
            document.select("tekken").get(0).remove();

        }
        return document.html();
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

    public ArrayList<String> getBackends() {
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
