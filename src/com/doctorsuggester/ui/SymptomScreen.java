package com.doctorsuggester.ui;

import com.doctorsuggester.dao.SymptomDAO;
import com.doctorsuggester.model.Symptom;
import com.doctorsuggester.service.SymptomAnalyzer;
import com.doctorsuggester.util.InvalidSymptomException;
import com.doctorsuggester.util.DoctorNotFoundException;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class SymptomScreen extends JFrame {

    private List<JCheckBox> checkBoxes = new ArrayList<>();
    private SymptomDAO symptomDAO = new SymptomDAO();
    private SymptomAnalyzer analyzer = new SymptomAnalyzer();

    public SymptomScreen() {
        setTitle("Select Your Symptoms");
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Title
        JLabel title = new JLabel("Select all symptoms you have:",
                                   SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 16));
        title.setBorder(BorderFactory.createEmptyBorder(15, 0, 10, 0));
        add(title, BorderLayout.NORTH);

        // Symptom checkboxes
        JPanel checkPanel = new JPanel(new GridLayout(0, 2, 5, 5));
        checkPanel.setBorder(BorderFactory.createEmptyBorder(10, 30, 10, 30));

        List<Symptom> symptoms = symptomDAO.getAllSymptoms();
        for (Symptom s : symptoms) {
            JCheckBox cb = new JCheckBox(s.getSymptomName());
            cb.setActionCommand(String.valueOf(s.getSymptomId()));
            checkBoxes.add(cb);
            checkPanel.add(cb);
        }

        add(new JScrollPane(checkPanel), BorderLayout.CENTER);

        // Buttons
        JPanel btnPanel = new JPanel(new FlowLayout());
        JButton analyzeBtn = new JButton("🔍 Find Doctor");
        JButton backBtn    = new JButton("Back");

        analyzeBtn.setBackground(new Color(70, 130, 180));
        analyzeBtn.setForeground(Color.WHITE);

        btnPanel.add(analyzeBtn);
        btnPanel.add(backBtn);
        add(btnPanel, BorderLayout.SOUTH);

        // Analyze action
        analyzeBtn.addActionListener(e -> {
            List<String> selected = new ArrayList<>();
            List<Integer> selectedIds = new ArrayList<>();

            for (JCheckBox cb : checkBoxes) {
                if (cb.isSelected()) {
                    selected.add(cb.getText());
                    selectedIds.add(Integer.parseInt(cb.getActionCommand()));
                }
            }

            try {
                String specialization = analyzer.analyze(selected);
                new DoctorListScreen(specialization, selectedIds).setVisible(true);
                dispose();
            } catch (InvalidSymptomException | DoctorNotFoundException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage());
            }
        });

        backBtn.addActionListener(e -> {
            new PatientDashboard().setVisible(true);
            dispose();
        });
    }
}