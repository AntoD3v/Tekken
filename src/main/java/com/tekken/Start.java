package com.tekken;

import com.tekken.handler.handleError;
import com.tekken.handler.handleWebsite;
import com.tekken.modules.ModuleManager;
import com.tekken.site.Controller;
import com.tekken.support.Logs;
import com.tekken.template.TemplateEngine;
import io.vertx.core.AbstractVerticle;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;
import io.vertx.ext.web.handler.StaticHandler;

import java.io.UnsupportedEncodingException;

public class Start extends AbstractVerticle {

    private final ModuleManager moduleManager;
    private final Controller controller;
    private TemplateEngine templateEngine;
    private Router routers;

    public Start() {
        moduleManager = new ModuleManager(this);
        controller = new Controller(moduleManager);
        try {
            templateEngine = new TemplateEngine(controller);
        } catch (UnsupportedEncodingException e) {
            Logs.error("Cannot found the template directory", e);
        }

    }

    public void start() {

        routers = Router.router(vertx);

        moduleManager.executeReady();

        routers.route().handler(BodyHandler.create());

        routers.route("/assets/*").handler(StaticHandler.create().setCachingEnabled(true).setWebRoot("assets"));
        routers.get().handler(new handleWebsite(templateEngine));
        routers.get().failureHandler(new handleError());

        vertx.createHttpServer().requestHandler(routers).listen(Option.VERTX_PORT);
        Logs.info("Listening on *:"+Option.VERTX_PORT);

    }

    public Router getRouters() {
        return routers;
    }
}
