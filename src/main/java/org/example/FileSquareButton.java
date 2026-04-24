package org.example;

import org.example.SourceFileInfo;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

public class FileSquareButton extends JButton {
    private final SourceFileInfo fileInfo;

    public FileSquareButton(SourceFileInfo fileInfo, FileColorCategory category) {
        this.fileInfo = fileInfo;

        setPreferredSize(new Dimension(100, 100));
        setFocusPainted(false);
        setOpaque(true);
        setBorder(new LineBorder(Color.BLACK, 1));
        setText(fileInfo.getFileName());

        switch (category) {
            case GREEN -> setBackground(Color.GREEN);
            case YELLOW -> setBackground(Color.YELLOW);
            case RED -> setBackground(Color.RED);
        }
    }

    public SourceFileInfo getFileInfo() {
        return fileInfo;
    }
}