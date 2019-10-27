package com.tekken.template;

import java.util.HashMap;
import java.util.Map;

public class TemplateCache {

    private final Map<String, TemplateFile> templateFilesCache = new HashMap<>();
    private final TemplateRouter routers;

    public TemplateCache(TemplateRouter templateRouter) {
        this.routers = templateRouter;
    }

    public void updateFileCache(String name, TemplateFile templateFile){
        templateFilesCache.put(name, templateFile);
        routers.reset(name);
        for(String getter : templateFile.getGetters())
            routers.addRoot(getter, name);
    }

    public Map<String, TemplateFile> getTemplateFilesCache() {
        return templateFilesCache;
    }

}
