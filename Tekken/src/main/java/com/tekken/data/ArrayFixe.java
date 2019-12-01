package com.tekken.data;

import java.util.ArrayList;

public class ArrayFixe<O> extends ArrayList<O> {

    private final String name;

    public ArrayFixe(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
