//package cz.muni.fi.pv168.project.ui.tabs;
//
//import cz.muni.fi.pv168.project.ui.action.NewRideAction;
//import cz.muni.fi.pv168.project.ui.model.CategoryListModel;
//import cz.muni.fi.pv168.project.ui.model.CurrencyListModel;
//import cz.muni.fi.pv168.project.ui.model.RideModel;
//
//import javax.swing.*;
//import java.awt.*;
//import java.util.ArrayList;
//import java.util.List;
//
//public class HomePage {
//
//    public static JPanel createHomePagePanel() {
//        JPanel panel = new JPanel();
//        panel.setLayout(new BorderLayout());
//
//        JPanel statsPanel = new JPanel();
//        statsPanel.setLayout(new GridLayout(2, 3, 10, 10)); // 2 rows, 3 columns for labels and values
//
//        List<RideModel> rideHistory = RidesHistory.getSampleRideHistory();
//
//        double totalAmount = calculateTotalAmount(rideHistory);
//        double totalDistance = calculateTotalDistance(rideHistory);
//        int totalDrives = rideHistory.size();
//
//        JLabel totalDistanceLabel = new JLabel("Total Distance:");
//        JLabel totalMoneyLabel = new JLabel("Total Money Earned:");
//        JLabel totalDrivesLabel = new JLabel("Total Rides:");
//
//        JLabel totalDistanceValue = new JLabel(String.format("%.2f km", totalDistance));
//        JLabel totalMoneyValue = new JLabel(String.format("%.2f CZK", totalAmount));
//        JLabel totalDrivesValue = new JLabel(String.valueOf(totalDrives));
//
//        Font labelFont = new Font("Arial", Font.PLAIN, 16);
//        Font valueFont = new Font("Arial", Font.BOLD, 24);
//
//        totalDistanceLabel.setFont(labelFont);
//        totalMoneyLabel.setFont(labelFont);
//        totalDrivesLabel.setFont(labelFont);
//
//        totalDistanceValue.setFont(valueFont);
//        totalMoneyValue.setFont(valueFont);
//        totalDrivesValue.setFont(valueFont);
//
//        totalDrivesLabel.setBorder(BorderFactory.createEmptyBorder(0, 30, 0, 0));
//        totalDrivesValue.setBorder(BorderFactory.createEmptyBorder(0, 80, 0, 0));
//
//        statsPanel.add(totalDistanceLabel);
//        statsPanel.add(totalMoneyLabel);
//        statsPanel.add(totalDrivesLabel);
//        statsPanel.add(totalDistanceValue);
//        statsPanel.add(totalMoneyValue);
//        statsPanel.add(totalDrivesValue);
//
//        statsPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK), "Ride Statistics"));
//
//        statsPanel.setPreferredSize(new Dimension(600, 200));
//
//        JPanel centerPanel = new JPanel(new GridBagLayout());
//        GridBagConstraints gbc = new GridBagConstraints();
//        gbc.gridx = 0;
//        gbc.gridy = 0;
//        gbc.anchor = GridBagConstraints.CENTER;
//        gbc.insets = new Insets(10, 0, 10, 0);
//
//        centerPanel.add(statsPanel, gbc);
//
//        // Add the button below the stats panel
//        JButton addButton = new JButton("Add");
//        addButton.addActionListener(new NewRideAction(panel, new CurrencyListModel(new ArrayList<>()), new CategoryListModel(new ArrayList<>())));
//
//        addButton.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
//        gbc.gridy = 1;  // Move to the next row for the button
//        gbc.anchor = GridBagConstraints.CENTER;  // Center the button
//        centerPanel.add(addButton, gbc);
//
//        panel.add(centerPanel, BorderLayout.CENTER);
//
//        return panel;
//    }
//
//    private static double calculateTotalAmount(List<RideModel> rideHistory) {
//        double total = 0;
//        double eurToCzkRate = 25.0;
//        double usdToCzkRate = 22.0;
//
//        for (RideModel ride : rideHistory) {
//            double tempAmount = ride.getAmountCurrency();
//            String currency = ride.getCurrency();
//
//            if (currency.equals("EUR")) {
//                total += tempAmount * eurToCzkRate;
//            } else if (currency.equals("USD")) {
//                total += tempAmount * usdToCzkRate;
//            } else { // CZK
//                total += tempAmount;
//            }
//        }
//        return total;
//    }
//
//    private static double calculateTotalDistance(List<RideModel> rideHistory) {
//        double totalDistance = 0;
//        for (RideModel ride : rideHistory) {
//            totalDistance += ride.getDistance();
//        }
//        return totalDistance;
//    }
//}




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
        JPanel panel = new JPanel(new BorderLayout());

        JPanel filterPanel = createFilterPanel();
        panel.add(filterPanel, BorderLayout.NORTH);

        JPanel statsPanel = createStatsPanel(RidesHistory.getSampleRideHistory());
        panel.add(statsPanel, BorderLayout.CENTER);

        JPanel snapshotPanel = createLastRidesPanel(RidesHistory.getSampleRideHistory());
        panel.add(snapshotPanel, BorderLayout.SOUTH);

        JButton addButton = new JButton("Add Ride");
        addButton.addActionListener(new NewRideAction(panel, new CurrencyListModel(new ArrayList<>()), new CategoryListModel(new ArrayList<>())));
        addButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel centerPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(10, 0, 10, 0);

        centerPanel.add(filterPanel, gbc);

        gbc.gridy = 1;
        centerPanel.add(statsPanel, gbc);

        gbc.gridy = 2;
        centerPanel.add(addButton, gbc);

        gbc.gridy = 3;
        centerPanel.add(snapshotPanel, gbc);

        panel.add(centerPanel, BorderLayout.CENTER);

        return panel;
    }

    private static JPanel createFilterPanel() {
        JPanel filterPanel = new JPanel();
        filterPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

        JLabel filterLabel = new JLabel("Filter by this: ");
        JRadioButton dayButton = new JRadioButton("Day");
        JRadioButton weekButton = new JRadioButton("Week");
        JRadioButton monthButton = new JRadioButton("Month");

        ButtonGroup group = new ButtonGroup();
        group.add(dayButton);
        group.add(weekButton);
        group.add(monthButton);

        filterPanel.add(filterLabel);
        filterPanel.add(dayButton);
        filterPanel.add(weekButton);
        filterPanel.add(monthButton);

        return filterPanel;
    }

    private static JPanel createStatsPanel(List<RideModel> rideHistory) {
        JPanel statsPanel = new JPanel(new GridLayout(2, 3, 10, 10));

        double totalAmount = calculateTotalAmount(rideHistory);
        double totalDistance = calculateTotalDistance(rideHistory);
        int totalDrives = rideHistory.size();

        addStatLabel(statsPanel, "Total Distance:");
        addStatLabel(statsPanel, "Total Money Earned:");
        addStatLabel(statsPanel, "Total Rides:");

        addStatValue(statsPanel, String.format("%.2f km", totalDistance));
        addStatValue(statsPanel, String.format("%.2f CZK", totalAmount));
        addStatValue(statsPanel, String.format("%d", totalDrives));

        statsPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK), "Ride Statistics"));
        statsPanel.setPreferredSize(new Dimension(600, 200));

        return statsPanel;
    }

    private static JPanel createLastRidesPanel(List<RideModel> rideHistory) {
        JPanel snapshotPanel = new JPanel();
        snapshotPanel.setLayout(new BoxLayout(snapshotPanel, BoxLayout.Y_AXIS));

        int start = Math.max(rideHistory.size() - 3, 0);
        List<RideModel> lastRides = rideHistory.subList(start, rideHistory.size());

        String[] labels = {"1st Last Ride", "2nd Last Ride", "3rd Last Ride"};
        for (int i = 0; i < lastRides.size(); i++) {
            RideModel ride = lastRides.get(i);
            JPanel ridePanel = createSingleRidePanel(ride, labels[i]);
            snapshotPanel.add(ridePanel);
            snapshotPanel.add(Box.createRigidArea(new Dimension(0, 10))); // Add space outside the border
        }

        return snapshotPanel;
    }

    private static JPanel createSingleRidePanel(RideModel ride, String title) {
        JPanel ridePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        ridePanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK), title));
        ridePanel.setBorder(BorderFactory.createCompoundBorder(
                ridePanel.getBorder(),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));

        JLabel rideLabel = new JLabel(String.format("%s, Distance: %.2f km, Amount: %.2f %s",
                ride.getCategoryName(), ride.getDistance(), ride.getAmountCurrency(), ride.getCurrency()));
        rideLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        ridePanel.add(rideLabel);

        return ridePanel;
    }

    private static void addStatLabel(JPanel panel, String label) {
        JLabel statLabel = new JLabel(label);
        statLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        panel.add(statLabel);
    }

    private static void addStatValue(JPanel panel, String value) {
        JLabel statValue = new JLabel(value);
        statValue.setFont(new Font("Arial", Font.BOLD, 24));
        panel.add(statValue);
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
            } else {
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