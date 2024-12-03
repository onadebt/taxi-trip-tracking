package cz.muni.fi.pv168.project.ui.tabs;

import cz.muni.fi.pv168.project.model.Category;
import cz.muni.fi.pv168.project.model.Currency;
import cz.muni.fi.pv168.project.model.Ride;
import cz.muni.fi.pv168.project.service.crud.CategoryCrudService;
import cz.muni.fi.pv168.project.service.crud.CrudService;
import cz.muni.fi.pv168.project.service.interfaces.ICategoryService;
import cz.muni.fi.pv168.project.service.interfaces.ICurrencyService;
import cz.muni.fi.pv168.project.service.interfaces.IRideService;
import cz.muni.fi.pv168.project.ui.action.NewRideAction;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class HomePage extends JPanel {
    private final IRideService rideService;
    private final ListModel<Currency> currencyListModel;
    private final ListModel<Category> categoryListModel;

    public HomePage(IRideService rideService, ListModel<Currency> currencyListModel, ListModel<Category> categoryListModel) {
        super(new BorderLayout());
        this.rideService = rideService;
        this.currencyListModel = currencyListModel;
        this.categoryListModel = categoryListModel;

        JPanel filterPanel = createFilterPanel();
        this.add(filterPanel, BorderLayout.NORTH);

        List<Ride> rideHistory = rideService.getAll();

        JPanel statsPanel = createStatsPanel(rideHistory);
        this.add(statsPanel, BorderLayout.CENTER);

        JPanel snapshotPanel = createLastRidesPanel(rideHistory);
        this.add(snapshotPanel, BorderLayout.SOUTH);

        JButton addButton = new JButton("Add Ride");
        addButton.addActionListener(new NewRideAction(this, rideService, currencyListModel, categoryListModel));
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

        this.add(centerPanel, BorderLayout.CENTER);
    }

    private JPanel createFilterPanel() {
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

    private JPanel createStatsPanel(List<Ride> rideHistory) {
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

    private JPanel createLastRidesPanel(List<Ride> rideHistory) {
        JPanel snapshotPanel = new JPanel();
        snapshotPanel.setLayout(new BoxLayout(snapshotPanel, BoxLayout.Y_AXIS));

        int start = Math.max(rideHistory.size() - 3, 0);
        List<Ride> lastRides = rideHistory.subList(start, rideHistory.size());

        String[] labels = {"1st Last Ride", "2nd Last Ride", "3rd Last Ride"};
        for (int i = 0; i < lastRides.size(); i++) {
            Ride ride = lastRides.get(i);
            JPanel ridePanel = createSingleRidePanel(ride, labels[i]);
            snapshotPanel.add(ridePanel);
            snapshotPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        }

        return snapshotPanel;
    }

    private JPanel createSingleRidePanel(Ride ride, String title) {
        JPanel ridePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        ridePanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK), title));
        ridePanel.setBorder(BorderFactory.createCompoundBorder(
                ridePanel.getBorder(),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));

        JLabel rideLabel = new JLabel(String.format("%s, Distance: %.2f km, Amount: %.2f %s",
                ride.getCategory().getName(), ride.getDistance(), ride.getAmountCurrency(), ride.getCurrency().getCode()));
        rideLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        ridePanel.add(rideLabel);

        return ridePanel;
    }

    private void addStatLabel(JPanel panel, String label) {
        JLabel statLabel = new JLabel(label);
        statLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        panel.add(statLabel);
    }

    private void addStatValue(JPanel panel, String value) {
        JLabel statValue = new JLabel(value);
        statValue.setFont(new Font("Arial", Font.BOLD, 24));
        panel.add(statValue);
    }

    private double calculateTotalAmount(List<Ride> rideHistory) {
        double totalAmount = 0;

        for (Ride ride : rideHistory) {
            totalAmount += ride.getAmountCurrency() * ride.getCurrency().getExchangeRate();
        }

        return totalAmount;
    }

    private double calculateTotalDistance(List<Ride> rideHistory) {
        double totalDistance = 0;
        for (Ride ride : rideHistory) {
            totalDistance += ride.getDistance();
        }
        return totalDistance;
    }
}
