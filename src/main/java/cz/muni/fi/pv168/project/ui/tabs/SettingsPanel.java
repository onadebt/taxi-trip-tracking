package cz.muni.fi.pv168.project.ui.tabs;

import cz.muni.fi.pv168.project.model.Settings;
import cz.muni.fi.pv168.project.model.enums.DistanceUnit;
import cz.muni.fi.pv168.project.service.RideService;
import cz.muni.fi.pv168.project.service.SettingsService;
import cz.muni.fi.pv168.project.service.interfaces.IRideService;
import cz.muni.fi.pv168.project.service.interfaces.ISettingsService;

import javax.swing.*;
import java.awt.*;

public class SettingsPanel extends JPanel {
    private final ISettingsService settingsService;
    private final IRideService rideService;

    public SettingsPanel(ISettingsService settingsService, IRideService rideService){
        super(new BorderLayout());
        this.settingsService = settingsService;
        this.rideService = rideService;

        JPanel panel = new JPanel();
        this.setLayout(new FlowLayout(FlowLayout.LEFT, 15, 10));

        JLabel distanceUnitLabel = new JLabel("Default Distance Unit:");
        Font labelFont = new Font("Arial", Font.PLAIN, 12);
        distanceUnitLabel.setFont(labelFont);


        JComboBox<DistanceUnit> distanceUnitComboBox = new JComboBox<>(DistanceUnit.values());
        distanceUnitComboBox.setPreferredSize(new Dimension(100, 20));
        distanceUnitComboBox.setSelectedItem(settingsService.getDefaultDistance());

        JButton applyButton = new JButton("Apply");
        applyButton.setPreferredSize(new Dimension(80, 25));
        applyButton.addActionListener(e -> {
            DistanceUnit selectedUnit = (DistanceUnit) distanceUnitComboBox.getSelectedItem();

            if (selectedUnit != settingsService.getDefaultDistance()) {
                rideService.recalculateDistances(settingsService.getDefaultDistance(), selectedUnit);
                settingsService.setDefaultDistance(selectedUnit);
            }


            JOptionPane.showMessageDialog(this,
                    "Settings have been updated.",
                    "Settings Updated",
                    JOptionPane.INFORMATION_MESSAGE);
        });

        this.add(Box.createRigidArea(new Dimension(10, 0)));
        this.add(distanceUnitLabel);
        this.add(distanceUnitComboBox);
        this.add(applyButton);
        this.setPreferredSize(new Dimension(300, 150));
    }
}
