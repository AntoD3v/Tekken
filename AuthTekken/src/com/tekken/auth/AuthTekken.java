package com.tekken.auth;

import com.tekken.auth.api.handlePostLogin;
import com.tekken.auth.api.handlePostLogout;
import com.tekken.auth.api.handlePostRegister;
import com.tekken.auth.connection.Database;
import com.tekken.auth.session.SessionManager;
import com.tekken.modules.JavaModule;

public class AuthTekken extends JavaModule {

    private final AuthTekken auth;
    private static final SessionManager sessionManager = new SessionManager();
    private Database database;

    public AuthTekken() {
        super("AuthTeken", "AntoZzz", "1.0");
        this.auth = this;
    }

    @Override
    public void onLoad() {
    }

    @Override
    public void onReady() {
        database = new Database(getTekken().getController().mysql());
        getRouters().post("/auth/login").handler(new handlePostLogin(auth));
        getRouters().post("/auth/register").handler(new handlePostRegister(auth));
        getRouters().post("/auth/logout").handler(new handlePostLogout(auth));
    }

    @Override
    public void onStop() {

    }

    public AuthTekken getAuth() {
        return auth;
    }

    public Database getDatabase() {
        return database;
    }

    public static SessionManager getSessionManager() {
        return sessionManager;
    }
}
