package org.example.jobportal;

import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseConnection {
    public Connection databaseLink;

    public Connection getConnection() {
        String databaseName = "job";
        String databaseUser = "root";
        String databasePassword = "";
        String databaseUrl = "jdbc:mysql://localhost/" + databaseName;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            databaseLink = DriverManager.getConnection(databaseUrl, databaseUser, databasePassword);
        } catch (Exception e){
            e.printStackTrace();
        }
        System.out.println("Connected to database");
        return databaseLink;
    }
}
