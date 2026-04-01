package com.doctorsuggester.ui;

import com.doctorsuggester.util.PrescriptionExporter;
import com.doctorsuggester.util.SessionManager;
import com.doctorsuggester.util.UITheme;

import javax.swing.*;
import java.awt.*;

public class PrescriptionScreen extends JFrame {

    private JTextField doctorField, dateField, medicineField;
    private JTextArea notesArea;
    private JTextArea previewArea;

    public PrescriptionScreen(String doctorName, String date) {
        setTitle("Write Prescription");
        setSize(600, 550);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        getContentPane().setBackground(UITheme.BACKGROUND);

        // Header
        add(UITheme.createHeaderPanel("📋 Prescription"),
            BorderLayout.NORTH);

        // Form
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(UITheme.BACKGROUND);
        formPanel.setBorder(BorderFactory.createEmptyBorder(15, 30, 15, 30));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Doctor
        gbc.gridx = 0; gbc.gridy = 0;
        formPanel.add(new JLabel("Doctor:"), gbc);
        gbc.gridx = 1;
        doctorField = new JTextField(doctorName, 20);
        doctorField.setEditable(false);
        doctorField.setBackground(new Color(220, 220, 220));
        formPanel.add(doctorField, gbc);

        // Date
        gbc.gridx = 0; gbc.gridy = 1;
        formPanel.add(new JLabel("Date:"), gbc);
        gbc.gridx = 1;
        dateField = new JTextField(date, 20);
        dateField.setEditable(false);
        dateField.setBackground(new Color(220, 220, 220));
        formPanel.add(dateField, gbc);

        // Medicine
        gbc.gridx = 0; gbc.gridy = 2;
        formPanel.add(new JLabel("Medicine:"), gbc);
        gbc.gridx = 1;
        medicineField = new JTextField(20);
        formPanel.add(medicineField, gbc);

        // Notes
        gbc.gridx = 0; gbc.gridy = 3;
        formPanel.add(new JLabel("Notes:"), gbc);
        gbc.gridx = 1;
        notesArea = new JTextArea(3, 20);
        notesArea.setLineWrap(true);
        formPanel.add(new JScrollPane(notesArea), gbc);

        // Preview area
        gbc.gridx = 0; gbc.gridy = 4;
        gbc.gridwidth = 2;
        previewArea = new JTextArea(5, 40);
        previewArea.setEditable(false);
        previewArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        previewArea.setBackground(new Color(245, 245, 245));
        formPanel.add(new JScrollPane(previewArea), gbc);

        add(formPanel, BorderLayout.CENTER);

        // Buttons
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        btnPanel.setBackground(UITheme.BACKGROUND);

        JButton exportBtn = UITheme.createButton("💾 Export .txt", UITheme.SUCCESS);
        JButton closeBtn  = UITheme.createButton("Close", UITheme.DANGER);

        btnPanel.add(exportBtn);
        btnPanel.add(closeBtn);
        add(btnPanel, BorderLayout.SOUTH);

        // Export action
        exportBtn.addActionListener(e -> {
            String medicine = medicineField.getText().trim();
            String notes    = notesArea.getText().trim();

            if (medicine.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter medicine!");
                return;
            }

            boolean success = PrescriptionExporter.exportPrescription(
                SessionManager.getUserName(),
                doctorName, date, medicine, notes
            );

            if (success) {
                String fileName = "Prescription_" +
                    SessionManager.getUserName().replace(" ", "_")
                    + "_" + date + ".txt";

                // Show preview using FileInputStream ✅
                previewArea.setText(
                    PrescriptionExporter.readPrescription(fileName));

                JOptionPane.showMessageDialog(this,
                    "✅ Prescription saved as:\n" + fileName,
                    "Exported!", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this,
                    "❌ Export failed!", "Error",
                    JOptionPane.ERROR_MESSAGE);
            }
        });

        closeBtn.addActionListener(e -> dispose());
    }
}