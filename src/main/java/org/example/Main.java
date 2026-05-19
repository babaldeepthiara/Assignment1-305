package org.example;
import javax.swing.SwingUtilities;

/**
 * Entry point for Assignment 02.
 * Delegates all window construction to {@link MainFrame} on the Event Dispatch Thread.
 *
 * @author babaldeep and yaneli
 * @version 2.0
 * 
 */

public class Main {

    /**
     * Application entry point.
     *
     * @param args command-line arguments (not used)
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainFrame frame = new MainFrame();
            frame.setVisible(true);
        });
    }
}
