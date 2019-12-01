package com.tekken.auth.api;

import com.tekken.auth.AuthCode;
import com.tekken.auth.AuthTekken;

import com.tekken.auth.crypt.Crypt;
import com.tekken.auth.crypt.Sha256;
import com.tekken.auth.session.Userdata;
import io.netty.handler.codec.http.QueryStringDecoder;
import io.vertx.core.Handler;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.ext.web.Cookie;
import io.vertx.ext.web.RoutingContext;
import javax.xml.ws.handler.MessageContext;
import java.sql.SQLException;
import java.util.List;
import java.util.Random;
import java.util.UUID;

public class handlePostLogin implements Handler<RoutingContext> {

    private final AuthTekken authTekken;
    private static Crypt crypt = new Sha256();

    public handlePostLogin(AuthTekken authTekken) {
        this.authTekken = authTekken;
    }


    @Override
    public void handle(RoutingContext routingContext) {
        String user, pass;
        routingContext.request().formAttributes().entries().forEach(a -> {
            System.out.println(a.getKey()+" ->>> "+a.getValue());
        });
        System.out.println("user: "+routingContext.request().getParam("user") );
        System.out.println("pass: "+routingContext.request().getParam("pass") );
        if((user = routingContext.request().getParam("user")) != null && (pass = routingContext.request().getParam("pass")) != null){
            String passCrypted = crypt.crypt(pass);
            try {
                if(authTekken.getDatabase().login(user, passCrypted)){

                    String cookie = "tekken_" + UUID.randomUUID().toString();
                    authTekken.getSessionManager().add(new Userdata(new Random().nextInt(1000), user), cookie);

                    /*Cookie cookieWeb = Cookie.cookie("tekken_auth",cookie);
                    cookieWeb.setPath("/");
                    cookieWeb.setMaxAge(158132000l);
                    routingContext.response().addCookie(cookieWeb);*/
                   // httpServerRequest.("/");
                    routingContext.response().end(AuthCode.failed(AuthCode.Code.CONNEXION_SUCCES, "Success connection"));
                }else
                    routingContext.response().end(AuthCode.failed(AuthCode.Code.BAD_PASS_OR_USER, "Mots de passe incorrect"));
            } catch (SQLException e) {
                routingContext.response().end(AuthCode.failed(AuthCode.Code.ERROR_SERVER, "Error database"));
            }
        }else
            routingContext.response().end(AuthCode.failed(AuthCode.Code.INVALID_ARGUMENT, "Invalid argument"));
    }

}
