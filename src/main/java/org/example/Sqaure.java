package org.example;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

public class Square extends JButton {
    private final SourceFileInfo fileInfo;

    public Square(SourceFileInfo fileInfo, double average) {
        this.fileInfo = fileInfo;

        setPreferredSize(new Dimension(100, 100));
        setFocusPainted(false);
        setOpaque(true);
        setBorder(new LineBorder(Color.BLACK, 1));
        setText("<html><center>" + fileInfo.getFileName() + "</center></html>");

        setBackground(getColorForFile(fileInfo.getLineCount(), average));

        addActionListener(e -> {
            Blackboard.getInstance().setSelectedFileName(fileInfo.getFileName());
            setBorder(new LineBorder(Color.BLUE, 3));
        });
    }

    private Color getColorForFile(int lineCount, double average) {
        double lowerBound = average * 0.8;
        double upperBound = average * 1.2;

        if (lineCount < lowerBound) {
            return Color.GREEN;
        } else if (lineCount <= upperBound) {
            return Color.YELLOW;
        } else {
            return Color.RED;
        }
    }
}