package org.example;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * Displays a grid of Squares, one per source file.
 * Implements RepoLoadedListener to refresh automatically when new files are loaded.
 *
 * @author babaldeep and yaneli
 * @version 1.0
 */


// TO SEE THE NAMES OF THE FILES AFTER UPLOADING THE GITHUB LINK, CLICK ON A SQUARE AND LOOK AT THE BOTTOM OF THE WINDOW.
// OR YOU CAN HOVER YOUR MOUSE OVER THE SQUARES TO SEE THE FILE NAMES AND # OF LINES IN THE FILE.
// I used the following github repository to test the application: https://github.com/javiergs/TULIP

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
        double average = calculateAverage(files);
        for (SourceFileInfo file : files) {
            add(new Square(file, average));
        }
        revalidate();
        repaint();
    }

    private double calculateAverage(List<SourceFileInfo> files) {
        if (files.isEmpty()) return 0;
        int total = 0;
        for (SourceFileInfo file : files) {
            total += file.getLineCount();
        }
        return (double) total / files.size();
    }
}
