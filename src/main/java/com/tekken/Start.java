package com.tekken;

import com.tekken.handler.handleWebsite;
import com.tekken.site.Controller;
import com.tekken.support.Logs;
import com.tekken.template.TemplateEngine;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.http.HttpServer;
import io.vertx.ext.web.Route;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;
import io.vertx.ext.web.handler.StaticHandler;
import org.fusesource.jansi.AnsiConsole;

import java.io.*;
import java.net.URLDecoder;

public class Start extends AbstractVerticle {

    private final Controller controller;
    private TemplateEngine templateEngine;

    private final ClassLoader classLoader = getClass().getClassLoader();


    public Start() {
        controller = new Controller();
        try {
            templateEngine = new TemplateEngine(controller,  "webroot");
        } catch (UnsupportedEncodingException e) {
            Logs.error("Template not found", e);
        }
    }

    public void start() {

        final Router router = Router.router(vertx);

        router.route().handler(BodyHandler.create());

        router.route("/assets/*").handler(StaticHandler.create().setCachingEnabled(true).setWebRoot("assets"));

        router.get().handler(new handleWebsite(templateEngine));

        router.get().failureHandler(ctx -> {
           try {
                String path = URLDecoder.decode(classLoader.getResource("tekken_default").getPath(), "UTF-8");
                File file = new File(path+"/"+ctx.statusCode()+".html");
                ctx.response().end(file.exists() ? readToString(file) : "Error tekken");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
       });

        vertx.createHttpServer().requestHandler(router).listen(Option.VERTX_PORT);
        Logs.info("Listening on *:"+Option.VERTX_PORT);

    }

    public String readToString(File file) {
        String returnString = "";
        try {
            FileReader reader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(reader);

            String line;
            while ((line = bufferedReader.readLine()) != null) {
                returnString += line;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return returnString;
    }

}
