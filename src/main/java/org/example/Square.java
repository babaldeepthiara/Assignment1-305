package org.example;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

/**
 * Represents a single file as a colored, clickable square in the grid.
 * Color is determined by the file's line count relative to the average.
 * On click, updates the selected file name on the Blackboard.
 *
 * @author babaldeep and yaneli
 * @version 1.0
 */


// TO SEE THE NAMES OF THE FILES AFTER UPLOADING THE GITHUB LINK, CLICK ON A SQUARE AND LOOK AT THE BOTTOM OF THE WINDOW.
// OR YOU CAN HOVER YOUR MOUSE OVER THE SQUARES TO SEE THE FILE NAMES AND # OF LINES IN THE FILE.
// I used the following github repository to test the application: https://github.com/javiergs/TULIP

public class Square extends JButton {

    private static final Color COLOR_BELOW  = new Color(144, 238, 144);
    private static final Color COLOR_AVERAGE = new Color(255, 220, 80);
    private static final Color COLOR_ABOVE  = new Color(255, 99, 99);

    private final SourceFileInfo fileInfo;

    public Square(SourceFileInfo fileInfo, double average) {
        this.fileInfo = fileInfo;
        initAppearance(average);
        initListener();
    }

    private void initAppearance(double average) {
        setPreferredSize(new Dimension(100, 100));
        setFocusPainted(false);
        setOpaque(true);
        setBorder(new LineBorder(Color.DARK_GRAY, 1));
        setBackground(getColorForFile(fileInfo.getLineCount(), average));
        setToolTipText(fileInfo.getFileName() + " (" + fileInfo.getLineCount() + " lines)");
    }

    private void initListener() {
        addActionListener(e -> {
            Blackboard.getInstance().setSelectedFileName(fileInfo.getFileName());
            Blackboard.getInstance().notifyRepoLoaded();
            setBorder(new LineBorder(Color.BLUE, 3));
        });
    }

    private Color getColorForFile(int lineCount, double average) {
        double lower = average * 0.8;
        double upper = average * 1.2;
        if (lineCount < lower) return COLOR_BELOW;
        else if (lineCount <= upper) return COLOR_AVERAGE;
        else return COLOR_ABOVE;
    }
}
