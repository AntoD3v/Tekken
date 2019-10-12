package com.tekken.template;

import com.tekken.exception.BackendInvalidException;
import com.tekken.Option;
import com.tekken.site.Controller;
import com.tekken.site.Request;
import com.tekken.site.Response;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;

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

    public Response classLoader(TemplateFile templateFile) throws BackendInvalidException {
        try {

            Class[] cArg = new Class[3];
            cArg[0] = Controller.class;
            cArg[1] = Request.class;
            cArg[2] = Response.class;
            return (Response) Class.forName(templateFile.getClazz()).getDeclaredMethod("handler", cArg).invoke(
                    controller, new Request(), new Response(templateFile.getHtmlCode()), null);
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
