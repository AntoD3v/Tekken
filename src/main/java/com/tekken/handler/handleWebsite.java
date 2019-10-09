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
        //routingContext.response().setChunked(true);

        routingContext.response().end(templateManager.getTemplateFilesCache().get("index.html"));
    }
}
