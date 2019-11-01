package com.tekken.example;

import com.tekken.site.Controller;
import com.tekken.site.Request;
import com.tekken.site.Response;
import com.tekken.site.Website;
import com.tekken.support.Benchmark;
import com.tekken.support.Logs;

import java.sql.SQLException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class PageSQL implements Website {

    @Override
    public Response handler(Controller controller, Request request, Response response) {
        Benchmark.begin("mysql-bench");
        try {
            CountDownLatch latch = new CountDownLatch(1);
            controller.mysql().execute("SELECT * FROM auth_clients WHERE pseudonyme = ?", resultset -> {
                try {
                    resultset.next();
                    response.setValue("valor", resultset.getString("pseudonyme"));
                } catch (SQLException e) {
                    e.printStackTrace();
                    response.setValue("valor", "nop");
                }
                latch.countDown();
            }, "AntoZzz");

            if(latch.await(5L, TimeUnit.SECONDS)){
                Benchmark.stopAndResult("mysql-bench");
                return response;
            }else{
                Logs.warn("Request mysql for "+request.getPath()+" is lose");
                return response;
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return response;
    }
}
