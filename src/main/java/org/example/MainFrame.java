package org.example;
import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * Main application window titled "Assignment 02".
 * Assembles the menu bar, grid area, and status bar.
 * Runs GitHub loading on a background thread to keep the UI responsive.
 *
 * @author babaldeep and yaneli
 * @version 2.0
 * 
 */

public class MainFrame extends JFrame {

    private GridPanel gridPanel;

    public MainFrame() {
        super("Assignment 02");
        buildGUI();
    }

    private void buildGUI() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 650);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(0, 0));

        setJMenuBar(buildMenuBar());

        gridPanel = new GridPanel();
        JScrollPane scrollPane = new JScrollPane(gridPanel);
        add(scrollPane, BorderLayout.CENTER);
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

        JMenuItem openItem = new JMenuItem("Open from URL…");
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
            if (url == null || url.isBlank()) {
                JOptionPane.showMessageDialog(this,
                    "No repository loaded. Use File → Open from URL… first.",
                    "Nothing to Reload", JOptionPane.INFORMATION_MESSAGE);
            } 
            
            else {
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
        aboutItem.addActionListener(e -> JOptionPane.showMessageDialog(this,
            "Assignment 02 – GitHub Repository Analyzer\n"
            + "Authors: babaldeep and yaneli\n"
            + "Version: 2.0\n\n"
            + "Color legend:\n"
            + "  Green  – CC < 5\n"
            + "  Yellow – CC 5–9\n"
            + "  Red    – CC ≥ 10\n"
            + "Transparency scales with LOC (more opaque = more lines).",
            "About", JOptionPane.INFORMATION_MESSAGE));

        menu.add(aboutItem);
        return menu;
    }

    private void promptForRepoUrl() {
        String url = JOptionPane.showInputDialog(
            this,
            "Enter GitHub Repository URL:",
            "Open from URL",
            JOptionPane.PLAIN_MESSAGE
        );
        if (url == null || url.trim().isEmpty()) return;

        url = url.trim();

        if (!url.startsWith("https://github.com/")) {
            JOptionPane.showMessageDialog(this,
                "Please enter a valid GitHub repository URL.\n"
                + "Example: https://github.com/owner/repo",
                "Invalid URL", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String[] parts = url.replace("https://github.com/", "").split("/");
        if (parts.length < 2 || parts[0].isBlank() || parts[1].isBlank()) {
            JOptionPane.showMessageDialog(this,
                "URL must include both an owner and a repository name.\n"
                + "Example: https://github.com/owner/repo",
                "Invalid URL", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Blackboard.getInstance().setRepoUrl(url);
        loadRepoInBackground(url);
    }

    private void loadRepoInBackground(String url) {
        SwingWorker<List<SourceFileInfo>, Void> worker = new SwingWorker<>() {
            @Override
            protected List<SourceFileInfo> doInBackground() throws Exception {
                GitHubLoader loader = new GitHubLoader();
                return loader.loadFiles(url);
            }

            @Override
            protected void done() {
                try {
                    Blackboard.getInstance().setFiles(get());
                } 
                
                catch (Exception ex) {
                    Blackboard.getInstance().setStatusMessage("Error: " + ex.getMessage());
                    JOptionPane.showMessageDialog(MainFrame.this,
                        "Failed to load repository:\n" + ex.getMessage(),
                        "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        };

        worker.execute();
    }
}
