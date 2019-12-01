package com.tekken.shopper.data;

public class Item {

    private final Categorie categorie;
    private final String name;
    private final Double price;

    public Item(Categorie categorie, String name, Double price) {
        this.categorie = categorie;
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public Categorie getCategorie() {
        return categorie;
    }

    public Double getPrice() {
        return price;
    }
}
