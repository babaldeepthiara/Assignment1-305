package org.example;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * Renders the Abstractness vs Instability scatter plot.
 * Pure painting component — no observer registration, no Blackboard interaction
 * beyond reading files at paint time. All lifecycle concerns are handled by MetricsTab.
 *
 * @author babaldeep and yaneli
 * @version 3.0
 */

public class MetricsPanel extends JPanel {

    private static final int   PAD        = 70;
    private static final int   POINT_R    = 7;
    private static final Color GREEN_ZONE = new Color(144, 180, 160, 80);
    private static final Color ZONE_LABEL = new Color(60, 60, 60);

    private static final Color BASE_GREEN  = new Color(144, 238, 144);
    private static final Color BASE_YELLOW = new Color(255, 220, 80);
    private static final Color BASE_RED    = new Color(255, 99,  99);

    public MetricsPanel() {
        setBackground(Color.WHITE);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        int w  = getWidth();
        int h  = getHeight();
        int x0 = PAD;
        int x1 = w - PAD;
        int y0 = h - PAD;
        int y1 = PAD;
        int pw = x1 - x0;
        int ph = y0 - y1;

        drawGreenZone(g2, x0, y0, x1, y1, pw, ph);
        drawAxes(g2, x0, y0, x1, y1, w, h);
        drawMainSequence(g2, x0, y0, x1, y1);
        drawZoneLabels(g2, x0, y0, x1, y1);
        drawAxisLabels(g2, x0, y0, x1, y1, w, h);
        drawPoints(g2, x0, y0, pw, ph);
    }

    private void drawGreenZone(Graphics2D g2, int x0, int y0, int x1, int y1, int pw, int ph) {
        java.awt.geom.Area area = new java.awt.geom.Area(new Rectangle(x0, y1, pw, ph));
        int r = (int) (Math.min(pw, ph) * 0.38);
        
        area.subtract(new java.awt.geom.Area(new java.awt.geom.Ellipse2D.Double(x0 - r, y0 - r, r * 2, r * 2)));
        area.subtract(new java.awt.geom.Area(new java.awt.geom.Ellipse2D.Double(x1 - r, y1 - r, r * 2, r * 2)));

        g2.setColor(GREEN_ZONE);
        g2.fill(area);
    }

    private void drawAxes(Graphics2D g2, int x0, int y0, int x1, int y1, int w, int h) {
        g2.setColor(Color.DARK_GRAY);
        g2.setStroke(new BasicStroke(2f));
        g2.drawLine(x0, y0, x1 + 10, y0);
        g2.drawLine(x0, y0, x0, y1 - 10);

        g2.fillPolygon(new int[]{x1+10, x1+4, x1+4}, new int[]{y0, y0-5, y0+5}, 3);
        g2.fillPolygon(new int[]{x0, x0-5, x0+5}, new int[]{y1-10, y1-4, y1-4}, 3);

        g2.setStroke(new BasicStroke(1f));
        g2.setFont(new Font("SansSerif", Font.PLAIN, 11));
        g2.drawString("0", x0 - 8,  y0 + 16);
        g2.drawLine(x1, y0 - 4, x1, y0 + 4);
        g2.drawString("1", x1 - 4,  y0 + 16);
        g2.drawLine(x0 - 4, y1, x0 + 4, y1);
        g2.drawString("1", x0 - 16, y1 + 4);
    }

    private void drawMainSequence(Graphics2D g2, int x0, int y0, int x1, int y1) {
        g2.setColor(new Color(180, 180, 180));
        g2.setStroke(new BasicStroke(1.5f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10, new float[]{6, 4}, 0));
        g2.drawLine(x0, y1, x1, y0);

        Graphics2D rot = (Graphics2D) g2.create();
        rot.setColor(new Color(140, 140, 140));
        rot.setFont(new Font("SansSerif", Font.ITALIC, 11));
        rot.translate((x0 + x1) / 2 - 20, (y0 + y1) / 2 - 10);
        rot.rotate(-Math.atan2(y0 - y1, x1 - x0));
        rot.drawString("the main sequence", 0, 0);
        rot.dispose();

        g2.setStroke(new BasicStroke(1f));
    }

    private void drawZoneLabels(Graphics2D g2, int x0, int y0, int x1, int y1) {
        g2.setFont(new Font("SansSerif", Font.BOLD, 12));
        g2.setColor(ZONE_LABEL);
        g2.drawString("Painful", x0 + 10, y0 - 15);
        g2.drawString("Useless", x1 - 70, y1 + 20);
    }

    private void drawAxisLabels(Graphics2D g2, int x0, int y0, int x1, int y1, int w, int h) {
        g2.setFont(new Font("SansSerif", Font.BOLD, 12));
        g2.setColor(new Color(200, 130, 0));
        g2.drawString("Instability", (x0 + x1) / 2 - 30, h - 12);

        g2.setFont(new Font("SansSerif", Font.PLAIN, 10));
        g2.setColor(Color.GRAY);
        g2.drawString("Stable",   x0 - 18, y0 + 28);
        g2.drawString("Unstable", x1 - 28, y0 + 28);

        Graphics2D rot = (Graphics2D) g2.create();
        rot.setFont(new Font("SansSerif", Font.BOLD, 12));
        rot.setColor(new Color(200, 130, 0));
        rot.rotate(-Math.PI / 2);
        rot.drawString("Abstractness", -(y0 + y1) / 2 - 40, 18);
        rot.dispose();

        g2.setFont(new Font("SansSerif", Font.PLAIN, 10));
        g2.setColor(Color.GRAY);
        g2.drawString("Abstract", 4, y1 + 4);
        g2.drawString("Concrete", 4, y0);
    }

    private void drawPoints(Graphics2D g2, int x0, int y0, int pw, int ph) {
        List<SourceFileInfo> files = Blackboard.getInstance().getFiles();

        if (files == null || files.isEmpty()) {
            g2.setColor(Color.GRAY);
            g2.setFont(new Font("SansSerif", Font.ITALIC, 13));
            g2.drawString("Load a repository to see metrics.", x0 + 20, y0 - 20);
            return;
        }

        int maxLoc = files.stream().mapToInt(SourceFileInfo::getLoc).max().orElse(1);

        for (SourceFileInfo file : files) {
            int px = x0 + (int) (file.getInstability()  * pw);
            int py = y0 - (int) (file.getAbstractness() * ph);

            int alpha = maxLoc == 0 ? 255 : (int) Math.round(80 + 175.0 * file.getLoc() / maxLoc);
            alpha = Math.min(255, Math.max(0, alpha));

            Color base = squareColor(file.getCc());
            Color fill = new Color(base.getRed(), base.getGreen(), base.getBlue(), alpha);

            g2.setColor(fill);
            g2.fillOval(px - POINT_R, py - POINT_R, POINT_R * 2, POINT_R * 2);

            g2.setColor(new Color(60, 60, 60, 180));
            g2.setStroke(new BasicStroke(1f));
            g2.drawOval(px - POINT_R, py - POINT_R, POINT_R * 2, POINT_R * 2);

            g2.setColor(Color.DARK_GRAY);
            g2.setFont(new Font("SansSerif", Font.PLAIN, 10));
            g2.drawString(file.getFileName().replace(".java", ""), px + POINT_R + 2, py + 4);
        }
    }

    private Color squareColor(int cc) {
        if (cc >= 10) return BASE_RED;
        if (cc >= 5)  return BASE_YELLOW;
        return BASE_GREEN;
    }
}
