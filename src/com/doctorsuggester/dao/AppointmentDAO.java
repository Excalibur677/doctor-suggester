package com.doctorsuggester.dao;

import com.doctorsuggester.model.Appointment;
import com.doctorsuggester.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AppointmentDAO {

    // Book appointment (uses Transaction ✅)
    public boolean bookAppointment(int patientId, int doctorId, String date) {
        Connection conn = DBConnection.getConnection();
        try {
            conn.setAutoCommit(false); // Start transaction

            String sql = "INSERT INTO appointments (patient_id, doctor_id, appointment_date) VALUES (?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, patientId);
            ps.setInt(2, doctorId);
            ps.setString(3, date);
            ps.executeUpdate();

            conn.commit(); // Commit transaction
            conn.setAutoCommit(true);
            return true;
        } catch (SQLException e) {
            try { conn.rollback(); } catch (SQLException ex) {}
            System.out.println("Booking Error: " + e.getMessage());
            return false;
        }
    }

    // Get appointments by patient
    public List<Appointment> getAppointmentsByPatient(int patientId) {
        List<Appointment> list = new ArrayList<>();
        String sql = "SELECT a.appointment_id, a.appointment_date, a.status, " +
                     "u.name AS doctor_name FROM appointments a " +
                     "JOIN doctors d ON a.doctor_id = d.doctor_id " +
                     "JOIN users u ON d.user_id = u.user_id " +
                     "WHERE a.patient_id = ?";
        try {
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ps.setInt(1, patientId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Appointment apt = new Appointment(patientId, 0, rs.getString("appointment_date"));
                apt.setAppointmentId(rs.getInt("appointment_id"));
                apt.setDoctorName(rs.getString("doctor_name"));
                apt.setStatus(Appointment.Status.valueOf(rs.getString("status").toUpperCase()));
                list.add(apt);
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
        return list;
    }

    // Get appointments by doctor
    public List<Appointment> getAppointmentsByDoctor(int doctorId) {
        List<Appointment> list = new ArrayList<>();
        String sql = "SELECT a.appointment_id, a.appointment_date, a.status, " +
                     "u.name AS patient_name FROM appointments a " +
                     "JOIN users u ON a.patient_id = u.user_id " +
                     "WHERE a.doctor_id = ?";
        try {
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ps.setInt(1, doctorId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Appointment apt = new Appointment(0, doctorId, rs.getString("appointment_date"));
                apt.setAppointmentId(rs.getInt("appointment_id"));
                apt.setPatientName(rs.getString("patient_name"));
                apt.setStatus(Appointment.Status.valueOf(rs.getString("status").toUpperCase()));
                list.add(apt);
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
        return list;
    }

    // Update appointment status
    public boolean updateStatus(int appointmentId, String status) {
        String sql = "UPDATE appointments SET status = ? WHERE appointment_id = ?";
        try {
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ps.setString(1, status);
            ps.setInt(2, appointmentId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
            return false;
        }
    }
    
 // Save prescription to database
    public boolean savePrescription(int appointmentId, 
                                     String medicine, String notes) {
        String sql = "INSERT INTO prescriptions " +
                     "(appointment_id, medicine, notes) VALUES (?, ?, ?)";
        try {
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ps.setInt(1, appointmentId);
            ps.setString(2, medicine);
            ps.setString(3, notes);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Prescription Error: " + e.getMessage());
            return false;
        }
    }
}
