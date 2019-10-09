package com.tekken;

import com.tekken.handler.handleWebsite;
import com.tekken.template.TemplateEngine;
import io.vertx.core.AbstractVerticle;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;

public class Start extends AbstractVerticle {

    private final TemplateEngine templateManager;

    public Start() {
        templateManager = new TemplateEngine("webroot");
    }

    public void start() {

        final Router router = Router.router(vertx);

        router.route().handler(BodyHandler.create());
        router.get().handler(new handleWebsite(templateManager));
        //router.get().handler(new handleGetIndex());
        //router.post("/auth/login").handler(new handlePostLogin(replication, cache));
        //router.post("/auth/token").handler(new handlePostToken(cache));

        //router.route().handler(StaticHandler.create("webroot"));
        vertx.createHttpServer().requestHandler(router).listen(80);

    }

}
