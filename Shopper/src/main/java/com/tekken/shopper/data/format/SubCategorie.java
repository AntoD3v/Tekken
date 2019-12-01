package com.tekken.shopper.data.format;

import com.tekken.shopper.data.format.Categorie;

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
