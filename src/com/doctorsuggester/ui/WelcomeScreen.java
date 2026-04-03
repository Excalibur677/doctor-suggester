package com.doctorsuggester.ui;

import com.doctorsuggester.util.UITheme;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class WelcomeScreen extends JFrame {

    public WelcomeScreen() {
        setTitle("Doctor Suggester - Welcome");
        setSize(520, 420);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        getContentPane().setBackground(UITheme.BACKGROUND);

        // Header Panel
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(26, 82, 122));
        headerPanel.setBorder(BorderFactory.createEmptyBorder(25, 20, 25, 20));

        JLabel titleLabel = new JLabel("Doctor Suggester", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);

        JLabel subtitleLabel = new JLabel(
            "Smart Healthcare Management System", SwingConstants.CENTER);
        subtitleLabel.setFont(new Font("Arial", Font.PLAIN, 13));
        subtitleLabel.setForeground(new Color(157, 200, 232));

        JPanel headerText = new JPanel(new GridLayout(2, 1, 0, 5));
        headerText.setBackground(new Color(26, 82, 122));
        headerText.add(titleLabel);
        headerText.add(subtitleLabel);
        headerPanel.add(headerText, BorderLayout.CENTER);
        add(headerPanel, BorderLayout.NORTH);

        // Subtitle
        JLabel selectLabel = new JLabel(
            "Please select your role to continue", SwingConstants.CENTER);
        selectLabel.setFont(UITheme.NORMAL_FONT);
        selectLabel.setForeground(new Color(100, 100, 100));
        selectLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 15, 0));
        add(selectLabel, BorderLayout.CENTER);

        // Role Cards Panel
        JPanel cardsPanel = new JPanel(new GridLayout(1, 3, 15, 0));
        cardsPanel.setBackground(UITheme.BACKGROUND);
        cardsPanel.setBorder(BorderFactory.createEmptyBorder(0, 30, 20, 30));

        cardsPanel.add(createRoleCard(
            "PATIENT",
            "Book appointments\n& view prescriptions",
            new Color(41, 128, 185), "patient"));

        cardsPanel.add(createRoleCard(
            "DOCTOR",
            "Manage appointments\n& write prescriptions",
            new Color(39, 174, 96), "doctor"));

        cardsPanel.add(createRoleCard(
            "ADMIN",
            "Manage doctors\n& view all records",
            new Color(142, 68, 173), "admin"));

        add(cardsPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    private JPanel createRoleCard(String role, String desc,
                                   Color color, String roleKey) {
        JPanel card = new JPanel(new BorderLayout(0, 8));
        card.setBackground(color);
        card.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));
        card.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Icon circle
        JPanel iconCircle = new JPanel() {
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                                    RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(255, 255, 255, 60));
                g2.fillOval(0, 0, 44, 44);

                // Draw + symbol as icon
                g2.setColor(Color.WHITE);
                g2.setStroke(new BasicStroke(2.5f));
                if (roleKey.equals("patient")) {
                    g2.drawOval(12, 8, 20, 20);
                    g2.drawLine(22, 30, 22, 38);
                    g2.drawLine(16, 38, 28, 38);
                } else if (roleKey.equals("doctor")) {
                    g2.drawLine(22, 10, 22, 34);
                    g2.drawLine(10, 22, 34, 22);
                } else {
                    g2.drawRect(10, 10, 9, 9);
                    g2.drawRect(25, 10, 9, 9);
                    g2.drawRect(10, 25, 9, 9);
                    g2.drawRect(25, 25, 9, 9);
                }
            }
        };
        iconCircle.setPreferredSize(new Dimension(44, 44));
        iconCircle.setOpaque(false);

        JPanel iconWrapper = new JPanel(new FlowLayout(FlowLayout.CENTER));
        iconWrapper.setOpaque(false);
        iconWrapper.add(iconCircle);

        // Role name
        JLabel roleLabel = new JLabel(role, SwingConstants.CENTER);
        roleLabel.setFont(new Font("Arial", Font.BOLD, 15));
        roleLabel.setForeground(Color.WHITE);

        // Description
        JLabel descLabel = new JLabel(
            "<html><center>" + desc.replace("\n", "<br>") + "</center></html>",
            SwingConstants.CENTER);
        descLabel.setFont(new Font("Arial", Font.PLAIN, 11));
        descLabel.setForeground(new Color(220, 220, 220));

        JPanel textPanel = new JPanel(new GridLayout(2, 1, 0, 4));
        textPanel.setOpaque(false);
        textPanel.add(roleLabel);
        textPanel.add(descLabel);

        card.add(iconWrapper, BorderLayout.NORTH);
        card.add(textPanel, BorderLayout.CENTER);

        // Hover effect
        card.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                card.setBackground(color.darker());
            }
            public void mouseExited(MouseEvent e) {
                card.setBackground(color);
            }
            public void mouseClicked(MouseEvent e) {
                new LoginScreen(roleKey).setVisible(true);
                dispose();
            }
        });

        // Make child components also trigger click
        for (Component c : card.getComponents()) {
            c.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                    new LoginScreen(roleKey).setVisible(true);
                    dispose();
                }
            });
        }

        return card;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new WelcomeScreen());
    }
}