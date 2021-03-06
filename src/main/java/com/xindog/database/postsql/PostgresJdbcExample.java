package com.xindog.database.postsql;

import lombok.extern.slf4j.Slf4j;

import java.sql.*;
import java.util.concurrent.CompletableFuture;

/**
 * Created by Xindog.com(TM).
 * Author:  Shawn.Wang / i.am@shawn.wang
 * Date:    12/28/20
 * Time:    3:59 PM
 * Project: learn
 */
@Slf4j
public class PostgresJdbcExample {

    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        Runnable run = () -> {
            try {
                Class.forName("org.postgresql.Driver");
                String url = "jdbc:postgresql://localhost:5432/dev?user=dev&password=dev";
                Connection conn = DriverManager.getConnection(url);
                Statement st = conn.createStatement();
                ResultSet rs = st.executeQuery("SELECT sum(numbackends) FROM pg_stat_database;");
                while (rs.next()) {
                    log.warn(rs.getString(1));
                }
                rs.close();
                st.close();
            } catch (Throwable e) {
                e.printStackTrace();
            }
        };

        CompletableFuture.runAsync(run).join();
        CompletableFuture.runAsync(run).join();

    }
}
