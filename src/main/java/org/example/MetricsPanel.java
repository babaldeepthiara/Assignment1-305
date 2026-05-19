package org.example;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * Displays an Abstractness vs Instability graph.
 * Each loaded Java file is plotted as a point.
 *
 * X-axis: Instability
 * Y-axis: Abstractness
 *
 * @author babaldeep and yaneli
 * @version 1.0
 */
public class MetricsPanel extends JPanel implements AppObserver {

    private static final int PADDING = 70;
    private static final int POINT_SIZE = 8;

    public MetricsPanel() {
        setBackground(Color.WHITE);
        Blackboard.getInstance().addObserver(this);
    }

    @Override
    public void onEvent(AppEvent event) {
        if (event == AppEvent.REPO_LOADED) {
            repaint();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        List<SourceFileInfo> files = Blackboard.getInstance().getFiles();

        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON
        );

        drawAxes(g2);
        drawMainSequence(g2);
        drawLabels(g2);
        drawPoints(g2, files);
    }

    private void drawAxes(Graphics2D g2) {
        int width = getWidth();
        int height = getHeight();

        int x0 = PADDING;
        int y0 = height - PADDING;
        int x1 = width - PADDING;
        int y1 = PADDING;

        g2.setColor(Color.BLACK);

        // x-axis
        g2.drawLine(x0, y0, x1, y0);

        // y-axis
        g2.drawLine(x0, y0, x0, y1);

        // tick labels
        g2.drawString("0.0", x0 - 10, y0 + 20);
        g2.drawString("1.0", x1 - 10, y0 + 20);
        g2.drawString("1.0", x0 - 40, y1 + 5);
    }

    private void drawMainSequence(Graphics2D g2) {
        int width = getWidth();
        int height = getHeight();

        int x0 = PADDING;
        int y0 = height - PADDING;
        int x1 = width - PADDING;
        int y1 = PADDING;

        g2.setColor(Color.GRAY);

        // Main sequence line: A + I = 1
        g2.drawLine(x0, y1, x1, y0);

        g2.drawString("Main Sequence", width / 2 - 40, height / 2);
    }

    private void drawLabels(Graphics2D g2) {
        int width = getWidth();
        int height = getHeight();

        g2.setColor(Color.BLACK);

        g2.drawString("Instability (I)", width / 2 - 40, height - 25);

        Graphics2D rotated = (Graphics2D) g2.create();
        rotated.rotate(-Math.PI / 2);
        rotated.drawString("Abstractness (A)", -height / 2 - 45, 25);
        rotated.dispose();

        g2.drawString("Abstractness vs Instability", width / 2 - 80, 30);

        g2.setColor(Color.DARK_GRAY);
        g2.drawString("Painful Zone", PADDING + 10, height - PADDING - 20);
        g2.drawString("Useless Zone", width - PADDING - 90, PADDING + 20);
    }

    private void drawPoints(Graphics2D g2, List<SourceFileInfo> files) {
        if (files == null || files.isEmpty()) {
            g2.setColor(Color.GRAY);
            g2.drawString("Load a repository to see metrics.", PADDING, PADDING);
            return;
        }

        int width = getWidth();
        int height = getHeight();

        for (SourceFileInfo file : files) {
            double instability = file.getInstability();
            double abstractness = file.getAbstractness();

            int x = PADDING + (int) (instability * (width - 2 * PADDING));
            int y = height - PADDING - (int) (abstractness * (height - 2 * PADDING));

            g2.setColor(Color.BLUE);
            g2.fillOval(
                    x - POINT_SIZE / 2,
                    y - POINT_SIZE / 2,
                    POINT_SIZE,
                    POINT_SIZE
            );

            g2.setColor(Color.BLACK);
            g2.drawString(file.getFileName(), x + 6, y - 6);
        }
    }
}