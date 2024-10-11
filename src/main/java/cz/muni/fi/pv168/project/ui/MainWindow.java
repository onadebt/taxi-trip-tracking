package cz.muni.fi.pv168.project.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainWindow {

    private final JFrame frame;

    public MainWindow() {
        frame = new JFrame("Taxi trip tracking");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1280, 800);
        frame.setLocationRelativeTo(null);

        JButton closeButton = new JButton("Close taxi trip tracking");
        closeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        // Add the button to the frame
        frame.getContentPane().setLayout(new FlowLayout());
        frame.getContentPane().add(closeButton);
    }

    // Method to show the window
    public void show() {
        frame.setVisible(true);
    }
}