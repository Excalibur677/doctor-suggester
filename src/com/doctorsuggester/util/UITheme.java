package com.doctorsuggester.util;

import javax.swing.*;
import javax.swing.BorderFactory;
import java.awt.*;
public class UITheme {

    // Colors
    public static final Color PRIMARY     = new Color(41, 128, 185);   // Blue
    public static final Color SUCCESS     = new Color(39, 174, 96);    // Green
    public static final Color DANGER      = new Color(192, 57, 43);    // Red
    public static final Color WARNING     = new Color(243, 156, 18);   // Orange
    public static final Color BACKGROUND  = new Color(236, 240, 241);  // Light Grey
    public static final Color WHITE       = Color.WHITE;
    public static final Color TEXT_DARK   = new Color(44, 62, 80);     // Dark Blue-Grey
    public static final Color HEADER_BG   = new Color(26, 82, 118);    // Dark Blue

    // Fonts
    public static final Font TITLE_FONT   = new Font("Arial", Font.BOLD, 22);
    public static final Font HEADER_FONT  = new Font("Arial", Font.BOLD, 16);
    public static final Font NORMAL_FONT  = new Font("Arial", Font.PLAIN, 14);
    public static final Font SMALL_FONT   = new Font("Arial", Font.PLAIN, 12);

    // Styled button
    public static JButton createButton(String text, Color bg) {
        JButton btn = new JButton(text);
        btn.setBackground(bg);
        btn.setForeground(Color.WHITE);
        btn.setFont(HEADER_FONT);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setPreferredSize(new Dimension(180, 40));
        return btn;
    }

    // Styled label
    public static JLabel createTitleLabel(String text) {
        JLabel label = new JLabel(text, SwingConstants.CENTER);
        label.setFont(TITLE_FONT);
        label.setForeground(WHITE);
        return label;
    }

    // Header panel
    public static JPanel createHeaderPanel(String text) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(HEADER_BG);
        panel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));
        JLabel label = createTitleLabel(text);
        panel.add(label, BorderLayout.CENTER);
        return panel;
    }
}