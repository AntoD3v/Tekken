package com.tekken.auth.crypt;

public interface Crypt {

    String crypt(String text);
    boolean compare(String args1, String args2);

}
