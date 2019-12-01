package com.tekken;

import com.tekken.handler.handleError;
import com.tekken.handler.handleWebsite;
import com.tekken.modules.ModuleManager;
import com.tekken.site.Controller;
import com.tekken.support.Logs;
import com.tekken.template.TemplateEngine;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.shareddata.SharedData;
import io.vertx.ext.bridge.PermittedOptions;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;
import io.vertx.ext.web.handler.CookieHandler;
import io.vertx.ext.web.handler.StaticHandler;
import io.vertx.ext.web.handler.sockjs.BridgeOptions;
import io.vertx.ext.web.handler.sockjs.SockJSHandler;

public class Start extends AbstractVerticle {

    private final ModuleManager moduleManager;
    private final Controller controller;
    private TemplateEngine templateEngine;
    private Router routers;

    public Start() {
        moduleManager = new ModuleManager(this);
        controller = new Controller(moduleManager);
        templateEngine = new TemplateEngine(controller);
    }

    public void start() {

        routers = Router.router(vertx);

        moduleManager.executeReady();

        //routers.route().handler(BodyHandler.create());
        routers.route().handler(CookieHandler.create());

        routers.route("/assets/*").handler(StaticHandler.create().setFilesReadOnly(true).setCachingEnabled(true).setWebRoot(Option.TEMPLATE_DESIGN));
        routers.route("/_ws/*").handler(eventBusHandler());
        routers.get().handler(new handleWebsite(templateEngine));
        routers.get().failureHandler(new handleError());

        vertx.createHttpServer().requestHandler(routers).listen(Option.VERTX_PORT);
        Logs.info("Listening on *:"+Option.VERTX_PORT);

    }

    public Router getRouters() {
        return routers;
    }

    public Controller getController() {
        return controller;
    }

    public ModuleManager getModuleManager() {
        return moduleManager;
    }


    private SockJSHandler eventBusHandler() {
        BridgeOptions options = new BridgeOptions()
                .addOutboundPermitted(new PermittedOptions().setAddressRegex("out"))
                .addInboundPermitted(new PermittedOptions().setAddressRegex("in"));

        SharedData data = vertx.sharedData();
        EventBus eventBus = vertx.eventBus();

        SockJSHandler sockJSHandler = SockJSHandler.create(vertx);
        return sockJSHandler.bridge(options, new handleWebsocket());
    }
}
