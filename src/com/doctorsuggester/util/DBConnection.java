package com.doctorsuggester.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/doctor_suggester";
    private static final String USER = "root";
    private static final String PASSWORD = "attackontitan"; 

    private static Connection connection = null;

    public static Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
            }
        } catch (SQLException e) {
            System.out.println("DB Connection Failed: " + e.getMessage());
        }
        return connection;
    }
}