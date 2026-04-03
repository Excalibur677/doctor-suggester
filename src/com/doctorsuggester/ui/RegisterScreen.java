package com.doctorsuggester.ui;

import com.doctorsuggester.dao.UserDAO;

import javax.swing.*;
import java.awt.*;

public class RegisterScreen extends JFrame {

    private JTextField nameField, emailField, phoneField;
    private JPasswordField passwordField;
    private UserDAO userDAO = new UserDAO();

    public RegisterScreen() {
        setTitle("Register - New Patient");
        setSize(400, 350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Title
        JLabel title = new JLabel("Patient Registration", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 18));
        title.setBorder(BorderFactory.createEmptyBorder(15, 0, 10, 0));
        add(title, BorderLayout.NORTH);

        // Form
        JPanel form = new JPanel(new GridLayout(5, 2, 10, 10));
        form.setBorder(BorderFactory.createEmptyBorder(10, 40, 10, 40));

        form.add(new JLabel("Full Name:"));
        nameField = new JTextField();
        form.add(nameField);

        form.add(new JLabel("Email:"));
        emailField = new JTextField();
        form.add(emailField);

        form.add(new JLabel("Password:"));
        passwordField = new JPasswordField();
        form.add(passwordField);

        form.add(new JLabel("Phone:"));
        phoneField = new JTextField();
        form.add(phoneField);

        form.add(new JLabel(""));
        JButton registerBtn = new JButton("Register");
        form.add(registerBtn);

        add(form, BorderLayout.CENTER);

        // Back to login
        JButton backBtn = new JButton("Back to Login");
        backBtn.setBorderPainted(false);
        backBtn.setContentAreaFilled(false);
        backBtn.setForeground(Color.BLUE);
        add(backBtn, BorderLayout.SOUTH);

        // Register action
        registerBtn.addActionListener(e -> {
            String name = nameField.getText().trim();
            String email = emailField.getText().trim();
            String password = new String(passwordField.getPassword()).trim();
            String phone = phoneField.getText().trim();

            if (name.isEmpty() || email.isEmpty() || password.isEmpty() || phone.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill all fields!");
                return;
            }

            boolean success = userDAO.registerPatient(name, email, password, phone);
            if (success) {
                JOptionPane.showMessageDialog(this, "Registration successful! Please login.");
                new LoginScreen("patient").setVisible(true);
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Registration failed! Email may already exist.");
            }
        });

        backBtn.addActionListener(e -> {
        	new LoginScreen("patient").setVisible(true);
            dispose();
        });
    }
}