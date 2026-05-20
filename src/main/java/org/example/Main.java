package org.example;
import javax.swing.SwingUtilities;

/**
 * Entry point for Assignment 02.
 * Delegates all window construction to {@link MainFrame} on the Event Dispatch Thread.
 *
 * @author babaldeep and yaneli
 * @version 3.0
 *
 */

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainFrame frame = new MainFrame();
            frame.setVisible(true);
        });
    }
}
