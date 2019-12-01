package com.tekken.shopper.data;

public class SubCategorie extends Categorie {
    private final String name;

    public SubCategorie(Categorie categorie, String name) {
        super(categorie);
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }
}
