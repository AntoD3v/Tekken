package com.tekken;

import com.tekken.handler.handleError;
import com.tekken.handler.handleWebsite;
import com.tekken.site.Controller;
import com.tekken.support.Logs;
import com.tekken.template.TemplateEngine;
import io.vertx.core.AbstractVerticle;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;
import io.vertx.ext.web.handler.StaticHandler;

import java.io.UnsupportedEncodingException;

public class Start extends AbstractVerticle {

    private final Controller controller = new Controller();
    private TemplateEngine templateEngine;

    public Start() {
        try {
            templateEngine = new TemplateEngine(controller);
        } catch (UnsupportedEncodingException e) {
            Logs.error("Template not found", e);
        }
    }

    public void start() {

        final Router router = Router.router(vertx);

        router.route().handler(BodyHandler.create());

        router.route("/assets/*").handler(StaticHandler.create().setCachingEnabled(true).setWebRoot("assets"));
        router.get().handler(new handleWebsite(templateEngine));
        router.get().failureHandler(new handleError());

        vertx.createHttpServer().requestHandler(router).listen(Option.VERTX_PORT);
        Logs.info("Listening on *:"+Option.VERTX_PORT);

    }

}
