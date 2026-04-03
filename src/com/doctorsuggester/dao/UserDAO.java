package com.doctorsuggester.dao;

import com.doctorsuggester.model.User;
import com.doctorsuggester.model.Patient;
import com.doctorsuggester.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {

    // Register new patient
    public boolean registerPatient(String name, String email, String password, String phone) {
        String sql = "INSERT INTO users (name, email, password, role, phone) VALUES (?, ?, ?, 'patient', ?)";
        try {
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ps.setString(1, name);
            ps.setString(2, email);
            ps.setString(3, password);
            ps.setString(4, phone);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Register Error: " + e.getMessage());
            return false;
        }
    }

    // Login - returns role if valid, null if invalid
    public String[] login(String email, String password) {
        String sql = "SELECT user_id, name, role FROM users WHERE email = ? AND password = ?";
        try {
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ps.setString(1, email);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new String[]{
                    rs.getString("user_id"),
                    rs.getString("name"),
                    rs.getString("role")
                };
            }
        } catch (SQLException e) {
            System.out.println("Login Error: " + e.getMessage());
        }
        return null;
    }

    // Get user by ID
    public String getNameById(int userId) {
        String sql = "SELECT name FROM users WHERE user_id = ?";
        try {
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getString("name");
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
        return null;
    }
    
 // Get patient user_id from appointment
    public int getPatientIdByAppointment(int appointmentId) {
        String sql = "SELECT patient_id FROM appointments WHERE appointment_id = ?";
        try {
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ps.setInt(1, appointmentId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getInt("patient_id");
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
        return -1;
    }
    
}