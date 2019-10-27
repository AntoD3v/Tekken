package com.tekken.site.data;

import com.tekken.support.Logs;

import java.sql.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Mysql {


    private final Connection[] connections;
    private final ExecutorService executorService;
    private int position;

    public Mysql(int connectionSize) {

        connections = new Connection[connectionSize];
        executorService = Executors.newFixedThreadPool(connectionSize);

        new Thread(connection()).start();

    }

    private Runnable connection() {

        return new Runnable() {
            @Override
            public void run() {
                for(int i = 0; i < connections.length; i++) {
                    try {
                        connections[i] = DriverManager.getConnection(
                                "jdbc:mysql://antozzz.fr/obscurfight", "obscur", "azerty");
                    } catch (SQLException e) {
                        Logs.error("Connection #"+i+" is close", e);
                    }
                }
            }
        };

    }

    public void execute(String sql, Callback<ResultSet> callback, String... params){

        executorService.execute(() -> {
            try {
                final PreparedStatement prepare = getConnection().prepareStatement(sql);
                for (int i = 0; i < params.length; i++)
                    prepare.setString((i+1), params[i]);

                callback.completeFuture(prepare.executeQuery());
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }

    public Connection getConnection() {
        if(position >= connections.length - 1) position = 0;
        this.position++;
        return connections[position - 1];
    }

}
