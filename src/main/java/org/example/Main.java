package org.example;

import org.example.Assignment01GUI;

import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Assignment01GUI gui = new Assignment01GUI();
            gui.setVisible(true);
        });
    }
}