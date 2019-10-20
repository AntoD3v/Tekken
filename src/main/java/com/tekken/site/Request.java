package com.tekken.site;

import io.vertx.core.http.HttpMethod;
import io.vertx.core.net.NetSocket;
import io.vertx.ext.web.RoutingContext;

public class Request {
    private RoutingContext routingContext;
    private String path;
    private NetSocket remoteAddress;
    private HttpMethod method;
    private HttpMethod params;

    public RoutingContext getRoutingContext() {
        return routingContext;
    }

    public void setRoutingContext(RoutingContext routingContext) {
        this.routingContext = routingContext;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public NetSocket getRemoteAddress() {
        return remoteAddress;
    }

    public void setRemoteAddress(NetSocket remoteAddress) {
        this.remoteAddress = remoteAddress;
    }

    public HttpMethod getMethod() {
        return method;
    }

    public void setMethod(HttpMethod method) {
        this.method = method;
    }

    public HttpMethod getParams() {
        return params;
    }

    public void setParams(HttpMethod params) {
        this.params = params;
    }
}
