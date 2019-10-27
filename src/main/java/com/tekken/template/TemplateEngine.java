package com.tekken.template;

import com.tekken.Option;
import com.tekken.exception.BackendInvalidException;
import com.tekken.site.Controller;
import com.tekken.site.Request;
import com.tekken.site.Response;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

public class TemplateEngine {

    private final TemplateUpdater templateUpdater;
    private final TemplateRouter templateRouter = new TemplateRouter();
    private final TemplateCache templateCache = new TemplateCache(templateRouter);
    private final Controller controller;

    public TemplateEngine(Controller controller) throws UnsupportedEncodingException {
        this.controller = controller;
        this.templateUpdater = new TemplateUpdater(Option.TEMPLATE_WEBROOT, this);
    }

    public Response classLoader(TemplateFile templateFile, Request request) throws BackendInvalidException {
        Response response = new Response(templateFile.getHtmlCode());

        for (String clazz : templateFile.getBackends()) {
            try {
                Class<?> c = Class.forName(clazz);
                Constructor<?> cons = c.getConstructor();
                Object o = cons.newInstance();
                Method method = c.getMethod("handler", Controller.class, Request.class, Response.class);
                response = (Response) method.invoke(o, controller, request, response);
            } catch (Exception e) {
                e.printStackTrace();
                throw new BackendInvalidException(clazz);
            }
        }
        return response;
    }

    public TemplateCache getTemplateCache() {
        return templateCache;
    }

    public TemplateUpdater getTemplateUpdater() {
        return templateUpdater;
    }

    public Controller getController() {
        return controller;
    }

    public TemplateRouter getTemplateRouter() {
        return templateRouter;
    }
}
