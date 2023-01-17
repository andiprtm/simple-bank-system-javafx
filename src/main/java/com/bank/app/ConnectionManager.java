package com.bank.app;

import java.sql.*;

public class ConnectionManager {
    private static ConnectionManager manager = null;

    private Connection connection = null;

    private ConnectionManager() {
        try {
            // load driver
            System.out.println("Loading driver...");
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("Loaded driver.");

            // setup connection with db
            System.out.println("Connecting to database...");
            connection = DriverManager.getConnection("jdbc:mysql://mysql.irvansn.com:3306/bank_system", "irvansn", "123hore");
            System.out.println("Successfully connected to database.");
        } catch (Exception e) {
            e.printStackTrace();

            // if failed to load driver/connection, exit application
            System.exit(1);
        }
    }

    public Connection getConnection() {
        return connection;
    }

    /**
     * Access to this class will be provided through this
     * singleton instance.
     */
    public static ConnectionManager getInstance() {
        if (manager == null) {
            manager = new ConnectionManager();
        }
        return manager;
    }

    /**
     * Close all connections
     */
    public void close() {
        try {
            if (connection != null) {
                connection.close();
            }

            if (manager != null) {
                manager = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
