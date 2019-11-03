package com.tekken.auth.api;

import com.tekken.auth.AuthTekken;

import io.vertx.core.Handler;
import io.vertx.ext.web.RoutingContext;
import javax.xml.ws.handler.MessageContext;

public class handlePostLogin implements Handler<RoutingContext> {

    private final AuthTekken authTekken;

    public handlePostLogin(AuthTekken authTekken) {
        this.authTekken = authTekken;
    }


    @Override
    public void handle(RoutingContext routingContext) {
        routingContext.response().end("test");
    }
}
