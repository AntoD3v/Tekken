package com.tekken.auth.api;

import com.tekken.auth.AuthCode;
import com.tekken.auth.AuthTekken;
import com.tekken.auth.crypt.Crypt;
import com.tekken.auth.crypt.Sha256;
import io.vertx.core.Handler;
import io.vertx.ext.web.Cookie;
import io.vertx.ext.web.RoutingContext;

import java.sql.SQLException;
import java.util.UUID;

public class handlePostLogout implements Handler<RoutingContext> {

    private final AuthTekken authTekken;
    private static Crypt crypt = new Sha256();

    public handlePostLogout(AuthTekken authTekken) {
        this.authTekken = authTekken;
    }


    @Override
    public void handle(RoutingContext routingContext) {
        Cookie cookie;
        if ((cookie = routingContext.getCookie("tekken_auth")) != null) {
            if (authTekken.getDatabase().getCache().getCaches().containsKey(cookie.getValue())) {
                authTekken.getDatabase().getCache().getCaches().remove(cookie.getValue());
                routingContext.removeCookie("tekken_auth");
                routingContext.response().end(AuthCode.failed(AuthCode.Code.CONNEXION_SUCCES, "Good"));
            }
        }
        routingContext.response().end(AuthCode.failed(AuthCode.Code.INVALID_FORMAT, "not connected"));
    }
}