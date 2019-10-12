package com.tekken.template;

import java.util.HashMap;
import java.util.Map;

public class TemplateCache {

    private final Map<String, TemplateFile> templateFilesCache = new HashMap<>();
    private final Map<String, String> routers = new HashMap<>();

    public void updateFileCache(String name, TemplateFile templateFile){
        for(String getter : templateFile.getGetters())
            routers.put(getter, name);
        if(templateFile.isCache())
            templateFilesCache.put(name, templateFile);
    }

    public Map<String, TemplateFile> getTemplateFilesCache() {
        return templateFilesCache;
    }

    public Map<String, String> getRouters() {
        return routers;
    }
}
