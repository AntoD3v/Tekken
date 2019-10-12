package com.tekken.template;

import com.tekken.Option;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.util.ArrayList;

public class TemplateFile {

    private final String name;
    private String htmlCode;
    private boolean cache = true;
    private ArrayList<String> getters = new ArrayList<>();
    private long lastModified;
    private String clazz = "com.tekken.site.Website";

    public TemplateFile(File file, String name, String htmlCode) {
        this.lastModified = file.lastModified();
        this.name = name;
        this.htmlCode = htmlCode;
        parseHtmlToTerrex();
        if(!Option.MODE_DEVELOPPER)
            compile();
    }

    private void compile() {
        //css & javascript minifié, simplifié,
    }

    private void parseHtmlToTerrex(){
        Document document = Jsoup.parse(htmlCode);
        Elements terrex = document.select("tekken");
        if(!document.select("tekken").isEmpty()){
            for (Element e : terrex.get(0).children()){
                switch (e.tagName()){
                    case "cache":
                        setCache(Boolean.parseBoolean(e.text()));
                        break;
                    case "getter":
                        getters.add(e.text());
                        break;
                    case "access_html":

                        break;
                    case "backend":
                        clazz = e.text();
                        break;
                    default:
                        System.out.println("Properties <"+e.tagName()+"> not exist in "+getName());
                }
            }
            document.select("tekken").get(0).remove();

        }
        htmlCode = document.html();
    }

    public String getName() {
        return name;
    }

    public String getHtmlCode() {
        return htmlCode;
    }

    public boolean isCache() {
        return cache;
    }

    public void setCache(boolean cache) {
        this.cache = cache;
    }

    public ArrayList<String> getGetters() {
        return getters;
    }

    public void setRoot(ArrayList<String> getters) {
        this.getters = getters;
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

    public String getClazz() {
        return clazz;
    }
}
