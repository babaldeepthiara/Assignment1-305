package org.example;

import javax.swing.SwingUtilities;

/**
 * Entry point that delegates all setup to MainFrame on the Event Dispatch Thread.
 *
 * @author babaldeep and yaneli
 * @version 1.0
 */


// TO SEE THE NAMES OF THE FILES AFTER UPLOADING THE GITHUB LINK, CLICK ON A SQUARE AND LOOK AT THE BOTTOM OF THE WINDOW.
// OR YOU CAN HOVER YOUR MOUSE OVER THE SQUARES TO SEE THE FILE NAMES AND # OF LINES IN THE FILE.
// I used the following github repository to test the application: https://github.com/javiergs/TULIP

// It takes a second to load...

// I have a tulip.properties file I didn't commit/aren't attaching with my personal token,
// so if you want to test the application, create a tulip.properties file in the src/main/resources directory
// with your own token in the following format: GITHUB_TOKEN=ghp_yourtokenhere

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainFrame frame = new MainFrame();
            frame.setVisible(true);
        });
    }
}
