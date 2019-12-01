package com.tekken.auth;

import com.tekken.auth.session.Session;
import io.vertx.ext.web.RoutingContext;

import java.util.Set;

public class AuthCookie {

    private final Session session;

    public AuthCookie(String cookieAuth) {
        session = AuthTekken.getSessionManager().get(cookieAuth);
    }

    public AuthCookie(RoutingContext context) {
        if(context.getCookie("tekken_auth") != null)
            session = AuthTekken.getSessionManager().get(context.getCookie("tekken_auth").getValue());
        else
            session = null;
    }

    public Session getSession() {
        return session;
    }

    public boolean isConnected(){
        return session != null;
    }
}
