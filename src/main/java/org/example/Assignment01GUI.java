package org.example;

import javax.swing.*;
import java.awt.*;

public class Assignment01GUI extends JFrame {

    private JTextField repoField;
    private JButton okButton;
    private JPanel gridPanel;
    private JTextField selectedFileField;

    public Assignment01GUI() {
        super("Assignment 01");
        buildGUI();
    }

    private void buildGUI() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 650);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        JPanel topPanel = new JPanel(new BorderLayout(10, 10));
        repoField = new JTextField();
        okButton = new JButton("OK");

        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 10));
        topPanel.add(repoField, BorderLayout.CENTER);
        topPanel.add(okButton, BorderLayout.EAST);

        gridPanel = new JPanel();
        gridPanel.setLayout(new GridLayout(0, 5, 10, 10));
        gridPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JScrollPane scrollPane = new JScrollPane(gridPanel);

        JPanel bottomPanel = new JPanel(new BorderLayout(10, 10));
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));

        JLabel selectedLabel = new JLabel("Selected File Name");
        selectedFileField = new JTextField();
        selectedFileField.setEditable(false);

        bottomPanel.add(selectedLabel, BorderLayout.NORTH);
        bottomPanel.add(selectedFileField, BorderLayout.CENTER);

        add(topPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

        okButton.addActionListener(e -> loadRepository());
    }

    private void loadRepository() {
        String path = repoField.getText().trim();
        JOptionPane.showMessageDialog(this, "You entered: " + path);
    }
}