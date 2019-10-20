package com.tekken.site;

import com.tekken.Tekken;

import java.util.concurrent.CountDownLatch;

public interface Website {


    public Response handler(Controller controller, Request request, Response response);
}
