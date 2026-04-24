package org.example;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class GridPanel extends JPanel {

    public GridPanel() {
        setLayout(new GridLayout(0, 5, 10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    }

    public void refreshSquares() {
        removeAll();

        List<SourceFileInfo> files = Blackboard.getInstance().getFiles();
        double average = calculateAverage(files);

        for (SourceFileInfo file : files) {
            Square square = new Square(file, average);
            add(square);
        }

        revalidate();
        repaint();
    }

    private double calculateAverage(List<SourceFileInfo> files) {
        if (files.isEmpty()) {
            return 0;
        }

        int total = 0;
        for (SourceFileInfo file : files) {
            total += file.getLineCount();
        }

        return (double) total / files.size();
    }
}