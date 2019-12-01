package com.tekken;


import com.tekken.support.Updater;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;

import java.util.function.Consumer;

public class Tekken {

    public Tekken() {

        Updater.upgrade();
        Option.loadConfig();

        Consumer<Vertx> runner = vertx -> {
            for (int i=0;i < Option.VERTX_POOL; i++)
                vertx.deployVerticle(new Start(), new DeploymentOptions());
        };

        Vertx vertx = Vertx.vertx(new VertxOptions());
        runner.accept(vertx);

    }

    public static void main(String[] args){
        System.out.println( "       _____    _    _              \n" +
                            "      |_   _|  | |  | |             \n" +
                            "        | | ___| | _| | _____ _ __  \n" +
                            "        | |/ _ \\ |/ / |/ / _ \\ '_ \\ \n" +
                            "        | |  __/   <|   <  __/ | | |\n" +
                            "        \\_/\\___|_|\\_\\_|\\_\\___|_| |_| V-0.1\n" +
                            "                              By antozzz.fr \n" +
                            "");
        new Tekken();
    }


}
