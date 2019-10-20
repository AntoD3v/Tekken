package com.tekken.example;

import com.tekken.Tekken;
import com.tekken.site.Controller;
import com.tekken.site.Request;
import com.tekken.site.Response;
import com.tekken.site.Website;
import com.tekken.support.Logs;

public class Home implements Website {

    @Override
    public Response handler(Controller controller, Request request, Response response) {

        response.setValue("username", "AntoZzz");
        return response;
    }
}
