package com.pouletnetwork;

import com.tekken.modules.JavaModule;
import com.tekken.shopper.Shopper;
import com.tekken.shopper.data.format.Categorie;
import com.tekken.shopper.data.format.Item;

public class PouletnetworkWebsite extends JavaModule {

    private Shopper shopper;

    public PouletnetworkWebsite() {
        super("Pouletnetwork", "AntoZzz", "1.0");
        shopper = (Shopper)getTekken().getModuleManager().getModule("Shopper");
    }

    @Override
    public void onLoad() {

    }

    @Override
    public void onReady() {

        Categorie categorieGrade = new Categorie("Grade");
        Categorie categorieHost = new Categorie("Host");

        shopper.getCollections().put("ChickensVIP", new Item(new Categorie("Example"), "Item #1", 9.99));
        shopper.getCollections().put("item1", new Item(new Categorie("Example"), "Item #2", 19.99));
    }

    @Override
    public void onStop() {

    }


}
