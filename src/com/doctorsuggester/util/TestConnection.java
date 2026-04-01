package com.doctorsuggester.util;

import java.sql.Connection;

public class TestConnection {
    public static void main(String[] args) {
        Connection conn = DBConnection.getConnection();
        if (conn != null) {
            System.out.println("✅ Connected to doctor_suggester database!");
        } else {
            System.out.println("❌ Connection failed!");
        }
    }
}