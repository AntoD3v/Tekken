package com.tekken.data;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;

public class MapFixe<K, V> extends HashMap<K, V> {

    private final String name;

    public MapFixe(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
