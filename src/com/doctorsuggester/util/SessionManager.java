package com.doctorsuggester.util;

public class SessionManager {

    private static int userId;
    private static String userName;
    private static String userRole;

    public static void setSession(int id, String name, String role) {
        userId = id;
        userName = name;
        userRole = role;
    }

    public static int getUserId() { return userId; }
    public static String getUserName() { return userName; }
    public static String getUserRole() { return userRole; }

    public static void clearSession() {
        userId = 0;
        userName = null;
        userRole = null;
    }

    public static boolean isLoggedIn() {
        return userName != null;
    }
}