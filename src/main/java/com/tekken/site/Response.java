package com.tekken.site;

public class Response {

    private String htmlCode;

    public Response(String htmlCode) {
        this.htmlCode = htmlCode;
    }

    public void setValue(String key, String value){
        htmlCode = htmlCode.replace("{"+key+"}", value);
    }

    public String getHtmlCode() {
        return htmlCode;
    }
}
