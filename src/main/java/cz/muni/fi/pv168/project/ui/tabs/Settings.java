package cz.muni.fi.pv168.project.ui.tabs;

import javax.swing.*;
import java.awt.*;


public class Settings {

    public static JPanel createSettingsPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout(FlowLayout.LEFT, 15, 10));

        JLabel currencyLabel = new JLabel("Default Currency:");
        JLabel distanceUnitLabel = new JLabel("Default Distance Unit:");

        Font labelFont = new Font("Arial", Font.PLAIN, 12);
        currencyLabel.setFont(labelFont);
        distanceUnitLabel.setFont(labelFont);

        String[] currencies = {"CZK", "EUR", "USD"};
        JComboBox<String> currencyComboBox = new JComboBox<>(currencies);
        currencyComboBox.setPreferredSize(new Dimension(100, 20));

        String[] distanceUnits = {"km", "miles"};
        JComboBox<String> distanceUnitComboBox = new JComboBox<>(distanceUnits);
        distanceUnitComboBox.setPreferredSize(new Dimension(100, 20));

        panel.add(currencyLabel);
        panel.add(currencyComboBox);
        panel.add(Box.createRigidArea(new Dimension(10, 0)));
        panel.add(distanceUnitLabel);
        panel.add(distanceUnitComboBox);

        panel.setPreferredSize(new Dimension(300, 100));

        return panel;
    }
}
