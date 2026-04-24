package org.example;

import javax.swing.*;
import java.awt.*;

/**
 * Assembles and displays the GUI components.
 * Uses private helper methods to keep the constructor clean.
 *
 * @author babaldeep and yaneli
 */

public class MainFrame extends JFrame implements RepoLoadedListener {

    private JLabel statusBar;

    public MainFrame() {
        super("Assignment 01");
        buildGUI();
        Blackboard.getInstance().addRepoLoadedListener(this);
    }

    private void buildGUI() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 650);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        setJMenuBar(buildMenuBar());
        add(buildGridArea(), BorderLayout.CENTER);
        add(buildStatusBar(), BorderLayout.SOUTH);
    }

    private JMenuBar buildMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        JMenuItem openItem = new JMenuItem("Open");
        openItem.addActionListener(e -> promptForRepoUrl());
        fileMenu.add(openItem);
        menuBar.add(fileMenu);
        return menuBar;
    }

    private JScrollPane buildGridArea() {
        GridPanel gridPanel = new GridPanel();
        Blackboard.getInstance().setGridPanel(gridPanel);
        return new JScrollPane(gridPanel);
    }

    private JPanel buildStatusBar() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(4, 8, 4, 8));
        JLabel label = new JLabel("Selected File Name: ");
        statusBar = new JLabel("");
        panel.add(label, BorderLayout.WEST);
        panel.add(statusBar, BorderLayout.CENTER);
        return panel;
    }

    private void promptForRepoUrl() {
        String url = JOptionPane.showInputDialog(
            this,
            "Enter GitHub Repository URL:",
            "Open Repository",
            JOptionPane.PLAIN_MESSAGE
        );
        if (url != null && !url.trim().isEmpty()) {
            Blackboard.getInstance().setRepoPath(url.trim());
            loadRepoInBackground(url.trim());
        }
    }

    private void loadRepoInBackground(String url) {
        SwingWorker<java.util.List<SourceFileInfo>, Void> worker = new SwingWorker<>() {
            @Override
            protected java.util.List<SourceFileInfo> doInBackground() throws Exception {
                GitHubLoader loader = new GitHubLoader();
                return loader.loadFiles(url);
            }

            @Override
            protected void done() {
                try {
                    Blackboard.getInstance().setFiles(get());
                    Blackboard.getInstance().notifyRepoLoaded();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(MainFrame.this,
                        "Failed to load repository:\n" + ex.getMessage(),
                        "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        };
        worker.execute();
    }

    @Override
    public void onRepoLoaded() {
        statusBar.setText(Blackboard.getInstance().getSelectedFileName());
    }
}
