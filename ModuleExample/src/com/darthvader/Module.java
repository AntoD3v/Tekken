package com.darthvader;

import com.tekken.modules.JavaModule;
import com.tekken.support.Logs;
import io.vertx.ext.web.RoutingContext;

public class Module extends JavaModule {

    public Module() {
        super("moduletest", "1.0");
    }

    @Override
    public void onLoad() {
        Logs.info("Module ["+getModuleName()+"] is loaded !");
    }

    @Override
    public void onReady() {
        getRouters().get("/module").handler(new io.vertx.core.Handler<io.vertx.ext.web.RoutingContext>() {

            @Override
            public void handle(RoutingContext routingContext) {
                routingContext.response().end("ça marche !!!!!");
            }
        });
        getRouters().
    }

    @Override
    public void onStop() {

    }


}
