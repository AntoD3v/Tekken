package com.tekken.example;

import com.tekken.site.Controller;
import com.tekken.site.Request;
import com.tekken.site.Response;
import com.tekken.site.Website;

public class Home implements Website {

    public static int incre = 0;

    @Override
    public Response handler(Controller controller, Request request, Response response) {
        Home.incre = Home.incre + 1;
        response.setValue("username", "AntoZzz");
        response.setValue("vue", "Il y a eu "+Home.incre);
        return response;
    }
}
