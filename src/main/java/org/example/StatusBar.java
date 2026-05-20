package org.example;
import javax.swing.*;
import java.awt.*;

/**
 * Status bar panel displayed at the bottom of the main window.
 * Shows the selected file name and application status messages.
 * Observes FILE_SELECTED and STATUS_CHANGED events only;
 * does not react to REPO_LOADED events.
 *
 * @author babaldeep and yaneli
 * @version 3.0
 *
 */

public class StatusBar extends JPanel implements AppObserver {

    private final JLabel statusLabel;

    public StatusBar() {
        setLayout(new BorderLayout());
        setBackground(new Color(173, 189, 227));
        setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(120, 140, 200), 1),
                BorderFactory.createEmptyBorder(8, 12, 8, 12)
        ));

        statusLabel = new JLabel("Ready.");
        statusLabel.setHorizontalAlignment(SwingConstants.CENTER);
        statusLabel.setFont(statusLabel.getFont().deriveFont(Font.PLAIN, 13f));

        add(statusLabel, BorderLayout.CENTER);

        Blackboard.getInstance().addObserver(this);
    }

    @Override
    public void onEvent(AppEvent event) {
        if (event == AppEvent.FILE_SELECTED) {
            String name = Blackboard.getInstance().getSelectedFileName();
            if (name != null && !name.isBlank()) {
                statusLabel.setText("Selected: " + name);
            }
        } else if (event == AppEvent.STATUS_CHANGED) {
            statusLabel.setText(Blackboard.getInstance().getStatusMessage());
        }
    }
}
