package com.tekken.shopper.data.format;

public class Categorie {

    private final String name;

    public Categorie(String name) {
        this.name = name;
    }

    public Categorie(Categorie categorie) {
        this.name = categorie.getName();
    }

    public String getName() {
        return name;
    }
}
