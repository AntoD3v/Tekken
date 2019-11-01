package com.tekken.modules;

import com.tekken.Start;
import io.vertx.ext.web.Router;

public abstract class JavaModule {

    private final String moduleName;
    private final String version;
    private Start main;

    public JavaModule(String moduleName, String version){
        this.moduleName = moduleName;
        this.version = version;
    }

    public abstract void onLoad();

    public void onReady(){

    }

    public abstract void onStop();

    public String getModuleName() {
        return moduleName;
    }

    public String getVersion() {
        return version;
    }

    public void setMain(Start main) {
        this.main = main;
    }

    public Router getRouters(){
        return main.getRouters();
    }
}

