package com.tekken.support;

import java.util.HashMap;
import java.util.Map;

public class Benchmark {

    private static Map<String, Long> benchmarks = new HashMap<>();

    public static void begin(String name){
        benchmarks.put(name, System.currentTimeMillis());
    }

    public static void stop(String name){
        benchmarks.put(name, (System.currentTimeMillis() -  benchmarks.get(name)));
    }

    public static void stopAndResult(String name){
        long time = (System.currentTimeMillis() - benchmarks.get(name));
        benchmarks.put(name, time);
        Logs.debug("Benchmark "+name+" in "+time+" ms");
    }

    public static void result(String name){
        Logs.debug("Benchmark "+name+" in "+benchmarks.get(name)+" ms");
    }

}
