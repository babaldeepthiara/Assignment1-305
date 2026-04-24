package org.example;

import javax.swing.*;
import java.awt.*;

public class URLPanel extends JPanel {
    private final JTextField repoField;
    private final JButton okButton;
    private final JTextField selectedFileField;

    public URLPanel() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel topRow = new JPanel(new BorderLayout(10, 10));
        repoField = new JTextField();
        okButton = new JButton("OK");

        topRow.add(repoField, BorderLayout.CENTER);
        topRow.add(okButton, BorderLayout.EAST);

        JPanel bottomRow = new JPanel(new BorderLayout(10, 10));
        JLabel selectedLabel = new JLabel("Selected File Name");
        selectedFileField = new JTextField();
        selectedFileField.setEditable(false);

        bottomRow.add(selectedLabel, BorderLayout.NORTH);
        bottomRow.add(selectedFileField, BorderLayout.CENTER);

        add(topRow, BorderLayout.NORTH);
        add(bottomRow, BorderLayout.SOUTH);

        okButton.addActionListener(e -> {
            String path = repoField.getText().trim();
            Blackboard.getInstance().setRepoPath(path);

            Worker worker = new Worker();
            Thread thread = new Thread(worker);
            thread.start();
        });

        Timer timer = new Timer(200, e ->
                selectedFileField.setText(Blackboard.getInstance().getSelectedFileName()));
        timer.start();
    }
}