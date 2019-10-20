package com.tekken.site;

import com.tekken.Option;
import com.tekken.site.data.MongoDB;
import com.tekken.site.data.Mysql;

public class Controller {

    private final Mysql mysql;

    public Controller() {
        mysql = new Mysql(Option.MYSQL_POOL);
    }

    public Mysql mysql(){
        return mysql;
    }

    public MongoDB mongodb(){
        return null;
    }
}
