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
    private String expectedRole;

    public LoginScreen(String role) {
        this.expectedRole = role;

        // Role specific title & color
        String title = "";
        Color headerColor = UITheme.HEADER_BG;

        switch (role) {
        case "patient": title = "Patient Login"; break;
        case "doctor":  title = "Doctor Login";  break;
        case "admin":   title = "Admin Login";   break;
        }

        setTitle("Doctor Suggester - " + role.toUpperCase() + " Login");
        setSize(450, 380);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        getContentPane().setBackground(UITheme.BACKGROUND);

        // Header with role color
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(headerColor);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));
        JLabel headerLabel = new JLabel(title, SwingConstants.CENTER);
        headerLabel.setFont(UITheme.TITLE_FONT);
        headerLabel.setForeground(Color.WHITE);
        headerPanel.add(headerLabel, BorderLayout.CENTER);
        add(headerPanel, BorderLayout.NORTH);

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
        JButton loginBtn = UITheme.createButton("Login", headerColor);
        formPanel.add(loginBtn, gbc);

        add(formPanel, BorderLayout.CENTER);

        // Bottom panel
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        bottomPanel.setBackground(UITheme.BACKGROUND);

        // Show register only for patients
        if (role.equals("patient")) {
            JButton registerBtn = new JButton("New Patient? Register Here");
            registerBtn.setBorderPainted(false);
            registerBtn.setContentAreaFilled(false);
            registerBtn.setForeground(UITheme.PRIMARY);
            registerBtn.setFont(UITheme.NORMAL_FONT);
            registerBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
            registerBtn.addActionListener(e -> {
                new RegisterScreen().setVisible(true);
                dispose();
            });
            bottomPanel.add(registerBtn);
        }

        // Back button
        JButton backBtn = new JButton("← Back to Home");
        backBtn.setBorderPainted(false);
        backBtn.setContentAreaFilled(false);
        backBtn.setForeground(UITheme.DANGER);
        backBtn.setFont(UITheme.NORMAL_FONT);
        backBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        backBtn.addActionListener(e -> {
            new WelcomeScreen().setVisible(true);
            dispose();
        });
        bottomPanel.add(backBtn);
        add(bottomPanel, BorderLayout.SOUTH);

        // Login action
        loginBtn.addActionListener(e -> handleLogin());
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
            // Check if role matches
            if (!result[2].equals(expectedRole)) {
                JOptionPane.showMessageDialog(this,
                    "❌ Invalid credentials for " + expectedRole + " login!",
                    "Wrong Role", JOptionPane.ERROR_MESSAGE);
                return;
            }
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
}