package com.tekken.auth.session;

import java.util.HashMap;
import java.util.Map;

public class Session {

    private Userdata userdata;
    private String cookieName;
    private Map<String, Object> storage = new HashMap<>();

    public Session(Userdata userdata, String cookieName) {
        this.cookieName = cookieName;
        this.userdata = userdata;
    }

    public Session(String cookieName) {
        this(null, cookieName);
    }

    public void addValue(String key, Object value){
        storage.put(key, value);
    }

    public void removeValue(String key){
        storage.remove(key);
    }

    public void getValue(String key){
        storage.get(key);
    }

    public Userdata getUserdata() {
        return userdata;
    }

    public Map<String, Object> getStorage() {
        return storage;
    }
}
