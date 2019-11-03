package com.tekken.example;

import com.tekken.site.Controller;
import com.tekken.site.Request;
import com.tekken.site.Response;
import com.tekken.site.Website;

public class Header implements Website {
    @Override
    public Response handler(Controller controller, Request request, Response response) {

        response.setValue("user", "AntoZzz");

        return response;
    }
}
