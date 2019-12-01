package com.tekken.data.cache;

public abstract class Cache<T> {

    private T object;
    private int age = -1;

    public Cache(){

    }

    public Cache(int age) {
        this.age = age;
    }

    public T get(){
        return object;
    }

    public void set(T o){
        object = o;
    }

    public abstract T updater();

}
