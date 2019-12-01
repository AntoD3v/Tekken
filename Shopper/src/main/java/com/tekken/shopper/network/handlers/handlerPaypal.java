package com.tekken.shopper.network.handlers;

import com.paypal.ipn.IPNMessage;
import com.tekken.shopper.network.ipn.Paypal;
import io.vertx.core.Handler;
import io.vertx.ext.web.RoutingContext;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.Map;

public class handlerPaypal implements Handler<RoutingContext> {

    @Override
    public void handle(RoutingContext routingContext) {
        Map<String,String> configMap = new HashMap<String,String>();
        configMap.put("mode", "sandbox");
/*
        IPNMessage ipnlistener = new IPNMessage((Map<String, String[]>) routingContext.request().params(),configMap);

        boolean isIpnVerified = ipnlistener.validate();
        String transactionType = ipnlistener.getTransactionType();
        Map<String,String> map = ipnlistener.getIpnMap();*/
    }
}
