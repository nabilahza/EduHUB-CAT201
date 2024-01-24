package com.example.schoolmngmt;

import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseConnection {
    public Connection databaseLink;

    public Connection getConnection() {
        String databaseName ="eduhub";
        String databaseUser ="root";
        String databasePassword ="Umiayah0106";
        String url ="jdbc:mysql://localhost:3306/" + databaseName;

        try {
            Class.forName("com.mysql.jdbc.Driver");
            databaseLink = DriverManager.getConnection(url,databaseUser,databasePassword);
            if (databaseLink!=null){
                System.out.println("Success");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return databaseLink;
    }
}
