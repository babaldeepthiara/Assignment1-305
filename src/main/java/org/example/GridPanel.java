package org.example;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * @author babaldeep and yaneli
 * @version 2.0
 */
public class GridPanel extends JPanel implements RepoLoadedListener {

    public GridPanel() {
        setLayout(new GridLayout(0, 5, 10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        Blackboard.getInstance().addRepoLoadedListener(this);
    }

    @Override
    public void onRepoLoaded() {
        refreshSquares();
    }

    public void refreshSquares() {
        removeAll();

        List<SourceFileInfo> files = Blackboard.getInstance().getFiles();

        int maxLoc = getMaxLoc(files);

        for (SourceFileInfo file : files) {
            add(new Square(file, maxLoc));
        }

        revalidate();
        repaint();
    }

    private int getMaxLoc(List<SourceFileInfo> files) {
        int max = 0;
        for (SourceFileInfo file : files) {
            if (file.getLoc() > max) {
                max = file.getLoc();
            }
        }
        return max == 0 ? 1 : max; // avoid division by 0
    }
}