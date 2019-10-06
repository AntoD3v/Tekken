package com.tekken;

import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;

import java.util.function.Consumer;

public class Tekken {

    public Tekken() {
/*
        cache = new Cache(redisHost, redisPort, poolInteger);
        replication = new Replication(poolInteger);
*/
        Consumer<Vertx> runner = vertx -> {
            //for (int i=0;i<poolInteger; i++)
                vertx.deployVerticle(new Start(), new DeploymentOptions());
        };

        Vertx vertx = Vertx.vertx(new VertxOptions());
        runner.accept(vertx);

    }

    public static void main(String[] args){
        System.out.println(" * Tekken (By AntoZzz)");
        new Tekken();
    }


}
