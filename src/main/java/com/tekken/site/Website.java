package com.tekken.site;

import com.tekken.Tekken;

public interface Website {

    public Response handler(Controller controller, Request request, Response response);
}
