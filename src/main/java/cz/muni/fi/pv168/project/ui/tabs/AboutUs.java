package cz.muni.fi.pv168.project.ui.tabs;

import javax.swing.*;
import java.awt.*;

public class AboutUs {
    public static JPanel createAboutUsPanel() {
        JPanel aboutUsPanel = new JPanel();
        aboutUsPanel.setLayout(new BoxLayout(aboutUsPanel, BoxLayout.Y_AXIS));
        aboutUsPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel namesLabel = new JLabel("Team Members:");
        JTextArea namesText = new JTextArea(
                """
                        Dorián Viktora
                        Jan VondrášekS
                        Bc. Jan Fabšič
                        Bc. Ivan Yatskiv"""
        );
        namesText.setEditable(false);
        namesText.setBackground(aboutUsPanel.getBackground());
        namesText.setFont(new Font("Arial", Font.PLAIN, 14));

        JLabel descriptionLabel = new JLabel("About the Application:");
        JTextArea descriptionText = new JTextArea("This Taxi Drivers Tracking App allows drivers to manually "
                + "record and track their rides. It helps manage ride history, track earnings, "
                + "and keep a detailed log of each trip.");
        descriptionText.setWrapStyleWord(true);
        descriptionText.setLineWrap(true);
        descriptionText.setEditable(false);
        descriptionText.setBackground(aboutUsPanel.getBackground());
        descriptionText.setFont(new Font("Arial", Font.PLAIN, 14));

        JLabel pluginsLabel = new JLabel("Used Plugins/Third-Party Software:");
        JTextArea pluginsText = new JTextArea("""
                1. JCalendar for date picking""");
        pluginsText.setEditable(false);
        pluginsText.setBackground(aboutUsPanel.getBackground());
        pluginsText.setFont(new Font("Arial", Font.PLAIN, 14));

        aboutUsPanel.add(namesLabel);
        aboutUsPanel.add(Box.createVerticalStrut(5));
        aboutUsPanel.add(namesText);
        aboutUsPanel.add(Box.createVerticalStrut(15));
        aboutUsPanel.add(descriptionLabel);
        aboutUsPanel.add(Box.createVerticalStrut(5));
        aboutUsPanel.add(new JScrollPane(descriptionText));
        aboutUsPanel.add(Box.createVerticalStrut(15));
        aboutUsPanel.add(pluginsLabel);
        aboutUsPanel.add(Box.createVerticalStrut(5));
        aboutUsPanel.add(pluginsText);

        return aboutUsPanel;
    }
}
