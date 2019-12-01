package com.tekken.auth;

public class AuthCode {

    public static String failed(Code code, String message){
        return "{\"code\": "+code.getCode()+", \"message\": \""+message+"\"}";
    }

    public enum Code{

        CONNEXION_SUCCES(200),
        INVALID_ARGUMENT(404),
        INVALID_FORMAT(400),
        BAD_PASS_OR_USER(401),
        ERROR_SERVER(500);

        private final int i;

        Code(int i) {
            this.i = i;
        }

        public int getCode() {
            return i;
        }
    }

}
