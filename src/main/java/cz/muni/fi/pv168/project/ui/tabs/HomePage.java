package cz.muni.fi.pv168.project.ui.tabs;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class HomePage {

    // store records (distance and money)
    private static java.util.List<String> records = new ArrayList<>();

    // total stats
    private static double totalDistance = 0.0;
    private static double totalMoney = 0.0;
    private static int totalDrives = 0;

    private static final JLabel totalDistanceLabelHeader = new JLabel("Total Distance:");
    private static final JLabel totalMoneyLabelHeader = new JLabel("Total Money:");
    private static final JLabel totalDrivesLabelHeader = new JLabel("Total Drives:");
    private static JLabel totalDistanceLabel = new JLabel("0");
    private static JLabel totalMoneyLabel = new JLabel("0");
    private static JLabel totalDrivesLabel = new JLabel("0");


    public static JPanel createHomePagePanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new FlowLayout());

        JLabel distanceLabel = new JLabel("Distance traveled (km):");
        JTextField distanceField = new JTextField(10); // 10 columns wide

        JLabel moneyLabel = new JLabel("Money earned (Kč):");
        JTextField moneyField = new JTextField(10);

        JButton addButton = new JButton("Add");

        // temp area to display added records
        JTextArea recordArea = new JTextArea(10, 30);
        recordArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(recordArea);

        JPanel statsPanel = new JPanel();
        statsPanel.setLayout(new GridLayout(2, 3, 10, 10));

        inputPanel.add(distanceLabel);
        inputPanel.add(distanceField);
        inputPanel.add(moneyLabel);
        inputPanel.add(moneyField);
        inputPanel.add(addButton);

        totalDistanceLabel.setFont(new Font("Arial", Font.BOLD, 24));
        totalMoneyLabel.setFont(new Font("Arial", Font.BOLD, 24));
        totalDrivesLabel.setFont(new Font("Arial", Font.BOLD, 24));

        totalDistanceLabel.setHorizontalAlignment(SwingConstants.CENTER);
        totalMoneyLabel.setHorizontalAlignment(SwingConstants.CENTER);
        totalDrivesLabel.setHorizontalAlignment(SwingConstants.CENTER);

        statsPanel.add(totalDistanceLabelHeader);
        statsPanel.add(totalMoneyLabelHeader);
        statsPanel.add(totalDrivesLabelHeader);
        statsPanel.add(totalDistanceLabel);
        statsPanel.add(totalMoneyLabel);
        statsPanel.add(totalDrivesLabel);

        Border lineBorder = BorderFactory.createLineBorder(Color.BLACK, 2);
        statsPanel.setBorder(BorderFactory.createTitledBorder(lineBorder, "Total Statistics"));

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String distanceText = distanceField.getText();
                String moneyText = moneyField.getText();

                if (distanceText.isEmpty() || moneyText.isEmpty()) {
                    JOptionPane.showMessageDialog(panel, "Please enter both distance and earned money values.");
                    return;
                }

                try {
                    double distance = Double.parseDouble(distanceText);
                    double money = Double.parseDouble(moneyText);

                    // temp currency and distance for now
                    String currencyType = "Kč";
                    String distanceType = "Km";

                    String record = "Distance: " + distance + distanceType + " , Money: " + money + currencyType;
                    records.add(record);
                    recordArea.append(record + "\n");

                    totalDistance += distance;
                    totalMoney += money;
                    totalDrives++;

                    totalDistanceLabel.setText(totalDistance + " " + distanceType);
                    totalMoneyLabel.setText(totalMoney + " " + currencyType);
                    totalDrivesLabel.setText(totalDrives + "");
                    distanceField.setText("");
                    moneyField.setText("");

                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(panel, "Please enter valid numbers.");
                }
            }
        });

        panel.add(statsPanel, BorderLayout.CENTER);
        panel.add(inputPanel, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.SOUTH);

        return panel;
    }
}
