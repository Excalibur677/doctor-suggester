package com.doctorsuggester.dao;

import com.doctorsuggester.model.Symptom;
import com.doctorsuggester.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SymptomDAO {

    // Get all symptoms
    public List<Symptom> getAllSymptoms() {
        List<Symptom> list = new ArrayList<>();
        String sql = "SELECT * FROM symptoms";
        try {
            Statement st = DBConnection.getConnection().createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                list.add(new Symptom(
                    rs.getInt("symptom_id"),
                    rs.getString("symptom_name"),
                    rs.getString("specialization")
                ));
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
        return list;
    }

    // Get specialization by symptom name
    public String getSpecialization(String symptomName) {
        String sql = "SELECT specialization FROM symptoms WHERE symptom_name = ?";
        try {
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ps.setString(1, symptomName);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getString("specialization");
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
        return null;
    }

    // Log patient symptoms
    public boolean logSymptom(int patientId, int symptomId) {
        String sql = "INSERT INTO patient_symptoms (patient_id, symptom_id) VALUES (?, ?)";
        try {
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ps.setInt(1, patientId);
            ps.setInt(2, symptomId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
            return false;
        }
    }
}