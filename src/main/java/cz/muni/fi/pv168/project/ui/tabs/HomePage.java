package cz.muni.fi.pv168.project.ui.tabs;

import cz.muni.fi.pv168.project.ui.action.NewRideAction;
import cz.muni.fi.pv168.project.ui.model.CategoryListModel;
import cz.muni.fi.pv168.project.ui.model.CurrencyListModel;
import cz.muni.fi.pv168.project.ui.model.RideModel;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class HomePage {

    public static JPanel createHomePagePanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        JPanel statsPanel = new JPanel();
        statsPanel.setLayout(new GridLayout(2, 3, 10, 10)); // 2 rows, 3 columns for labels and values

        List<RideModel> rideHistory = RidesHistory.getSampleRideHistory();

        double totalAmount = calculateTotalAmount(rideHistory);
        double totalDistance = calculateTotalDistance(rideHistory);
        int totalDrives = rideHistory.size();

        JLabel totalDistanceLabel = new JLabel("Total Distance:");
        JLabel totalMoneyLabel = new JLabel("Total Money Earned:");
        JLabel totalDrivesLabel = new JLabel("Total Rides:");

        JLabel totalDistanceValue = new JLabel(String.format("%.2f km", totalDistance));
        JLabel totalMoneyValue = new JLabel(String.format("%.2f CZK", totalAmount));
        JLabel totalDrivesValue = new JLabel(String.valueOf(totalDrives));

        Font labelFont = new Font("Arial", Font.PLAIN, 16);
        Font valueFont = new Font("Arial", Font.BOLD, 24);

        totalDistanceLabel.setFont(labelFont);
        totalMoneyLabel.setFont(labelFont);
        totalDrivesLabel.setFont(labelFont);

        totalDistanceValue.setFont(valueFont);
        totalMoneyValue.setFont(valueFont);
        totalDrivesValue.setFont(valueFont);

        totalDrivesLabel.setBorder(BorderFactory.createEmptyBorder(0, 30, 0, 0));
        totalDrivesValue.setBorder(BorderFactory.createEmptyBorder(0, 80, 0, 0));

        statsPanel.add(totalDistanceLabel);
        statsPanel.add(totalMoneyLabel);
        statsPanel.add(totalDrivesLabel);
        statsPanel.add(totalDistanceValue);
        statsPanel.add(totalMoneyValue);
        statsPanel.add(totalDrivesValue);

        statsPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK), "Ride Statistics"));

        // Set a preferred size for the stats panel to make it smaller
        statsPanel.setPreferredSize(new Dimension(600, 200));

        // Use GridBagLayout for centering
        JPanel centerPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(10, 0, 10, 0); // Add some padding between components

        centerPanel.add(statsPanel, gbc);

        // Add the button below the stats panel
        JButton addButton = new JButton("Add");
        addButton.addActionListener(new NewRideAction(panel, new CurrencyListModel(new ArrayList<>()), new CategoryListModel(new ArrayList<>())));

        addButton.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        gbc.gridy = 1;  // Move to the next row for the button
        gbc.anchor = GridBagConstraints.CENTER;  // Center the button
        centerPanel.add(addButton, gbc);

        panel.add(centerPanel, BorderLayout.CENTER);

        return panel;
    }

    private static double calculateTotalAmount(List<RideModel> rideHistory) {
        double total = 0;
        double eurToCzkRate = 25.0;
        double usdToCzkRate = 22.0;

        for (RideModel ride : rideHistory) {
            double tempAmount = ride.getAmountCurrency();
            String currency = ride.getCurrency();

            if (currency.equals("EUR")) {
                total += tempAmount * eurToCzkRate;
            } else if (currency.equals("USD")) {
                total += tempAmount * usdToCzkRate;
            } else { // CZK
                total += tempAmount;
            }
        }
        return total;
    }

    private static double calculateTotalDistance(List<RideModel> rideHistory) {
        double totalDistance = 0;
        for (RideModel ride : rideHistory) {
            totalDistance += ride.getDistance();
        }
        return totalDistance;
    }
}
