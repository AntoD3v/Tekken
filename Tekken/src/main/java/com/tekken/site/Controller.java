package com.tekken.site;

import com.tekken.Option;
import com.tekken.modules.ModuleManager;
import com.tekken.site.data.MongoDB;
import com.tekken.site.data.Mysql;

public class Controller {

    private final Mysql mysql;
    private final ModuleManager moduleManager;

    public Controller(ModuleManager moduleManager) {
        this.moduleManager = moduleManager;
        mysql = new Mysql(Option.MYSQL_POOL);
    }

    public Mysql mysql(){
        return mysql;
    }

    public MongoDB mongodb(){
        return null;
    }

    public ModuleManager getModuleManager() {
        return moduleManager;
    }
}
