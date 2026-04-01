package com.doctorsuggester.ui;

import com.doctorsuggester.dao.UserDAO;
import com.doctorsuggester.util.SessionManager;
import com.doctorsuggester.util.UITheme;

import javax.swing.*;
import java.awt.*;

public class LoginScreen extends JFrame {

    private JTextField emailField;
    private JPasswordField passwordField;
    private UserDAO userDAO = new UserDAO();

    public LoginScreen() {
        setTitle("Doctor Suggester - Login");
        setSize(450, 380);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        getContentPane().setBackground(UITheme.BACKGROUND);

        // Header
        add(UITheme.createHeaderPanel("🏥 Doctor Suggester"), BorderLayout.NORTH);

        // Form Panel
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(UITheme.BACKGROUND);
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 50, 10, 50));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Email
        gbc.gridx = 0; gbc.gridy = 0;
        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setFont(UITheme.NORMAL_FONT);
        formPanel.add(emailLabel, gbc);

        gbc.gridx = 1;
        emailField = new JTextField(20);
        emailField.setFont(UITheme.NORMAL_FONT);
        formPanel.add(emailField, gbc);

        // Password
        gbc.gridx = 0; gbc.gridy = 1;
        JLabel passLabel = new JLabel("Password:");
        passLabel.setFont(UITheme.NORMAL_FONT);
        formPanel.add(passLabel, gbc);

        gbc.gridx = 1;
        passwordField = new JPasswordField(20);
        passwordField.setFont(UITheme.NORMAL_FONT);
        formPanel.add(passwordField, gbc);

        // Login Button
        gbc.gridx = 0; gbc.gridy = 2;
        gbc.gridwidth = 2;
        JButton loginBtn = UITheme.createButton("Login", UITheme.PRIMARY);
        formPanel.add(loginBtn, gbc);

        add(formPanel, BorderLayout.CENTER);

        // Register link
        JPanel bottomPanel = new JPanel();
        bottomPanel.setBackground(UITheme.BACKGROUND);
        JButton registerBtn = new JButton("New Patient? Register Here");
        registerBtn.setBorderPainted(false);
        registerBtn.setContentAreaFilled(false);
        registerBtn.setForeground(UITheme.PRIMARY);
        registerBtn.setFont(UITheme.NORMAL_FONT);
        registerBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        bottomPanel.add(registerBtn);
        add(bottomPanel, BorderLayout.SOUTH);

        // Actions
        loginBtn.addActionListener(e -> handleLogin());
        registerBtn.addActionListener(e -> {
            new RegisterScreen().setVisible(true);
            dispose();
        });

        setVisible(true);
    }

    private void handleLogin() {
        String email = emailField.getText().trim();
        String password = new String(passwordField.getPassword()).trim();

        if (email.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                "Please fill all fields!", "Error",
                JOptionPane.ERROR_MESSAGE);
            return;
        }

        String[] result = userDAO.login(email, password);
        if (result != null) {
            SessionManager.setSession(
                Integer.parseInt(result[0]), result[1], result[2]);
            openDashboard(result[2]);
            dispose();
        } else {
            JOptionPane.showMessageDialog(this,
                "Invalid email or password!", "Login Failed",
                JOptionPane.ERROR_MESSAGE);
        }
    }

    private void openDashboard(String role) {
        switch (role) {
            case "patient": new PatientDashboard().setVisible(true); break;
            case "doctor":  new DoctorDashboard().setVisible(true);  break;
            case "admin":   new AdminDashboard().setVisible(true);   break;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LoginScreen());
    }
}