package org.example;
import javax.swing.*;
import java.awt.*;

/**
 * Status bar panel displayed at the bottom of the main window.
 * Shows the selected file name and application status messages.
 * Observes file selected app even and app event status change only;
 * does not react to repository loaded events.
 *
 * @author babaldeep and yaneli
 * @version 2.0
 *
 */

public class StatusBar extends JPanel implements AppObserver {

    private final JLabel selectedFileLabel;
    private final JLabel statusLabel;

    public StatusBar() {
        setLayout(new BorderLayout(10, 0));
        setBorder(BorderFactory.createEmptyBorder(4, 8, 4, 8));

        JLabel prefix = new JLabel("Selected File Name: ");
        selectedFileLabel = new JLabel("");
        statusLabel = new JLabel(" ");
        statusLabel.setHorizontalAlignment(SwingConstants.RIGHT);

        add(prefix, BorderLayout.WEST);
        add(selectedFileLabel, BorderLayout.CENTER);
        add(statusLabel, BorderLayout.EAST);

        Blackboard.getInstance().addObserver(this);
    }

    @Override
    public void onEvent(AppEvent event) {
        if (event == AppEvent.FILE_SELECTED) {
            selectedFileLabel.setText(Blackboard.getInstance().getSelectedFileName());
        }

        else if (event == AppEvent.STATUS_CHANGED) {
            statusLabel.setText(Blackboard.getInstance().getStatusMessage());
        }
    }
}
