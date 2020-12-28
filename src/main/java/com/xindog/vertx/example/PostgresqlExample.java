package com.xindog.vertx.example;

import io.vertx.pgclient.PgConnectOptions;
import io.vertx.pgclient.PgPool;
import io.vertx.sqlclient.PoolOptions;
import io.vertx.sqlclient.Row;
import io.vertx.sqlclient.RowSet;
import lombok.extern.slf4j.Slf4j;

/**
 * Created by Xindog.com(TM).
 * Author:  Shawn.Wang / i.am@shawn.wang
 * Date:    12/28/20
 * Time:    2:46 PM
 * Project: learn
 */

@Slf4j
public class PostgresqlExample {
    public static void main(String[] args) {
//        PgConnectOptions connectOptions = new PgConnectOptions()
//                .setPort(5432)
//                .setHost("localhost")
//                .setDatabase("dev")
//                .setUser("dev")
//                .setPassword("dev");

        PgConnectOptions connectOptions = PgConnectOptions.fromUri("postgresql://dev:dev@localhost:5432/dev");
// Pool options
        PoolOptions poolOptions = new PoolOptions()
                .setMaxSize(5);

// Create the client pool
        PgPool client = PgPool.pool(connectOptions, poolOptions);

// A simple query
        client.query("SHOW max_connections;").execute(ar -> {
            if (ar.succeeded()) {
                RowSet<Row> result = ar.result();
                log.warn("Got " + result.size() + " rows ");
            } else {
                log.warn("Failure: " + ar.cause().getMessage());
            }

            // Now close the pool
            client.close();
        });
    }
}
