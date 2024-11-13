package cz.muni.fi.pv168.project.ui.tabs;

import cz.muni.fi.pv168.project.model.enums.DistanceUnit;
import cz.muni.fi.pv168.project.providers.DIProvider;
import cz.muni.fi.pv168.project.service.interfaces.IRideService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Settings extends JPanel {
    private DIProvider diProvider;
    private static DistanceUnit defaultDistanceUnit = DistanceUnit.Kilometer;

    private Settings(DIProvider diProvider) {
        super(new BorderLayout());
        this.diProvider = diProvider;


        JPanel panel = new JPanel();
        this.setLayout(new FlowLayout(FlowLayout.LEFT, 15, 10));

        JLabel currencyLabel = new JLabel("Default Currency:");
        JLabel distanceUnitLabel = new JLabel("Default Distance Unit:");
        Font labelFont = new Font("Arial", Font.PLAIN, 12);
        currencyLabel.setFont(labelFont);
        distanceUnitLabel.setFont(labelFont);

        String[] currencies = {"CZK", "EUR", "USD", "GBP", "JPY"};
        JComboBox<String> currencyComboBox = new JComboBox<>(currencies);
        currencyComboBox.setPreferredSize(new Dimension(100, 20));

        JComboBox<DistanceUnit> distanceUnitComboBox = new JComboBox<>(DistanceUnit.values());
        distanceUnitComboBox.setPreferredSize(new Dimension(100, 20));

        JButton applyButton = new JButton("Apply");
        applyButton.setPreferredSize(new Dimension(80, 25));
        applyButton.addActionListener(e -> {
            DistanceUnit selectedUnit = (DistanceUnit) distanceUnitComboBox.getSelectedItem();
            if (selectedUnit != null && selectedUnit != defaultDistanceUnit) {
                setDefaultDistanceUnit(selectedUnit);
                diProvider.getRideService().recalculateDistances(selectedUnit);

                JOptionPane.showMessageDialog(this,
                        "Distance unit has been changed to " + selectedUnit + " and distances have been recalculated.",
                        "Distance Unit Changed",
                        JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this,
                        "The selected distance unit is already the default unit.",
                        "No Change Made",
                        JOptionPane.WARNING_MESSAGE);
            }
        });

        this.add(currencyLabel);
        this.add(currencyComboBox);
        this.add(Box.createRigidArea(new Dimension(10, 0)));
        this.add(distanceUnitLabel);
        this.add(distanceUnitComboBox);
        this.add(applyButton);  // Add the Apply button
        this.setPreferredSize(new Dimension(300, 150));

    }

    public static JPanel createSettingsPanel(DIProvider diProvider) {
        return new Settings(diProvider);
    }

    public static DistanceUnit getDefaultDistanceUnit() {
        return defaultDistanceUnit;
    }

    public static void setDefaultDistanceUnit(DistanceUnit unit) {
        defaultDistanceUnit = unit;
    }
}
