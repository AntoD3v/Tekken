package com.pouletnetwork.backends;

import com.tekken.auth.AuthCookie;
import com.tekken.site.Controller;
import com.tekken.site.Request;
import com.tekken.site.Response;
import com.tekken.site.Website;

public class Header implements Website {
    @Override
    public Response handler(Controller controller, Request request, Response response) {
        AuthCookie auth = new AuthCookie(request.getRoutingContext());
        if(auth.isConnected()){
            response.setValue("user", auth.getSession().getUserdata().getUsername());
        }
        response.display("isLogin", auth.isConnected());
        return response;
    }
}
