package cz.muni.fi.pv168.project.ui.tabs;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HomePage {
    public static JPanel createHomePagePanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());

        JButton closeButton = new JButton("Close button on first tab.");
        closeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        panel.add(closeButton);
        return panel;
    }
}
