package com.tekken;

import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;

import java.util.function.Consumer;

public class Tekken {

    public Tekken() {

        Consumer<Vertx> runner = vertx -> {
            for (int i=0;i < Option.VERTX_POOL; i++)
                vertx.deployVerticle(new Start(), new DeploymentOptions());
        };

        Vertx vertx = Vertx.vertx(new VertxOptions());
        runner.accept(vertx);

    }

    public static void main(String[] args){
        System.out.println(" * Tekken 2019 (By AntoDev)");
        new Tekken();
    }


}
