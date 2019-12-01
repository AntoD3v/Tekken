package com.tekken.auth.backend;

import com.tekken.auth.AuthCookie;
import com.tekken.site.Controller;
import com.tekken.site.Request;
import com.tekken.site.Response;
import com.tekken.site.Website;

public class Register implements Website {

    @Override
    public Response handler(Controller controller, Request request, Response response) {
        AuthCookie authCookie = new AuthCookie(request.getRoutingContext());
        if(authCookie.isConnected())
            response.redirect(request);
        return response;
    }

}
