package cz.muni.fi.pv168.project.ui.tabs;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RidesCategories {
    public static JPanel createRidesCategoriesPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());

        JLabel label = new JLabel("User can add, edit and rename categories of his rides.");
        panel.add(label);

        JButton closeButton = new JButton("Close app.");
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
