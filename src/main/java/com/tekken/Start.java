package com.tekken;

import io.vertx.core.AbstractVerticle;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;
import io.vertx.ext.web.handler.StaticHandler;

public class Start extends AbstractVerticle {

    public Start() {

    }

    public void start() {

        final Router router = Router.router(vertx);

        router.route().handler(BodyHandler.create());
        router.get().handler(new handleGetIndex());
        //router.post("/auth/login").handler(new handlePostLogin(replication, cache));
        //router.post("/auth/token").handler(new handlePostToken(cache));

        router.route().handler(StaticHandler.create("webroot"));
        vertx.createHttpServer().requestHandler(router).listen(80);

    }

}
