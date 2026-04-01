package com.doctorsuggester.dao;

import com.doctorsuggester.model.Doctor;
import com.doctorsuggester.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DoctorDAO {

    // Get doctors by specialization
    public List<Doctor> getDoctorsBySpecialization(String specialization) {
        List<Doctor> list = new ArrayList<>();
        String sql = "SELECT u.user_id, u.name, u.phone, d.doctor_id, d.specialization, " +
                     "d.experience, d.available_days FROM doctors d " +
                     "JOIN users u ON d.user_id = u.user_id " +
                     "WHERE d.specialization = ?";
        try {
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ps.setString(1, specialization);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Doctor doc = new Doctor(
                    rs.getString("name"), "", "", rs.getString("phone"),
                    rs.getString("specialization"),
                    rs.getInt("experience"),
                    rs.getString("available_days")
                );
                doc.setDoctorId(rs.getInt("doctor_id"));
                doc.setUserId(rs.getInt("user_id"));
                list.add(doc);
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
        return list;
    }

    // Get all doctors
    public List<Doctor> getAllDoctors() {
        List<Doctor> list = new ArrayList<>();
        String sql = "SELECT u.name, u.phone, d.doctor_id, d.specialization, " +
                     "d.experience, d.available_days FROM doctors d " +
                     "JOIN users u ON d.user_id = u.user_id";
        try {
            Statement st = DBConnection.getConnection().createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                Doctor doc = new Doctor(
                    rs.getString("name"), "", "", rs.getString("phone"),
                    rs.getString("specialization"),
                    rs.getInt("experience"),
                    rs.getString("available_days")
                );
                doc.setDoctorId(rs.getInt("doctor_id"));
                list.add(doc);
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
        return list;
    }
}