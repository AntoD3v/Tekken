package com.tekken.handler;

import com.tekken.template.impl.FileUtils;
import io.vertx.core.Handler;
import io.vertx.ext.web.RoutingContext;

import java.io.File;

public class handleError extends FileUtils implements Handler<RoutingContext> {



    @Override
    public void handle(RoutingContext routingContext) {

            String path = getPathRessource("tekken_default");
            File file = new File(path+"/"+routingContext.statusCode()+".html");
            routingContext.response().end(file.exists() ? readToString(file) : "Error "+routingContext.statusCode());

    }

}
