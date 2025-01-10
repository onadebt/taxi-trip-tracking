// HomePage.java
package cz.muni.fi.pv168.project.ui.tabs;

import cz.muni.fi.pv168.project.model.Category;
import cz.muni.fi.pv168.project.model.Currency;
import cz.muni.fi.pv168.project.model.Ride;
import cz.muni.fi.pv168.project.service.RideFilterStatisticsService;
import cz.muni.fi.pv168.project.service.interfaces.IRideService;
import cz.muni.fi.pv168.project.service.interfaces.ISettingsService;
import cz.muni.fi.pv168.project.ui.action.NewRideAction;
import cz.muni.fi.pv168.project.ui.model.RideTableModel;

import javax.swing.*;
import java.awt.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public class HomePage extends JPanel {
    private final IRideService rideService;
    private final ListModel<Currency> currencyListModel;
    private final ListModel<Category> categoryListModel;
    private final ISettingsService settingsService;
    JPanel centralPanel;
    private JPanel statsPanel;
    private JPanel snapshotPanel;
    private GridBagConstraints gbc;
    private RideFilterStatisticsService rideFilterStatisticsService;

    public HomePage(RideTableModel rideTableModel, IRideService rideService, ListModel<Currency> currencyListModel, ListModel<Category> categoryListModel, ISettingsService settingsService) {
        super(new BorderLayout());
        this.rideService = rideService;
        this.currencyListModel = currencyListModel;
        this.categoryListModel = categoryListModel;
        this.settingsService = settingsService;
        this.rideFilterStatisticsService = new RideFilterStatisticsService();

        JPanel filterPanel = createFilterPanel();
        this.add(filterPanel, BorderLayout.NORTH);

        List<Ride> rideHistory = rideService.findAll();

        statsPanel = createStatsPanel(rideHistory);
        snapshotPanel = createLastRidesPanel(rideHistory);

        JButton addButton = new JButton("Add Ride");
        addButton.addActionListener(e -> {
            new NewRideAction(this, rideTableModel, rideService, currencyListModel, categoryListModel).actionPerformed(e);
            refreshStatsPanel();
        });
        addButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        centralPanel = new JPanel(new GridBagLayout());
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(10, 0, 10, 0);

        centralPanel.add(filterPanel, gbc);

        gbc.gridy = 1;
        centralPanel.add(statsPanel, gbc);

        gbc.gridy = 2;
        centralPanel.add(addButton, gbc);

        gbc.gridy = 3;
        centralPanel.add(snapshotPanel, gbc);

        this.add(centralPanel, BorderLayout.CENTER);
    }


    public void refreshHomePage() {
        refreshStatsPanel();
        refreshLastRidesPanel();
        refreshFilterPanel();
    }

    public void refreshFilterPanel() {
        Component filterPanel = centralPanel.getComponent(0);

        centralPanel.remove(filterPanel);
        filterPanel = createFilterPanel();

        gbc.gridy = 0;
        centralPanel.add(filterPanel, gbc);
        centralPanel.revalidate();
        centralPanel.repaint();
    }

    public void refreshStatsPanel() {
        List<Ride> rideHistory = rideService.findAll();
        centralPanel.remove(statsPanel);
        statsPanel = createStatsPanel(rideHistory);
        gbc.gridy = 1;
        centralPanel.add(statsPanel, gbc);
        centralPanel.revalidate();
        centralPanel.repaint();
    }

    public void refreshLastRidesPanel() {
        List<Ride> rideHistory = rideService.findAll();
        centralPanel.remove(snapshotPanel);
        snapshotPanel = createLastRidesPanel(rideHistory);
        gbc.gridy = 3;
        centralPanel.add(snapshotPanel, gbc);
        centralPanel.revalidate();
        centralPanel.repaint();
    }

    private JPanel createFilterPanel() {
        JPanel filterPanel = new JPanel();
        filterPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

        JLabel filterLabel = new JLabel("Filter by this: ");
        JRadioButton dayButton = new JRadioButton("Day");
        JRadioButton weekButton = new JRadioButton("Week");
        JRadioButton monthButton = new JRadioButton("Month");
        JRadioButton totalButton = new JRadioButton("Total");

        ButtonGroup group = new ButtonGroup();
        group.add(dayButton);
        group.add(weekButton);
        group.add(monthButton);
        group.add(totalButton);

        filterPanel.add(filterLabel);
        filterPanel.add(dayButton);
        filterPanel.add(weekButton);
        filterPanel.add(monthButton);
        filterPanel.add(totalButton);

        dayButton.addActionListener(e -> filterRidesByPeriod("day"));
        weekButton.addActionListener(e -> filterRidesByPeriod("week"));
        monthButton.addActionListener(e -> filterRidesByPeriod("month"));
        totalButton.addActionListener(e -> updateStatsPanel(rideService.findAll()));

        totalButton.setSelected(true);
        return filterPanel;
    }

    private JPanel createStatsPanel(List<Ride> rideHistory) {
        JPanel statsPanel = new JPanel(new GridLayout(2, 3, 10, 10));

        BigDecimal totalAmount = calculateTotalAmount(rideHistory);
        double totalDistance = calculateTotalDistance(rideHistory);
        int totalDrives = rideHistory.size();

        addStatLabel(statsPanel, "Total Distance:");
        addStatLabel(statsPanel, "Total Money Earned:");
        addStatLabel(statsPanel, "Total Rides:");

        String distanceUnit = settingsService.getDefaultDistance().getShortcut();
        addStatValue(statsPanel, String.format("%.2f %s", totalDistance, distanceUnit));
        addStatValue(statsPanel, String.format("%.2f EUR", totalAmount));
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

        Optional<String> categoryName = Optional.ofNullable(ride.getCategory()).map(Category::getName).orElse("Unknown").describeConstable();
        JLabel rideLabel = new JLabel(String.format("%s, Distance: %.2f %s, Amount: %.2f %s",
                categoryName.get(), ride.getDistance(), settingsService.getDefaultDistance().getShortcut(), ride.getAmountCurrency(), ride.getCurrency().getCode()));
        rideLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        ridePanel.add(rideLabel);

        return ridePanel;
    }

    private void filterRidesByPeriod(String period) {
        List<Ride> rideHistory = rideService.findAll();
        List<Ride> filteredRides = switch (period) {
            case "day" -> rideFilterStatisticsService.filterRidesByDay(rideHistory);
            case "week" -> rideFilterStatisticsService.filterRidesByWeek(rideHistory);
            case "month" -> rideFilterStatisticsService.filterRidesByMonth(rideHistory);
            default -> rideHistory;
        };

        updateStatsPanel(filteredRides);
    }

    private void updateStatsPanel(List<Ride> filteredRides) {
        centralPanel.remove(statsPanel);
        statsPanel = createStatsPanel(filteredRides);
        gbc.gridy = 1;
        centralPanel.add(statsPanel, gbc);
        centralPanel.revalidate();
        centralPanel.repaint();
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

    private BigDecimal calculateTotalAmount(List<Ride> rideHistory) {
        return rideHistory.stream()
                .map(ride -> ride.getAmountCurrency().multiply(ride.getCurrency().getExchangeRate()))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private double calculateTotalDistance(List<Ride> rideHistory) {
        double totalDistance = 0;
        for (Ride ride : rideHistory) {
            totalDistance += ride.getDistance();
        }
        return totalDistance;
    }
}