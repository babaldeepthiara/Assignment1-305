package org.example;
import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * Main application window.
 *
 * @author babaldeep and yaneli
 * @version 3.0
 */

public class MainFrame extends JFrame {

    private GridPanel gridPanel;

    public MainFrame() {
        super("Assignment 03");
        buildGUI();
    }

    private void buildGUI() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1100, 700);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        setJMenuBar(buildMenuBar());

        TreePanel treePanel = new TreePanel();
        JTabbedPane tabs = new JTabbedPane();
        gridPanel = new GridPanel();

        tabs.addTab("Grid", new JScrollPane(gridPanel));
        tabs.addTab("Metrics", new MetricsTab());
        tabs.addTab("Diagram", new DiagramTab());

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, treePanel, tabs);
        splitPane.setDividerLocation(250);

        add(splitPane, BorderLayout.CENTER);
        add(new StatusBar(), BorderLayout.SOUTH);
    }

    private JMenuBar buildMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        menuBar.add(buildFileMenu());
        menuBar.add(buildActionMenu());
        menuBar.add(buildHelpMenu());
        return menuBar;
    }

    private JMenu buildFileMenu() {
        JMenu menu = new JMenu("File");

        JMenuItem openItem = new JMenuItem("Open from URL");
        openItem.addActionListener(e -> promptForRepoUrl());

        JMenuItem exitItem = new JMenuItem("Exit");
        exitItem.addActionListener(e -> System.exit(0));

        menu.add(openItem);
        menu.addSeparator();
        menu.add(exitItem);
        return menu;
    }

    private JMenu buildActionMenu() {
        JMenu menu = new JMenu("Action");

        JMenuItem reloadItem = new JMenuItem("Reload");
        reloadItem.addActionListener(e -> {
            String url = Blackboard.getInstance().getRepoUrl();
            if (url != null && !url.isBlank()) {
                loadRepoInBackground(url);
            }
        });

        JMenuItem clearItem = new JMenuItem("Clear");
        clearItem.addActionListener(e -> {
            Blackboard.getInstance().setFiles(new java.util.ArrayList<>());
            Blackboard.getInstance().setSelectedFileName("");
            Blackboard.getInstance().setStatusMessage("Cleared.");
        });

        menu.add(reloadItem);
        menu.add(clearItem);
        return menu;
    }

    private JMenu buildHelpMenu() {
        JMenu menu = new JMenu("Help");

        JMenuItem aboutItem = new JMenuItem("About");
        aboutItem.addActionListener(e ->
                JOptionPane.showMessageDialog(
                        this,
                        "<html><b>GitHub Repository Analyzer</b><br>" +
                        "Version 3.0<br><br>" +
                        "Authors: babaldeep and yaneli<br><br>" +
                        "Fetches Java source files from a public GitHub<br>" +
                        "repository and visualizes software design metrics.</html>",
                        "About",
                        JOptionPane.INFORMATION_MESSAGE
                )
        );

        menu.add(aboutItem);
        return menu;
    }

    private void promptForRepoUrl() {
        String url = JOptionPane.showInputDialog(this, "Enter GitHub Repository URL:");
        if (url == null || url.isBlank()) return;
        Blackboard.getInstance().setRepoUrl(url);
        loadRepoInBackground(url);
    }

    private void loadRepoInBackground(String url) {
        SwingWorker<List<SourceFileInfo>, Void> worker = new SwingWorker<>() {

            @Override
            protected List<SourceFileInfo> doInBackground() throws Exception {
                return new GitHubLoader().loadFiles(url);
            }

            @Override
            protected void done() {
                try {
                    Blackboard.getInstance().setFiles(get());
                } catch (Exception ex) {
                    Blackboard.getInstance().setStatusMessage("Error loading repository.");
                    JOptionPane.showMessageDialog(MainFrame.this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        };
        worker.execute();
    }
}
