package com.doctorsuggester.ui;

import com.doctorsuggester.dao.DoctorDAO;
import com.doctorsuggester.model.Doctor;
import com.doctorsuggester.service.AppointmentService;
import com.doctorsuggester.service.ReminderService;
import com.doctorsuggester.util.SessionManager;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class DoctorListScreen extends JFrame {

    private JList<String> doctorList;
    private List<Doctor> doctors;
    private List<Integer> symptomIds;
    private DoctorDAO doctorDAO = new DoctorDAO();
    private AppointmentService appointmentService = new AppointmentService();

    public DoctorListScreen(String specialization, List<Integer> symptomIds) {
        this.symptomIds = symptomIds;

        setTitle("Suggested Doctors - " + specialization);
        setSize(500, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Title
        JLabel title = new JLabel("Recommended: " + specialization,
                                   SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 16));
        title.setForeground(new Color(70, 130, 180));
        title.setBorder(BorderFactory.createEmptyBorder(15, 0, 10, 0));
        add(title, BorderLayout.NORTH);

        // Doctor list
        doctors = doctorDAO.getDoctorsBySpecialization(specialization);
        DefaultListModel<String> listModel = new DefaultListModel<>();
        for (Doctor d : doctors) {
            listModel.addElement("Dr. " + d.getName() +
                                 " | Exp: " + d.getExperience() + " yrs" +
                                 " | Available: " + d.getAvailableDays());
        }
        doctorList = new JList<>(listModel);
        doctorList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        add(new JScrollPane(doctorList), BorderLayout.CENTER);

        // Buttons
        JPanel btnPanel = new JPanel(new FlowLayout());
        JButton bookBtn = new JButton("📅 Book Appointment");
        JButton backBtn = new JButton("Back");

        bookBtn.setBackground(new Color(60, 179, 113));
        bookBtn.setForeground(Color.WHITE);

        btnPanel.add(bookBtn);
        btnPanel.add(backBtn);
        add(btnPanel, BorderLayout.SOUTH);

        // Book action
        bookBtn.addActionListener(e -> {
            int selectedIndex = doctorList.getSelectedIndex();
            if (selectedIndex == -1) {
                JOptionPane.showMessageDialog(this, "Please select a doctor!");
                return;
            }

            String date = JOptionPane.showInputDialog(this,
                          "Enter appointment date (YYYY-MM-DD):");
            if (date == null || date.isEmpty()) return;

            Doctor selectedDoctor = doctors.get(selectedIndex);
            boolean success = appointmentService.bookWithSymptoms(
                SessionManager.getUserId(),
                selectedDoctor.getDoctorId(),
                date, symptomIds
            );

            if (success) {
                JOptionPane.showMessageDialog(this,
                    "✅ Appointment booked successfully!");
                // Start reminder thread ✅
                ReminderService.startReminder(SessionManager.getUserName(), date);
                new PatientDashboard().setVisible(true);
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "❌ Booking failed!");
            }
        });

        backBtn.addActionListener(e -> {
            new SymptomScreen().setVisible(true);
            dispose();
        });
    }
}