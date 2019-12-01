package com.tekken.auth.api;

import com.tekken.auth.AuthCode;
import com.tekken.auth.AuthTekken;
import com.tekken.auth.crypt.Crypt;
import com.tekken.auth.crypt.Sha256;
import com.tekken.auth.session.Userdata;
import io.vertx.core.Handler;
import io.vertx.ext.web.Cookie;
import io.vertx.ext.web.RoutingContext;

import java.sql.SQLException;
import java.util.Random;
import java.util.UUID;

public class handlePostRegister implements Handler<RoutingContext> {

    private final AuthTekken authTekken;
    private static Crypt crypt = new Sha256();

    public handlePostRegister(AuthTekken authTekken) {
        this.authTekken = authTekken;
    }


    @Override
    public void handle(RoutingContext routingContext) {
        String user, pass;
        if((user = routingContext.request().getParam("user")) != null && (pass = routingContext.request().getParam("pass")) != null){
            String passCrypted = crypt.crypt(pass);
            try {
                if(authTekken.getDatabase().register(user, passCrypted)){

                    String cookie = "tekken_" + UUID.randomUUID().toString();
                    authTekken.getSessionManager().add(new Userdata(new Random().nextInt(1000), user), cookie);

                    Cookie cookieWeb = Cookie.cookie("tekken_auth",cookie);
                    cookieWeb.setPath("/");
                    cookieWeb.setMaxAge(158132000l);
                    routingContext.addCookie(cookieWeb);
                    routingContext.response().end(AuthCode.failed(AuthCode.Code.CONNEXION_SUCCES, "Success registing"));
                }else
                    routingContext.response().end(AuthCode.failed(AuthCode.Code.BAD_PASS_OR_USER, "Ce compte existe déja"));
            } catch (SQLException e) {
                e.printStackTrace();
                routingContext.response().end(AuthCode.failed(AuthCode.Code.ERROR_SERVER, "Error database"));
            }
        }else
            routingContext.response().end(AuthCode.failed(AuthCode.Code.INVALID_ARGUMENT, "Invalid argument"));
    }
}
