package org.example;

import javax.swing.*;
import java.awt.*;

/**
 * @author babaldeep and yaniel
 */

public class Main extends JFrame {

    public static void main(String[] a) {
        Main work = new Main();
        work.setSize(900, 650);
        work.setTitle("Assignment 01");
        work.setVisible(true);
    }

    public Main() {
        this.setLayout(new BorderLayout());

        URLPanel urlPanel = new URLPanel();
        this.add(urlPanel, BorderLayout.NORTH);

        GridPanel gridPanel = new GridPanel();
        this.add(gridPanel, BorderLayout.CENTER);
    }
}