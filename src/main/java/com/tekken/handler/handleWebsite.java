package com.tekken.handler;

import com.tekken.exception.BackendInvalidException;
import com.tekken.site.Request;
import com.tekken.site.Response;
import com.tekken.support.Logs;
import com.tekken.template.TemplateEngine;
import com.tekken.template.TemplateFile;
import io.vertx.core.Handler;
import io.vertx.ext.web.RoutingContext;

public class handleWebsite implements Handler<RoutingContext> {

    private final TemplateEngine templateEngine;

    public handleWebsite(TemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
    }

    @Override
    public void handle(RoutingContext routingContext) {

        Logs.debug("Client "+routingContext.request().remoteAddress().host()+" with path -> "+routingContext.request().path());

        String pagename;
        if((pagename = templateEngine.getTemplateRouter().getPage(routingContext.request().path())) != null) {

            TemplateFile templateFile = templateEngine.getTemplateCache().getTemplateFilesCache().get(pagename);

            Request request = new Request();

            request.setPath(routingContext.request().path());
            request.setMethod(routingContext.request().method());
            request.setParams(routingContext.request().method());

            try {
                Response response = templateEngine.classLoader(templateFile, request);
                routingContext.response().setStatusCode(200);
                routingContext.response().end(response.getHtmlCode());
            } catch (Exception e) {
                routingContext.fail(500);
                Logs.error("Client "+routingContext.request().remoteAddress().host()+" created error 500 ", e);
            } catch (BackendInvalidException e) {
                routingContext.fail(500);
                Logs.error("Client "+routingContext.request().remoteAddress().host()+" created error 500 ", e);
            }
        } else
            routingContext.fail(404);
    }
}
