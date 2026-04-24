package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Panel that allows the user to input a repository URL and displays the selected file name.
 * On clicking OK, it updates the repository URL on the Blackboard and triggers a refresh of the
 * 
 * @author babaldeep and yaneli
 */

public class URLPanel extends JPanel implements ActionListener {

    private JTextField urlField;
    private JButton okButton;
    private JTextField selectedFileField;

    public URLPanel() {
        this.setLayout(new BorderLayout());

        JPanel topPanel = new JPanel(new BorderLayout());
        urlField = new JTextField();
        okButton = new JButton("OK");

        topPanel.add(urlField, BorderLayout.CENTER);
        topPanel.add(okButton, BorderLayout.EAST);

        JPanel bottomPanel = new JPanel(new BorderLayout());
        JLabel label = new JLabel("Selected File Name");
        selectedFileField = new JTextField();

        bottomPanel.add(label, BorderLayout.NORTH);
        bottomPanel.add(selectedFileField, BorderLayout.CENTER);

        this.add(topPanel, BorderLayout.NORTH);
        this.add(bottomPanel, BorderLayout.SOUTH);

        okButton.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String url = urlField.getText();
        Blackboard.getInstance().setRepoPath(url);
        System.out.println("URL: " + url);
    }
}
