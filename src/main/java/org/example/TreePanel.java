package org.example;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.*;
import java.util.List;

/**
 * Displays loaded source files in a tree structure.
 *
 * @author babaldeep and yaneli
 * @version 1.0
 */

public class TreePanel extends JPanel implements AppObserver {

    private final JTree tree;
    private final DefaultMutableTreeNode root;

    public TreePanel() {

        setLayout(new BorderLayout());

        root = new DefaultMutableTreeNode("Repository Files");
        tree = new JTree(root);

        add(new JScrollPane(tree), BorderLayout.CENTER);

        Blackboard.getInstance().addObserver(this);
    }

    @Override
    public void onEvent(AppEvent event) {

        if (event == AppEvent.REPO_LOADED) {
            refreshTree();
        }
    }

    private void refreshTree() {

        root.removeAllChildren();

        List<SourceFileInfo> files =
                Blackboard.getInstance().getFiles();

        for (SourceFileInfo file : files) {
            root.add(new DefaultMutableTreeNode(file.getFileName()));
        }

        ((javax.swing.tree.DefaultTreeModel)
                tree.getModel()).reload();
    }
}