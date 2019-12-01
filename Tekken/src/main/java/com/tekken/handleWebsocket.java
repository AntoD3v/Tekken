package com.tekken;

import com.tekken.support.Logs;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.ext.bridge.BridgeEventType;
import io.vertx.ext.bridge.PermittedOptions;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.sockjs.BridgeEvent;
import io.vertx.ext.web.handler.sockjs.BridgeOptions;
import io.vertx.ext.web.handler.sockjs.SockJSHandler;

public class handleWebsocket implements Handler<BridgeEvent> {

    @Override
    public void handle(BridgeEvent bridgeEvent) {

    }
}
