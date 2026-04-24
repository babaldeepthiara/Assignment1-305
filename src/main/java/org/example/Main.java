package org.example;

import javax.swing.SwingUtilities;

/**
 * Entry point that delegates all setup to MainFrame on the Event Dispatch Thread.
 *
 * @author babaldeep and yaneli
 */

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainFrame frame = new MainFrame();
            frame.setVisible(true);
        });
    }
}
