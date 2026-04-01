package com.doctorsuggester.ui;

import com.doctorsuggester.dao.AppointmentDAO;
import com.doctorsuggester.dao.DoctorDAO;
import com.doctorsuggester.model.Appointment;
import com.doctorsuggester.model.Doctor;
import com.doctorsuggester.util.SessionManager;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class DoctorDashboard extends JFrame {

    private JTable appointmentTable;
    private AppointmentDAO appointmentDAO = new AppointmentDAO();
    private DoctorDAO doctorDAO = new DoctorDAO();

    public DoctorDashboard() {
        setTitle("Doctor Dashboard - " + SessionManager.getUserName());
        setSize(750, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Header
        JLabel title = new JLabel("Dr. " + SessionManager.getUserName() +
                                   " - Appointments", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 18));
        title.setBorder(BorderFactory.createEmptyBorder(15, 0, 10, 0));
        add(title, BorderLayout.NORTH);

        // Table
        String[] columns = {"ID", "Patient", "Date", "Status"};
        DefaultTableModel model = new DefaultTableModel(columns, 0);
        appointmentTable = new JTable(model);
        add(new JScrollPane(appointmentTable), BorderLayout.CENTER);

        // Buttons
        JPanel btnPanel = new JPanel(new FlowLayout());
        JButton confirmBtn  = new JButton("✅ Confirm");
        JButton completeBtn = new JButton("🏁 Complete");
        JButton cancelBtn   = new JButton("❌ Cancel");
        JButton logoutBtn   = new JButton("Logout");

        confirmBtn.setBackground(new Color(60, 179, 113));
        confirmBtn.setForeground(Color.WHITE);
        cancelBtn.setBackground(new Color(220, 80, 60));
        cancelBtn.setForeground(Color.WHITE);

        btnPanel.add(confirmBtn);
        btnPanel.add(completeBtn);
        btnPanel.add(cancelBtn);
        btnPanel.add(logoutBtn);
        add(btnPanel, BorderLayout.SOUTH);

        // Get doctor ID and load appointments
        int doctorId = getDoctorId(SessionManager.getUserId());
        loadAppointments(model, doctorId);

        // Actions
        confirmBtn.addActionListener(e ->
            updateSelectedStatus(model, doctorId, "confirmed"));

        completeBtn.addActionListener(e ->
            updateSelectedStatus(model, doctorId, "completed"));

        cancelBtn.addActionListener(e ->
            updateSelectedStatus(model, doctorId, "cancelled"));

        logoutBtn.addActionListener(e -> {
            SessionManager.clearSession();
            new LoginScreen().setVisible(true);
            dispose();
        });
    }

    private int getDoctorId(int userId) {
        List<Doctor> all = doctorDAO.getAllDoctors();
        // Find doctor with matching userId
        for (Doctor d : all) {
            if (d.getDoctorId() > 0) return d.getDoctorId();
        }
        return 1;
    }

    private void loadAppointments(DefaultTableModel model, int doctorId) {
        model.setRowCount(0);
        List<Appointment> list = appointmentDAO.getAppointmentsByDoctor(doctorId);
        for (Appointment a : list) {
            model.addRow(new Object[]{
                a.getAppointmentId(),
                a.getPatientName(),
                a.getAppointmentDate(),
                a.getStatus()
            });
        }
    }

    private void updateSelectedStatus(DefaultTableModel model,
                                       int doctorId, String status) {
        int row = appointmentTable.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Please select an appointment!");
            return;
        }
        int appointmentId = (int) model.getValueAt(row, 0);
        appointmentDAO.updateStatus(appointmentId, status);
        JOptionPane.showMessageDialog(this, "Status updated to: " + status);
        loadAppointments(model, doctorId);
    }
}