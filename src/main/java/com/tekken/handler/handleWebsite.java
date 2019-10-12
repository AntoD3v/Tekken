package com.tekken.handler;

import com.tekken.template.TemplateEngine;
import io.vertx.core.Handler;
import io.vertx.ext.web.RoutingContext;

public class handleWebsite implements Handler<RoutingContext> {

    private final TemplateEngine templateManager;

    public handleWebsite(TemplateEngine templateManager) {
        this.templateManager = templateManager;
    }

    @Override
    public void handle(RoutingContext routingContext) {
        System.out.println("Path -> "+routingContext.request().path());
        if(templateManager.getTemplateCache().getRouters().containsKey(routingContext.request().path()))
            routingContext.response().end(
                templateManager.getTemplateCache().getTemplateFilesCache().get(templateManager.getTemplateCache().getRouters().get(routingContext.request().path())).getHtmlCode());
        else
            routingContext.fail(404);
    }
}
