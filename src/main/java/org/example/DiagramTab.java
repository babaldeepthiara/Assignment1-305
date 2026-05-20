package org.example;

import javax.swing.*;
import java.awt.*;

/**
 * Tab container for the UML Diagram view.
 * Responsible only for layout and observer registration.
 * Delegates all diagram generation and rendering to DiagramPanel.
 *
 * @author babaldeep and yaneli
 * @version 3.0
 */

public class DiagramTab extends JPanel implements AppObserver {

    private final DiagramPanel diagramPanel;

    public DiagramTab() {
        setLayout(new BorderLayout());
        diagramPanel = new DiagramPanel();
        add(new JScrollPane(diagramPanel), BorderLayout.CENTER);
        Blackboard.getInstance().addObserver(this);
    }

    @Override
    public void onEvent(AppEvent event) {
        if (event == AppEvent.REPO_LOADED) {
            diagramPanel.refresh();
        }
    }
}
