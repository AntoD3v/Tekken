package com.pouletnetwork.backends;

import com.tekken.site.Controller;
import com.tekken.site.Request;
import com.tekken.site.Response;
import com.tekken.site.Website;

public class Home implements Website {
    @Override
    public Response handler(Controller controller, Request request, Response response) {
        response.setValue("account_register", "95");
        return response;
    }
}
