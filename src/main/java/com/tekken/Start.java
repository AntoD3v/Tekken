package com.tekken;

import com.tekken.extra.ErrorManager;
import com.tekken.handler.handleWebsite;
import com.tekken.template.TemplateEngine;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.http.HttpServer;
import io.vertx.ext.web.Route;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;
import io.vertx.ext.web.handler.StaticHandler;

import java.io.*;
import java.net.URLDecoder;

public class Start extends AbstractVerticle {

    private TemplateEngine templateEngine;
    private ErrorManager errorManager;

    private final ClassLoader classLoader = getClass().getClassLoader();


    public Start() {

        try {
            templateEngine = new TemplateEngine("webroot");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    public void start() {

        final Router router = Router.router(vertx);

        router.route().handler(BodyHandler.create());
        Route website = router.get().handler(new handleWebsite(templateEngine));
        router.get("/assets/").handler(StaticHandler.create("assets/"));

        router.get().failureHandler(ctx -> {
            try {
                String path = URLDecoder.decode(classLoader.getResource("tekken_default").getPath(), "UTF-8");
                File file = new File(path+"/"+ctx.statusCode()+".html");
                ctx.response().end(file.exists() ? readToString(file) : "Error tekken");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        });

        HttpServer test = vertx.createHttpServer().requestHandler(router).listen(80);

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
