package com.tekken.auth;

import com.tekken.auth.api.handlePostLogin;
import com.tekken.modules.JavaModule;

public class AuthTekken extends JavaModule {

    private final AuthTekken auth;

    public AuthTekken() {
        super("AuthTeken", "AntoZzz", "1.0");
        this.auth = this;
    }

    @Override
    public void onLoad() {
    }

    @Override
    public void onReady() {

        getRouters().post("/auth/login").handler(new handlePostLogin(auth));
    }

    @Override
    public void onStop() {

    }

    public AuthTekken getAuth() {
        return auth;
    }
}
