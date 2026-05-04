package org.example;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

/**
 * @author babaldeep and yaneli
 * @version 2.0
 */
public class Square extends JButton {

    private final SourceFileInfo fileInfo;
    private final int maxLoc;

    public Square(SourceFileInfo fileInfo, int maxLoc) {
        this.fileInfo = fileInfo;
        this.maxLoc = maxLoc;
        initAppearance();
        initListener();
    }

    private void initAppearance() {
        setPreferredSize(new Dimension(100, 100));
        setFocusPainted(false);
        setOpaque(false);
        setContentAreaFilled(false);
        setBorder(new LineBorder(Color.DARK_GRAY, 1));

        setToolTipText(
                "<html>"
                        + "Filename: " + fileInfo.getFileName() + "<br>"
                        + "LOC: " + fileInfo.getLoc() + "<br>"
                        + "CC: " + fileInfo.getCc() + "<br>"
                        + "D: " + fileInfo.getDependencies() + "<br>"
                        + "I: " + String.format("%.2f", fileInfo.getInstability()) + "<br>"
                        + "A: " + String.format("%.2f", fileInfo.getAbstractness())
                        + "</html>"
        );
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();

        // 🎨 Color based on CC
        Color baseColor = getColorForCC(fileInfo.getCc());

        // 🌫 Transparency based on LOC
        float alpha = (float) fileInfo.getLoc() / maxLoc;
        alpha = Math.max(0.1f, Math.min(1f, alpha)); // clamp

        Color finalColor = new Color(
                baseColor.getRed(),
                baseColor.getGreen(),
                baseColor.getBlue(),
                (int) (alpha * 255)
        );

        g2.setColor(finalColor);
        g2.fillRect(0, 0, getWidth(), getHeight());

        g2.dispose();
        super.paintComponent(g);
    }

    private void initListener() {
        addActionListener(e -> {
            Blackboard.getInstance().setSelectedFileName(fileInfo.getFileName());
            Blackboard.getInstance().notifyRepoLoaded();
            setBorder(new LineBorder(Color.BLUE, 3));
        });
    }

    private Color getColorForCC(int cc) {
        if (cc >= 10) return Color.RED;
        if (cc >= 5) return Color.YELLOW;
        return Color.GREEN;
    }
}