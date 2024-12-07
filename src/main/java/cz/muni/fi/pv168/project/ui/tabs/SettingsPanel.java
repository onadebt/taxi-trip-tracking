package cz.muni.fi.pv168.project.ui.tabs;

import cz.muni.fi.pv168.project.model.Settings;
import cz.muni.fi.pv168.project.model.enums.DistanceUnit;
import cz.muni.fi.pv168.project.service.RideService;
import cz.muni.fi.pv168.project.service.SettingsService;
import cz.muni.fi.pv168.project.service.interfaces.ISettingsService;

import javax.swing.*;
import java.awt.*;

public class SettingsPanel extends JPanel {
    private final ISettingsService settingsService;
    private final RideService rideService;

    public SettingsPanel(ISettingsService settingsService, RideService rideService){
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
        distanceUnitComboBox.setSelectedItem(settingsService.getSettings().map(Settings::getDefaultDistanceUnit).orElse(DistanceUnit.KILOMETER));

        JButton applyButton = new JButton("Apply");
        applyButton.setPreferredSize(new Dimension(80, 25));
        applyButton.addActionListener(e -> {
            DistanceUnit selectedUnit = (DistanceUnit) distanceUnitComboBox.getSelectedItem();

            Settings settings = settingsService.getSettings().orElse(new Settings(null));
            if (selectedUnit != null && !selectedUnit.equals(settings.getDefaultDistanceUnit())) {
                rideService.recalculateDistances(settingsService.getDefaultDistance(), selectedUnit);
                settings.setDefaultDistanceUnit(selectedUnit);
            }

            settingsService.saveSettings(settings);

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
