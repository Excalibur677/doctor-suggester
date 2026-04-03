package com.doctorsuggester.ui;

import com.doctorsuggester.dao.DoctorDAO;
import com.doctorsuggester.model.Doctor;
import com.doctorsuggester.util.SessionManager;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class AdminDashboard extends JFrame {

    private JTable doctorTable;
    private DoctorDAO doctorDAO = new DoctorDAO();

    public AdminDashboard() {
        setTitle("Admin Dashboard");
        setSize(750, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Header
        JLabel title = new JLabel("Admin Panel - All Doctors",
                                   SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 18));
        title.setBorder(BorderFactory.createEmptyBorder(15, 0, 10, 0));
        add(title, BorderLayout.NORTH);

        // Table
        String[] columns = {"Doctor ID", "Name", "Specialization",
                            "Experience", "Available Days"};
        DefaultTableModel model = new DefaultTableModel(columns, 0);
        doctorTable = new JTable(model);
        add(new JScrollPane(doctorTable), BorderLayout.CENTER);

        // Load all doctors
        List<Doctor> doctors = doctorDAO.getAllDoctors();
        for (Doctor d : doctors) {
            model.addRow(new Object[]{
                d.getDoctorId(),
                d.getName(),
                d.getSpecialization(),
                d.getExperience(),
                d.getAvailableDays()
            });
        }

        // Buttons
        JPanel btnPanel = new JPanel(new FlowLayout());
        JButton logoutBtn = new JButton("Logout");
        btnPanel.add(logoutBtn);
        add(btnPanel, BorderLayout.SOUTH);

        logoutBtn.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(this,
                "Are you sure you want to logout?",
                "Logout", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                SessionManager.clearSession();
                new WelcomeScreen().setVisible(true); // ← goes back to welcome
                dispose();
            }
        });
    }
}