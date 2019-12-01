package com.tekken.shopper;

import com.tekken.modules.JavaModule;
import com.tekken.data.MapFixe;
import com.tekken.shopper.data.format.Item;

public class Shopper extends JavaModule {

    private MapFixe<String, Item> collections = new MapFixe<>("Collection");

    public Shopper() {
        super("Shopper", "AntoZzz", "1.0");
    }

    @Override
    public void onLoad() {
//        getRouters().get("/shopper/_ipn/paypal").handler(new handlerPaypal());
    }

    @Override
    public void onStop() {

    }

    public MapFixe<String, Item> getCollections() {
        return collections;
    }
}
