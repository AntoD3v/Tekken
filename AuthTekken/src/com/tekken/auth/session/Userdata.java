package com.tekken.auth.session;

public class Userdata {

    private int id;
    private String username;

    public Userdata(int id, String username) {
        this.id = id;
        this.username = username;
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }
}
