package com.tekken.template;

import com.tekken.Option;

import java.io.UnsupportedEncodingException;

public class TemplateEngine {

    private final TemplateUpdater templateUpdater;
    private final TemplateCache templateCache = new TemplateCache();
    private final String root;

    public TemplateEngine(String root) throws UnsupportedEncodingException {
        this.root = root;
        this.templateUpdater = new TemplateUpdater(Option.UPDATER_DELAY, root, this);
        templateUpdater.updater();
        templateUpdater.start();
    }

    public TemplateCache getTemplateCache() {
        return templateCache;
    }

    public TemplateUpdater getTemplateUpdater() {
        return templateUpdater;
    }

    public String getRoot() {
        return root;
    }
}
