package com.tekken.auth.session;

import java.util.concurrent.ConcurrentHashMap;

public class SessionManager {

    public ConcurrentHashMap<String, Session> sessions = new ConcurrentHashMap<>();

    public void add(Userdata userdata, String cookie){
        sessions.put(cookie, new Session(userdata, cookie));
    }

    public void remove(String cookie){
        sessions.remove(cookie);
    }

    public Session get(String cookie){
        if(!sessions.containsKey(cookie))
            sessions.put(cookie, new Session(cookie));
        return sessions.get(cookie);
    }

}
