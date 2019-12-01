package com.tekken.auth.connection;

import com.tekken.site.data.Callback;
import com.tekken.site.data.Mysql;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Database {

    private Mysql mysql;
    private Cache cache = new Cache();

    public Database(Mysql mysql) {
        this.mysql = mysql;
    }

    public boolean login(String user, String passCrypted) throws SQLException {
        String truePass;
        if(!cache.getCaches().containsKey(user)){
            String sql = "SELECT pass FROM tauth_clients WHERE user = ? AND  pass = ?";
            PreparedStatement prepare = mysql.getConnection().prepareStatement(sql);
            prepare.setString(1, user);
            prepare.setString(2, passCrypted);
            ResultSet resultSet = prepare.executeQuery();
            if(resultSet.next()){
                cache.getCaches().put(user, passCrypted);
                return true;
            }else
                return false;
        }
        truePass = cache.getCaches().get(user);
        if(truePass.equalsIgnoreCase(passCrypted))
            return true;
        return false;
    }

    public boolean register(String user, String passCrypted) throws SQLException {
        if(!cache.getCaches().containsKey(user)){
            String sql = "SELECT user FROM tauth_clients WHERE user = ?";
            PreparedStatement prepare = mysql.getConnection().prepareStatement(sql);
            prepare.setString(1, user);
            ResultSet resultSet = prepare.executeQuery();
            if(resultSet.next())
                return false;
            String sql1 = "INSERT INTO tauth_clients(user, pass) VALUES(?, ?)";
            PreparedStatement prepare1 = mysql.getConnection().prepareStatement(sql1);
            prepare1.setString(1, user);
            prepare1.setString(2, passCrypted);
            prepare.executeUpdate();
            cache.getCaches().put(user, passCrypted);
            return true;
        }
        return false;
    }

    public Cache getCache() {
        return cache;
    }
}
