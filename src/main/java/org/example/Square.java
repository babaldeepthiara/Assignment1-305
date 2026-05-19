package org.example;
import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

/**
 * Represents a single .java file as a colored, semi-transparent, clickable square.
 *
 *
 * @author babaldeep and yaneli
 * @version 2.0
 *
 */

public class Square extends JButton {

    private static final Color BASE_GREEN  = new Color(144, 238, 144);
    private static final Color BASE_YELLOW = new Color(255, 220, 80);
    private static final Color BASE_RED    = new Color(255, 99, 99);

    private static final LineBorder DEFAULT_BORDER  = new LineBorder(Color.DARK_GRAY, 1);
    private static final LineBorder SELECTED_BORDER = new LineBorder(Color.BLUE, 3);

    private static Square currentlySelected = null;
    private final SourceFileInfo fileInfo;

    public Square(SourceFileInfo fileInfo, int maxLoc) {
        this.fileInfo = fileInfo;
        initAppearance(maxLoc);
        initTooltip();
        initListener();
    }

    public static void clearSelection() {
        currentlySelected = null;
    }

    private void initAppearance(int maxLoc) {
        setPreferredSize(new Dimension(100, 100));
        setFocusPainted(false);
        setOpaque(false);
        setContentAreaFilled(false);
        setBorder(DEFAULT_BORDER);
    }

    private void initTooltip() {
        String tip = String.format(
                "<html><b>%s</b><br>LOC: %d<br>CC: %d<br>D: %.2f<br>I: %.2f<br>A: %.0f</html>",
                fileInfo.getFileName(),
                fileInfo.getLoc(),
                fileInfo.getCc(),
                fileInfo.getDistance(),
                fileInfo.getInstability(),
                fileInfo.getAbstractness()
        );

        setToolTipText(tip);
    }

    private void initListener() {
        addActionListener(e -> {
            if (currentlySelected != null) {
                currentlySelected.setBorder(DEFAULT_BORDER);
            }

            setBorder(SELECTED_BORDER);
            currentlySelected = this;
            Blackboard.getInstance().setSelectedFileName(fileInfo.getFileName());
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        int maxLoc = computeMaxLoc();
        int alpha = maxLoc == 0 ? 255
                : (int) Math.round(80 + 175.0 * fileInfo.getLoc() / maxLoc);
        alpha = Math.min(255, Math.max(0, alpha));

        Color base = baseColor();
        Color fill = new Color(base.getRed(), base.getGreen(), base.getBlue(), alpha);
        Graphics2D g2 = (Graphics2D) g.create();

        g2.setColor(fill);
        g2.fillRect(0, 0, getWidth(), getHeight());
        g2.dispose();

        super.paintComponent(g);
    }

    private Color baseColor() {
        int cc = fileInfo.getCc();
        if (cc >= 10) return BASE_RED;
        if (cc >= 5)  return BASE_YELLOW;
        return BASE_GREEN;
    }

    private int computeMaxLoc() {
        return Blackboard.getInstance().getFiles().stream()
                .mapToInt(SourceFileInfo::getLoc)
                .max()
                .orElse(1);
    }
}
