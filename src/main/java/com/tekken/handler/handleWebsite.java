package com.tekken.handler;

import com.tekken.exception.BackendInvalidException;
import com.tekken.site.Controller;
import com.tekken.site.Response;
import com.tekken.template.TemplateEngine;
import com.tekken.template.TemplateFile;
import io.vertx.core.Handler;
import io.vertx.ext.web.RoutingContext;

public class handleWebsite implements Handler<RoutingContext> {

    private final TemplateEngine templateManager;

    public handleWebsite(Controller controller, TemplateEngine templateManager) {
        this.templateManager = templateManager;
    }

    @Override
    public void handle(RoutingContext routingContext) {
        System.out.println("Path -> "+routingContext.request().path());
        if(templateManager.getTemplateCache().getRouters().containsKey(routingContext.request().path())) {
            TemplateFile templateFile = templateManager.getTemplateCache().getTemplateFilesCache().get(templateManager.getTemplateCache().getRouters().get(routingContext.request().path()));
            try {
                Response response = templateManager.classLoader(templateFile);
                routingContext.response().setStatusCode(200);
                routingContext.response().end(response.getHtmlCode());
            } catch (BackendInvalidException e) {
                routingContext.fail(500);
                e.printStackTrace();
            }
       } else
            routingContext.fail(404);
    }
}
