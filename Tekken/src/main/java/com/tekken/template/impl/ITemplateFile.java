package com.tekken.template.impl;

import com.tekken.site.Response;
import com.tekken.site.Website;

import java.util.List;

public interface ITemplateFile {

    String getName();
    String getHtmlCode();
    long getLastModified();
    Response getResponse();

    List<String> getRouters();
    List<Website> getBackends();

    void addRouters(String router);
    void addBackend(Website website);

    void updateLastModified(long lastModified);
    void updateHtmlCode(String htmlCode);


}
