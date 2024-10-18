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

        statsPanel.add(totalDistanceLabel);
        statsPanel.add(totalMoneyLabel);
        statsPanel.add(totalDrivesLabel);
        statsPanel.add(totalDistanceValue);
        statsPanel.add(totalMoneyValue);
        statsPanel.add(totalDrivesValue);

        statsPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK), "Ride Statistics"));

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.add(Box.createVerticalGlue());
        centerPanel.add(statsPanel);
        centerPanel.add(Box.createVerticalGlue());

        panel.add(centerPanel, BorderLayout.CENTER);

        JButton addButton = new JButton("Add");
        addButton.addActionListener(new NewRideAction(panel, new CurrencyListModel(new ArrayList<>()), new CategoryListModel(new ArrayList<>())));

        addButton.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        centerPanel.add(Box.createVerticalStrut(10));
        centerPanel.add(addButton);

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
