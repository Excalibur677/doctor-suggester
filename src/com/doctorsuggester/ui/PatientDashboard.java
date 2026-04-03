package com.doctorsuggester.ui;

import com.doctorsuggester.dao.AppointmentDAO;
import com.doctorsuggester.model.Appointment;
import com.doctorsuggester.util.SessionManager;
import com.doctorsuggester.util.UITheme;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.util.List;

public class PatientDashboard extends JFrame {

    private JTable appointmentTable;
    private AppointmentDAO appointmentDAO = new AppointmentDAO();

    public PatientDashboard() {
        setTitle("Patient Dashboard");
        setSize(900, 550);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        getContentPane().setBackground(UITheme.BACKGROUND);

        // Header
        add(UITheme.createHeaderPanel("Welcome, " + SessionManager.getUserName()), BorderLayout.NORTH);

        // Table
        String[] columns = {"ID", "Doctor", "Date", "Status"};
        DefaultTableModel model = new DefaultTableModel(columns, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };
        appointmentTable = new JTable(model);
        appointmentTable.setFont(UITheme.NORMAL_FONT);
        appointmentTable.setRowHeight(30);
        appointmentTable.setGridColor(new Color(189, 195, 199));

        JTableHeader header = appointmentTable.getTableHeader();
        header.setBackground(UITheme.PRIMARY);
        header.setForeground(Color.WHITE);
        header.setFont(UITheme.HEADER_FONT);

        JScrollPane scrollPane = new JScrollPane(appointmentTable);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(scrollPane, BorderLayout.CENTER);

        // Buttons
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        btnPanel.setBackground(UITheme.BACKGROUND);
        
        JButton symptomBtn      = UITheme.createButton("Check Symptoms", UITheme.PRIMARY);
        JButton refreshBtn      = UITheme.createButton("Refresh", UITheme.WARNING);
        JButton prescriptionBtn = UITheme.createButton("Get Prescription", UITheme.SUCCESS);
        JButton logoutBtn = UITheme.createButton("Logout", UITheme.DANGER);
        btnPanel.add(logoutBtn);

        btnPanel.add(symptomBtn);
        btnPanel.add(refreshBtn);
        btnPanel.add(prescriptionBtn);
        btnPanel.add(logoutBtn);
        add(btnPanel, BorderLayout.SOUTH);

        // Load data
        loadAppointments(model);

        // Actions
        symptomBtn.addActionListener(e -> {
            new SymptomScreen().setVisible(true);
            dispose();
        });
        logoutBtn.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(this,
                "Are you sure you want to logout?",
                "Logout", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                SessionManager.clearSession();
                new WelcomeScreen().setVisible(true);
                dispose();
            }
        });

        refreshBtn.addActionListener(e -> loadAppointments(model));

        prescriptionBtn.addActionListener(e -> {
            int row = appointmentTable.getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(this,
                    "Please select an appointment first!");
                return;
            }
            String doctorName = model.getValueAt(row, 1).toString();
            String date       = model.getValueAt(row, 2).toString();
            new PrescriptionScreen(doctorName, date).setVisible(true);
        });

        logoutBtn.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(this,
                "Are you sure you want to logout?",
                "Logout", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                SessionManager.clearSession();
                new WelcomeScreen().setVisible(true); // ← goes back to welcome
                dispose();
            }
        });    }

    private void loadAppointments(DefaultTableModel model) {
        model.setRowCount(0);
        List<Appointment> list = appointmentDAO
            .getAppointmentsByPatient(SessionManager.getUserId());
        for (Appointment a : list) {
            model.addRow(new Object[]{
                a.getAppointmentId(),
                a.getDoctorName(),
                a.getAppointmentDate(),
                a.getStatus()
            });
        }
    }
}