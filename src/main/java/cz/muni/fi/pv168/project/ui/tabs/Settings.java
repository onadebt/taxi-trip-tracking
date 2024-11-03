package cz.muni.fi.pv168.project.ui.tabs;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Settings {
    private static double eurToCzkRate = 25.0; // Default rates
    private static double usdToCzkRate = 22.0; // Default rates
    private static double gbpToCzkRate = 28.0; // Default rates
    private static double jpyToCzkRate = 0.15; // Default rates

    public static JPanel createSettingsPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout(FlowLayout.LEFT, 15, 10));
    
        JLabel currencyLabel = new JLabel("Default Currency:");
        JLabel distanceUnitLabel = new JLabel("Default Distance Unit:");
        
        JLabel currencySettingsLabel = new JLabel("Currencies Settings:");
        
        Font labelFont = new Font("Arial", Font.PLAIN, 12);
        currencyLabel.setFont(labelFont);
        distanceUnitLabel.setFont(labelFont);
        currencySettingsLabel.setFont(labelFont);
    
        String[] currencies = {"CZK", "EUR", "USD", "GBP", "JPY"};
        JComboBox<String> currencyComboBox = new JComboBox<>(currencies);
        currencyComboBox.setPreferredSize(new Dimension(100, 20));
    
        String[] distanceUnits = {"km", "miles"};
        JComboBox<String> distanceUnitComboBox = new JComboBox<>(distanceUnits);
        distanceUnitComboBox.setPreferredSize(new Dimension(100, 20));
    
        JButton changeRatesButton = new JButton("Change Rates");
        changeRatesButton.addActionListener(e -> showRateChangeDialog());
    
        panel.add(currencyLabel);
        panel.add(currencyComboBox);
        panel.add(Box.createRigidArea(new Dimension(10, 0)));
        panel.add(distanceUnitLabel);
        panel.add(distanceUnitComboBox);
        
        panel.add(Box.createRigidArea(new Dimension(0, 15)));
        panel.add(currencySettingsLabel);
        panel.add(changeRatesButton);
    
        panel.setPreferredSize(new Dimension(300, 150));
    
        return panel;
    }

    private static void showRateChangeDialog() {
        JDialog dialog = new JDialog((Frame) null, "Change Currency Rates", true);
        dialog.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Adding padding around components
        gbc.fill = GridBagConstraints.HORIZONTAL;
    
        // EUR to CZK Rate
        gbc.gridx = 0;
        gbc.gridy = 0;
        dialog.add(new JLabel("EUR to CZK Rate:"), gbc);
        
        JTextField eurRateField = new JTextField(String.valueOf(eurToCzkRate), 10);
        gbc.gridx = 1;
        dialog.add(eurRateField, gbc);
    
        // USD to CZK Rate
        gbc.gridx = 0;
        gbc.gridy = 1;
        dialog.add(new JLabel("USD to CZK Rate:"), gbc);
        
        JTextField usdRateField = new JTextField(String.valueOf(usdToCzkRate), 10);
        gbc.gridx = 1;
        dialog.add(usdRateField, gbc);
    
        // GBP to CZK Rate
        gbc.gridx = 0;
        gbc.gridy = 2;
        dialog.add(new JLabel("GBP to CZK Rate:"), gbc);
        
        JTextField gbpRateField = new JTextField(String.valueOf(gbpToCzkRate), 10);
        gbc.gridx = 1;
        dialog.add(gbpRateField, gbc);
    
        // JPY to CZK Rate
        gbc.gridx = 0;
        gbc.gridy = 3;
        dialog.add(new JLabel("JPY to CZK Rate:"), gbc);
        
        JTextField jpyRateField = new JTextField(String.valueOf(jpyToCzkRate), 10);
        gbc.gridx = 1;
        dialog.add(jpyRateField, gbc);
    
        // Save Button
        JButton saveButton = new JButton("Save Rates");
        saveButton.addActionListener(e -> {
            try {
                eurToCzkRate = Double.parseDouble(eurRateField.getText());
                usdToCzkRate = Double.parseDouble(usdRateField.getText());
                gbpToCzkRate = Double.parseDouble(gbpRateField.getText());
                jpyToCzkRate = Double.parseDouble(jpyRateField.getText());
                JOptionPane.showMessageDialog(dialog, "Rates updated successfully!");
                dialog.dispose();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(dialog, "Invalid rate value. Please enter valid numbers.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        dialog.add(saveButton, gbc);
    
        dialog.setSize(400, 300);
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);
    }

    public static double getEurToCzkRate() {
        return eurToCzkRate;
    }
    public static double getUsdToCzkRate() {
        return usdToCzkRate;
    }
    public static double getGbpToCzkRate() {
        return gbpToCzkRate;
    }
    public static double getJpyToCzkRate() {
        return jpyToCzkRate;
    }
}
