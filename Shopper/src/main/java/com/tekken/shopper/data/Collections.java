package com.tekken.shopper.data;

import com.tekken.data.MapFixe;
import com.tekken.shopper.data.format.Categorie;
import com.tekken.shopper.data.format.Item;

public class Collections {

    private MapFixe<String, Item> collections = new MapFixe<>("Collection");
    private MapFixe<String, Categorie> categories = new MapFixe<>("Collection");

    public Collections() {

    }

    public void addItem(Item item, Categorie categorie){
        collections.put(item.getName(), item);
        categories.put(item.getName(), categorie);
    }

    public void toArrayList(){

    }


}
