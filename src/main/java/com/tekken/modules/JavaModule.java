package com.tekken.modules;

import com.tekken.support.Logs;

public abstract class JavaModule {

    private final String moduleName;
    private final String version;

    public JavaModule(String moduleName, String version){
        this.moduleName = moduleName;
        this.version = version;
    }

    public void onLoad(){
        Logs.info("Module ["+moduleName+"] is loaded !");
    }

    public void onReady(){

    }

    public void onStop(){
        Logs.info("Module ["+moduleName+"] is stopped !");
    }

}
