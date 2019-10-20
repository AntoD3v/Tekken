package com.tekken.template;

import com.tekken.exception.BackendInvalidException;
import com.tekken.Option;
import com.tekken.site.Controller;
import com.tekken.site.Request;
import com.tekken.site.Response;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class TemplateEngine {

    private final TemplateUpdater templateUpdater;
    private final TemplateCache templateCache = new TemplateCache();
    private final String root;
    private final Controller controller;

    public TemplateEngine(Controller controller, String root) throws UnsupportedEncodingException {
        this.controller = controller;
        this.root = root;
        this.templateUpdater = new TemplateUpdater(Option.UPDATER_DELAY, root, this);
        templateUpdater.updater();
        templateUpdater.start();
    }

    public Response classLoader(TemplateFile templateFile, Request request) throws BackendInvalidException {
        try {

            Class<?> c = Class.forName(templateFile.getClazz());
            Constructor<?> cons = c.getConstructor();
            Object o = cons.newInstance();
            Method method = c.getMethod("handler", Controller.class, Request.class, Response.class);
            return (Response) method.invoke(o, controller, request, new Response(templateFile.getHtmlCode()));
        } catch (Exception e) {
            e.printStackTrace();
            throw new BackendInvalidException(templateFile.getClazz());
        }
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
