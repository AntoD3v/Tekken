package com.tekken.auth.connection;

import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

public class Cache {

    public ConcurrentHashMap<String, String> caches = new ConcurrentHashMap<>();

    public ConcurrentHashMap<String, String> getCaches() {
        return caches;
    }
}
