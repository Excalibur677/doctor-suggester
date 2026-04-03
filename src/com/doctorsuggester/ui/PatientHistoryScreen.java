package com.doctorsuggester.ui;

import com.doctorsuggester.dao.SymptomDAO;
import com.doctorsuggester.dao.AppointmentDAO;
import com.doctorsuggester.util.UITheme;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class PatientHistoryScreen extends JFrame {

    private SymptomDAO symptomDAO = new SymptomDAO();
    private AppointmentDAO appointmentDAO = new AppointmentDAO();

    public PatientHistoryScreen(String patientName, 
                                 int patientId, int appointmentId) {
        setTitle("Patient - " + patientName);
        setSize(650, 550);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        getContentPane().setBackground(UITheme.BACKGROUND);

        // Header
        add(UITheme.createHeaderPanel("Patient: " + patientName),
            BorderLayout.NORTH);

        // Tabbed pane - Symptoms + Prescription
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setFont(UITheme.NORMAL_FONT);

        // ── Tab 1: Symptom History ──
        JPanel symptomPanel = new JPanel(new BorderLayout());
        symptomPanel.setBackground(UITheme.BACKGROUND);
        symptomPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        String[] symCols = {"Symptom", "Specialization", "Logged At"};
        DefaultTableModel symModel = new DefaultTableModel(symCols, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };

        JTable symTable = new JTable(symModel);
        symTable.setFont(UITheme.NORMAL_FONT);
        symTable.setRowHeight(28);
        symTable.getTableHeader().setBackground(UITheme.PRIMARY);
        symTable.getTableHeader().setForeground(Color.WHITE);
        symTable.getTableHeader().setFont(UITheme.HEADER_FONT);

        // Load symptoms
        List<String> symptoms = symptomDAO.getSymptomsByPatient(patientId);
        if (symptoms.isEmpty()) {
            symModel.addRow(new Object[]{
                "No symptoms recorded", "", ""});
        } else {
            for (String s : symptoms) {
                String[] parts = s.split(" \\| ");
                symModel.addRow(new Object[]{
                    parts[0],
                    parts.length > 1 ? parts[1] : "",
                    parts.length > 2 ? parts[2] : ""
                });
            }
        }

        symptomPanel.add(new JScrollPane(symTable), BorderLayout.CENTER);
        tabbedPane.addTab("Symptom History", symptomPanel);

        // ── Tab 2: Write Prescription ──
        JPanel prescPanel = new JPanel(new GridBagLayout());
        prescPanel.setBackground(UITheme.BACKGROUND);
        prescPanel.setBorder(BorderFactory.createEmptyBorder(15, 30, 15, 30));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Appointment ID (readonly)
        gbc.gridx = 0; gbc.gridy = 0;
        JLabel aptLabel = new JLabel("Appointment ID:");
        aptLabel.setFont(UITheme.NORMAL_FONT);
        prescPanel.add(aptLabel, gbc);

        gbc.gridx = 1;
        JTextField aptField = new JTextField(
                              String.valueOf(appointmentId));
        aptField.setEditable(false);
        aptField.setBackground(new Color(220, 220, 220));
        aptField.setFont(UITheme.NORMAL_FONT);
        prescPanel.add(aptField, gbc);

        // Medicine
        gbc.gridx = 0; gbc.gridy = 1;
        JLabel medLabel = new JLabel("Medicine:");
        medLabel.setFont(UITheme.NORMAL_FONT);
        prescPanel.add(medLabel, gbc);

        gbc.gridx = 1;
        JTextField medicineField = new JTextField(20);
        medicineField.setFont(UITheme.NORMAL_FONT);
        prescPanel.add(medicineField, gbc);

        // Dosage
        gbc.gridx = 0; gbc.gridy = 2;
        JLabel dosLabel = new JLabel("Dosage:");
        dosLabel.setFont(UITheme.NORMAL_FONT);
        prescPanel.add(dosLabel, gbc);

        gbc.gridx = 1;
        JTextField dosageField = new JTextField(20);
        dosageField.setFont(UITheme.NORMAL_FONT);
        dosageField.setToolTipText("e.g. 1 tablet twice a day");
        prescPanel.add(dosageField, gbc);

        // Notes
        gbc.gridx = 0; gbc.gridy = 3;
        JLabel notesLabel = new JLabel("Notes:");
        notesLabel.setFont(UITheme.NORMAL_FONT);
        prescPanel.add(notesLabel, gbc);

        gbc.gridx = 1;
        JTextArea notesArea = new JTextArea(4, 20);
        notesArea.setFont(UITheme.NORMAL_FONT);
        notesArea.setLineWrap(true);
        notesArea.setBorder(BorderFactory.createLineBorder(
                            new Color(180, 180, 180)));
        prescPanel.add(new JScrollPane(notesArea), gbc);

        // Save button
        gbc.gridx = 0; gbc.gridy = 4;
        gbc.gridwidth = 2;
        JButton saveBtn = UITheme.createButton(
                          "Save Prescription", UITheme.SUCCESS);
        prescPanel.add(saveBtn, gbc);

        // Status label
        gbc.gridy = 5;
        JLabel statusLabel = new JLabel("", SwingConstants.CENTER);
        statusLabel.setFont(UITheme.NORMAL_FONT);
        prescPanel.add(statusLabel, gbc);

        tabbedPane.addTab("Write Prescription", prescPanel);
        add(tabbedPane, BorderLayout.CENTER);

        // Save prescription action
        saveBtn.addActionListener(e -> {
            String medicine = medicineField.getText().trim();
            String dosage   = dosageField.getText().trim();
            String notes    = notesArea.getText().trim();

            if (medicine.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                    "Please enter medicine name!");
                return;
            }

            // Combine dosage into notes
            String fullNotes = "Dosage: " + dosage + 
                               (notes.isEmpty() ? "" : "\nNotes: " + notes);

            boolean saved = appointmentDAO.savePrescription(
                            appointmentId, medicine, fullNotes);

            if (saved) {
                statusLabel.setForeground(UITheme.SUCCESS);
                statusLabel.setText("Prescription saved successfully!");
                medicineField.setText("");
                dosageField.setText("");
                notesArea.setText("");
                JOptionPane.showMessageDialog(this,
                    "Prescription saved to database!");
            } else {
                statusLabel.setForeground(UITheme.DANGER);
                statusLabel.setText("Failed to save prescription!");
            }
        });

        setVisible(true);
    }
}