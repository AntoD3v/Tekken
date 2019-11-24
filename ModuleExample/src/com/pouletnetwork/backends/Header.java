package com.pouletnetwork.backends;

import com.tekken.auth.AuthCookie;
import com.tekken.site.Controller;
import com.tekken.site.Request;
import com.tekken.site.Response;
import com.tekken.site.Website;

public class Header implements Website {
    @Override
    public Response handler(Controller controller, Request request, Response response) {
        new AuthCookie(request.getRoutingContext());
        response.setValue("user", "AntoZzz");

        return response;
    }
}
