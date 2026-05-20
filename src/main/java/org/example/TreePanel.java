package org.example;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Displays loaded source files in a hierarchical tree reflecting the
 * actual repository directory structure (e.g. src → main → java → File.java).
 * Reacts only to REPO_LOADED events.
 *
 * @author babaldeep and yaneli
 * @version 3.0
 */

public class TreePanel extends JPanel implements AppObserver {

    private final JTree tree;
    private final DefaultMutableTreeNode root;

    public TreePanel() {
        setLayout(new BorderLayout());
        root = new DefaultMutableTreeNode("Repository");
        tree = new JTree(root);
        add(new JScrollPane(tree), BorderLayout.CENTER);
        Blackboard.getInstance().addObserver(this);
    }

    @Override
    public void onEvent(AppEvent event) {
        if (event == AppEvent.REPO_LOADED) refreshTree();
    }

    private void refreshTree() {
        root.removeAllChildren();

        List<SourceFileInfo> files = Blackboard.getInstance().getFiles();

        Map<String, DefaultMutableTreeNode> nodeMap = new HashMap<>();
        nodeMap.put("", root);

        for (SourceFileInfo file : files) {
            String path = file.getFilePath();          
            String[] parts = path.split("/");

            StringBuilder current = new StringBuilder();
            DefaultMutableTreeNode parent = root;

            for (int i = 0; i < parts.length; i++) {
                if (i > 0) current.append("/");
                current.append(parts[i]);

                String key = current.toString();
                if (!nodeMap.containsKey(key)) {
                    DefaultMutableTreeNode node = new DefaultMutableTreeNode(parts[i]);
                    parent.add(node);
                    nodeMap.put(key, node);
                }

                parent = nodeMap.get(key);
            }
        }

        ((DefaultTreeModel) tree.getModel()).reload();

        for (int i = 0; i < tree.getRowCount(); i++) tree.expandRow(i);
    }
}
