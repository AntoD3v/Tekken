package com.tekken.template;

import com.tekken.site.Response;
import com.tekken.site.Website;
import com.tekken.template.impl.ITemplateFile;

import java.util.ArrayList;
import java.util.List;

public class TemplatePage implements ITemplateFile {

    private final String name;
    private String htmlCode;
    private final Response response;
    private long lastModified;
    private List<String> routers = new ArrayList<>();
    private List<Website> backends = new ArrayList<>();

    public TemplatePage(String name, String htmlCode, long lastModified, List<String> routers, List<Website> backends) {
        this.name = name;
        this.htmlCode = htmlCode;
        this.lastModified = lastModified;
        this.routers = routers;
        this.backends = backends;
        this.response = new Response(htmlCode);
    }

    public TemplatePage(String name, String htmlCode, long lastModified) {
        this.name = name;
        this.htmlCode = htmlCode;
        this.lastModified = lastModified;
        this.response = new Response(htmlCode);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getHtmlCode() {
        return htmlCode;
    }

    @Override
    public long getLastModified() {
        return lastModified;
    }

    @Override
    public Response getResponse() {
        return null;
    }

    @Override
    public List<String> getRouters() {
        return routers;
    }

    @Override
    public List<Website> getBackends() {
        return backends;
    }

    @Override
    public void addRouters(String router) {
        routers.add(router);
    }

    @Override
    public void addBackend(Website website) {
        backends.add(website);
    }

    @Override
    public void updateLastModified(long lastModified) {
        this.lastModified = lastModified;
    }

    @Override
    public void updateHtmlCode(String htmlCode) {
        this.htmlCode = htmlCode;
    }
}
