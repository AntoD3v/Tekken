package com.tekken.site;

import com.tekken.support.Logs;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class Response {

    private String htmlCode;

    public Response(String htmlCode) {
        this.htmlCode = htmlCode;
    }

    public void setValue(String key, String value){
        htmlCode = htmlCode.replace("{"+key+"}", value);
    }

    public void display(String name, boolean isDisplay){
        Document parser = Jsoup.parse(htmlCode);
        Elements select = parser.select("*[displayIf=" + name + "]");
        if(isDisplay)
            select.removeAttr("displayIf");
        else{
            select.remove();
        }
        htmlCode = parser.toString();
    }

    public void redirect(Request request){
        request.getRoutingContext().reroute("/");
    }

    public String getHtmlCode() {
        return htmlCode;
    }
}
