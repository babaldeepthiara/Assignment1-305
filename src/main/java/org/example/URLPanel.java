package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author babaldeep and yaniel
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
        Blackboard.getInstance().setRepoURL(url);
        System.out.println("URL: " + url);
    }
}