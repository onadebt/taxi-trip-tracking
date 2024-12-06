package cz.muni.fi.pv168.project.ui.tabs;

import cz.muni.fi.pv168.project.model.enums.DistanceUnit;
import cz.muni.fi.pv168.project.service.interfaces.IRideService;
import cz.muni.fi.pv168.project.ui.model.RideTableModel;

import javax.swing.*;
import java.awt.*;

public class Settings extends JPanel {
    private static DistanceUnit defaultDistanceUnit = DistanceUnit.Kilometer;

    public Settings(IRideService rideService) {
        super(new BorderLayout());

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
                rideService.recalculateDistances(selectedUnit);

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
        this.add(applyButton);
        this.setPreferredSize(new Dimension(300, 150));
    }

    public static DistanceUnit getDefaultDistanceUnit() {
        return defaultDistanceUnit;
    }

    public static void setDefaultDistanceUnit(DistanceUnit unit) {
        defaultDistanceUnit = unit;
    }
}
