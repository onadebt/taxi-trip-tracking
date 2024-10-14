package cz.muni.fi.pv168.project.ui.tabs;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class HomePage {

    // store records (distance and money)
    private static java.util.List<String> records = new ArrayList<>();

    public static JPanel createHomePagePanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());

        JLabel distanceLabel = new JLabel("Distance traveled (km):");
        JTextField distanceField = new JTextField(10); // 10 columns wide

        JLabel moneyLabel = new JLabel("Money earned (Kč):");
        JTextField moneyField = new JTextField(10);

        JButton addButton = new JButton("Add");

        // temp area to display added records
        JTextArea recordArea = new JTextArea(10, 30);
        recordArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(recordArea);

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

                    // temp currency for now
                    String currencyType = "Kč";

                    String record = "Distance: " + distance + " km, Money: " + money + currencyType;
                    records.add(record);

                    recordArea.append(record + "\n");
                    distanceField.setText("");
                    moneyField.setText("");

                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(panel, "Please enter valid numbers.");
                }
            }
        });

        panel.add(distanceLabel);
        panel.add(distanceField);
        panel.add(moneyLabel);
        panel.add(moneyField);
        panel.add(addButton);
        panel.add(scrollPane);

        return panel;
    }
}
