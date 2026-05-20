package org.example;

import javax.swing.*;
import java.awt.*;

/**
 * Tab container for the Metrics view.
 * Responsible only for layout and observer registration.
 * Delegates all painting to MetricsPanel.
 *
 * @author babaldeep and yaneli
 * @version 3.0
 */

public class MetricsTab extends JPanel implements AppObserver {

    private final MetricsPanel metricsPanel;

    public MetricsTab() {
        setLayout(new BorderLayout());
        metricsPanel = new MetricsPanel();
        add(metricsPanel, BorderLayout.CENTER);
        Blackboard.getInstance().addObserver(this);
    }

    @Override
    public void onEvent(AppEvent event) {
        if (event == AppEvent.REPO_LOADED) {
            metricsPanel.repaint();
        }
    }
}
