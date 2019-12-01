package com.tekken.template;

import java.util.HashMap;
import java.util.Map;

public class TemplateCache {

    private final Map<String, TemplatePage> templatePageCache = new HashMap<>();
    private final TemplateRouter routers;

    public TemplateCache(TemplateRouter templateRouter) {
        this.routers = templateRouter;
    }

    public void updateFileCache(String name, TemplatePage templatePage){
        templatePageCache.put(name, templatePage);
        routers.reset(name);
        for(String getter : templatePage.getRouters())
            routers.addRoot(getter, name);
    }

    public Map<String, TemplatePage> getCache() {
        return templatePageCache;
    }

}
