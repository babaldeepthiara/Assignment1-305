package org.example;
import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * Displays a scrollable grid of square components, one per source file.
 * Implements app observer and reacts only to reloaded app events,
 * ensuring that file-selection events do not trigger an unnecessary grid rebuild.
 *
 * @author babaldeep and yaneli
 * @version 3.0
 *
 */

public class GridPanel extends JPanel implements AppObserver {

    private static final int COLUMNS = 5;

    public GridPanel() {
        setLayout(new GridLayout(0, COLUMNS, 10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        Blackboard.getInstance().addObserver(this);
    }

    @Override
    public void onEvent(AppEvent event) {
        if (event == AppEvent.REPO_LOADED) {
            refreshSquares();
        }
    }

    private void refreshSquares() {
        removeAll();
        Square.clearSelection();
        List<SourceFileInfo> files = Blackboard.getInstance().getFiles();
        int maxLoc = files.stream().mapToInt(SourceFileInfo::getLoc).max().orElse(1);

        for (SourceFileInfo file : files) {
            add(new Square(file, maxLoc));
        }

        revalidate();
        repaint();
    }
}
