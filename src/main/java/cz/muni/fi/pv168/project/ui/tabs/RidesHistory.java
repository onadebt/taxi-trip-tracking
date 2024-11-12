package cz.muni.fi.pv168.project.ui.tabs;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableRowSorter;

import cz.muni.fi.pv168.project.model.Category;
import cz.muni.fi.pv168.project.model.Currency;
import cz.muni.fi.pv168.project.model.Ride;
import cz.muni.fi.pv168.project.model.enums.CurrencyCode;
import cz.muni.fi.pv168.project.model.enums.DistanceUnit;
import cz.muni.fi.pv168.project.model.enums.TripType;
import cz.muni.fi.pv168.project.providers.DIProvider;
import cz.muni.fi.pv168.project.ui.action.JsonExportAction;
import cz.muni.fi.pv168.project.ui.action.JsonImportAction;
//import cz.muni.fi.pv168.project.ui.action.NewRideAction;
import cz.muni.fi.pv168.project.ui.action.NewRideAction;
import cz.muni.fi.pv168.project.ui.model.CategoryListModel;

import java.awt.*;
import java.awt.event.*;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.List;

import com.toedter.calendar.JDateChooser;
import cz.muni.fi.pv168.project.ui.resources.Icons;

import java.util.Date;
import java.util.Locale;

public class RidesHistory extends JPanel {

    public List<Ride> rideHistory = getSampleRideHistory();
    private final DIProvider diProvider;

    private final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)
            .withLocale(Locale.forLanguageTag("cs-CZ"))
            .withZone(ZoneId.systemDefault());


    private RidesHistory(DIProvider diProvider) {
        super(new BorderLayout());
        this.diProvider = diProvider;


        JLabel label = new JLabel("History of taxi rides:");
        this.add(label, BorderLayout.NORTH);

        JTable rideHistoryTable = createRidesTable();
        JScrollPane scrollPane = new JScrollPane(rideHistoryTable);

        JToolBar toolBar = createToolBar(rideHistoryTable, (DefaultTableModel) rideHistoryTable.getModel());
        this.add(toolBar, BorderLayout.NORTH);
        this.add(scrollPane, BorderLayout.CENTER);

        JPanel filterPanel = createFilterPanel(rideHistoryTable, (DefaultTableModel) rideHistoryTable.getModel());
        this.add(filterPanel, BorderLayout.SOUTH);
    }

    public static JPanel createRidesHistoryPanel(DIProvider diProvider) {
        return new RidesHistory(diProvider);
    }

    private JToolBar createToolBar(JTable table, DefaultTableModel tableModel) {
        JToolBar toolBar = new JToolBar();

        JButton addButton = new JButton("Add New Ride");
        addButton.addActionListener(new NewRideAction(this, diProvider.getRideService(), diProvider.getCurrencyService(), diProvider.getCategoryService()));
        toolBar.add(addButton);

        JButton editAmountButton = new JButton("Edit Amount");
        editAmountButton.addActionListener(e -> editAmount(table, tableModel));
        toolBar.add(editAmountButton);

        JButton editCurrencyButton = new JButton("Edit Currency");
        editCurrencyButton.addActionListener(e -> editCurrency(table, tableModel));
        toolBar.add(editCurrencyButton);

        JButton editDistanceButton = new JButton("Edit Distance");
        editDistanceButton.addActionListener(e -> editDistance(table, tableModel));
        toolBar.add(editDistanceButton);

        JButton editCategoryButton = new JButton("Edit Category");
        editCategoryButton.addActionListener(e -> editCategory(table, tableModel));
        toolBar.add(editCategoryButton);

        JButton editPersonalRideButton = new JButton("Edit Trip Type");
        editPersonalRideButton.addActionListener(e -> editTripType(table, tableModel));
        toolBar.add(editPersonalRideButton);

        JButton deleteRowsButton = new JButton("Delete Selected Rows");
        deleteRowsButton.addActionListener(e -> deleteSelectedRows(table, tableModel));
        toolBar.add(deleteRowsButton);

        JButton importButton = new JButton("Import");
        importButton.addActionListener(new JsonImportAction(this, diProvider.getJsonImportService()));
        JButton exportButton = new JButton("Export");
        exportButton.addActionListener(new JsonExportAction(this, diProvider.getJsonExportService(), diProvider.getRideService()));
        toolBar.add(importButton);
        toolBar.add(exportButton);

        return toolBar;
    }

    private JTable createRidesTable() {
        String[] columnNames = {"Amount", "Currency", "Distance", "Category", "Trip Type", "Number of People", "Date"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 0 || column == 1 || column == 2 || column == 3 || column == 4 || column == 5 || column == 6;
            }
        };

        for (Ride ride : rideHistory) {
            Object[] rowData = {
                    ride.getAmountCurrency(),
                    ride.getCurrency().getCode(),
                    ride.getDistance(),
                    ride.getCategory().getName(),
                    ride.getTripType().name(),
                    ride.getNumberOfPassengers(),
                    DATE_TIME_FORMATTER.format(ride.getCreatedAt())

            };
            tableModel.addRow(rowData);
        }

        JTable table = new JTable(tableModel);
        table.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

        TableColumn currencyColumn = table.getColumnModel().getColumn(1);
        JComboBox<CurrencyCode> currencyComboBox = new JComboBox<>(CurrencyCode.values());
        currencyColumn.setCellEditor(new DefaultCellEditor(currencyComboBox));

        TableColumn categoryColumn = table.getColumnModel().getColumn(3);
        JComboBox<String> categoryComboBox = createCategoryComboBox();
        categoryColumn.setCellEditor(new DefaultCellEditor(categoryComboBox));

        TableColumn tripTypeColumn = table.getColumnModel().getColumn(4);
        JComboBox<TripType> tripTypeComboBox = new JComboBox<>(TripType.values());
        tripTypeColumn.setCellEditor(new DefaultCellEditor(tripTypeComboBox));

        // Add mouse listener to detect right-clicks
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (e.isPopupTrigger()) {
                    showPopupMenu(e, table, tableModel);
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (e.isPopupTrigger()) {
                    showPopupMenu(e, table, tableModel);
                }
            }
        });

        return table;
    }

    private void showPopupMenu(MouseEvent e, JTable table, DefaultTableModel tableModel) {
        JPopupMenu popupMenu = new JPopupMenu();

        JMenuItem editAmountItem = new JMenuItem("Edit Amount");
        editAmountItem.addActionListener(event -> editAmount(table, tableModel));
        popupMenu.add(editAmountItem);

        JMenuItem editCurrencyItem = new JMenuItem("Edit Currency");
        editCurrencyItem.addActionListener(event -> editCurrency(table, tableModel));
        popupMenu.add(editCurrencyItem);

        JMenuItem editDistanceItem = new JMenuItem("Edit Distance");
        editDistanceItem.addActionListener(event -> editDistance(table, tableModel));
        popupMenu.add(editDistanceItem);

        JMenuItem editCategoryItem = new JMenuItem("Edit Category");
        editCategoryItem.addActionListener(event -> editCategory(table, tableModel));
        popupMenu.add(editCategoryItem);

        JMenuItem editPersonalRideItem = new JMenuItem("Edit Personal Ride");
        editPersonalRideItem.addActionListener(event -> editTripType(table, tableModel));
        popupMenu.add(editPersonalRideItem);

        popupMenu.addSeparator();

        JMenuItem deleteRowsItem = new JMenuItem("Delete Selected Rows");
        deleteRowsItem.addActionListener(event -> deleteSelectedRows(table, tableModel));
        popupMenu.add(deleteRowsItem);

        popupMenu.show(e.getComponent(), e.getX(), e.getY());
    }

    private void editAmount(JTable table, DefaultTableModel tableModel) {
        for (int row : table.getSelectedRows()) {
            Object currentAmount = tableModel.getValueAt(row, 0);
            String newAmountStr = JOptionPane.showInputDialog(table, "Enter new amount:", currentAmount);

            try {
                double newAmount = Double.parseDouble(newAmountStr);
                tableModel.setValueAt(newAmount, row, 0);
                rideHistory.get(row).setAmountCurrency(newAmount);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(table, "Invalid amount entered.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void editCurrency(JTable table, DefaultTableModel tableModel) {
        for (int row : table.getSelectedRows()) {
            Object currentCurrency = tableModel.getValueAt(row, 1);
            JComboBox<CurrencyCode> currencyComboBox = new JComboBox<>(CurrencyCode.values());
            currencyComboBox.setSelectedItem(currentCurrency);
            int option = JOptionPane.showConfirmDialog(table, currencyComboBox, "Choose new currency", JOptionPane.OK_CANCEL_OPTION);

            if (option == JOptionPane.OK_OPTION) {
                tableModel.setValueAt(currencyComboBox.getSelectedItem(), row, 1);
            }
        }
    }

    private void editDistance(JTable table, DefaultTableModel tableModel) {
        for (int row : table.getSelectedRows()) {
            Object currentDistance = tableModel.getValueAt(row, 2);
            String newDistanceStr = JOptionPane.showInputDialog(table, "Enter new distance:", currentDistance);

            try {
                double newDistance = Double.parseDouble(newDistanceStr);
                tableModel.setValueAt(newDistance, row, 2);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(table, "Invalid distance entered.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void editCategory(JTable table, DefaultTableModel tableModel) {
        for (int row : table.getSelectedRows()) {
            Object currentCategory = tableModel.getValueAt(row, 3);
            JComboBox<String> categoryComboBox = new JComboBox<>(new String[]{"Premium", "Comfort", "Standard"});
            categoryComboBox.setSelectedItem(currentCategory);
            int option = JOptionPane.showConfirmDialog(table, categoryComboBox, "Choose new category", JOptionPane.OK_CANCEL_OPTION);

            if (option == JOptionPane.OK_OPTION) {
                tableModel.setValueAt(categoryComboBox.getSelectedItem(), row, 3);
            }
        }
    }

    private void editTripType(JTable table, DefaultTableModel tableModel) {
        for (int row : table.getSelectedRows()) {
            Object currentTripType = tableModel.getValueAt(row, 4);
            JComboBox<String> tripTypeComboBox = new JComboBox<>(new String[]{TripType.Paid.name(), TripType.Personal.name()});
            tripTypeComboBox.setSelectedItem(currentTripType);
            int option = JOptionPane.showConfirmDialog(table, tripTypeComboBox, "Trip Type", JOptionPane.OK_CANCEL_OPTION);

            if (option == JOptionPane.OK_OPTION) {
                tableModel.setValueAt(tripTypeComboBox.getSelectedItem(), row, 4);
            }
        }
    }

    private void deleteSelectedRows(JTable table, DefaultTableModel tableModel) {
        int[] selectedRows = table.getSelectedRows();
        if (selectedRows.length > 0) {
            int confirm = JOptionPane.showConfirmDialog(table, "Are you sure you want to delete the selected rows?", "Confirm Deletion", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                for (int i = selectedRows.length - 1; i >= 0; i--) {
                    tableModel.removeRow(selectedRows[i]);
                }
            }
        } else {
            JOptionPane.showMessageDialog(table, "No rows selected for deletion.", "Warning", JOptionPane.WARNING_MESSAGE);
        }
    }

    // for test
    public static List<Ride> getSampleRideHistory() {
        List<Ride> rides = new ArrayList<>();
        Category premium = new Category("Premium", Icons.getByName("convertible-car.png"));
        Category comfort = new Category("Sport", Icons.getByName("sport-car.png"));
        Category standard = new Category("Standard", Icons.getByName("normal-car.png"));

        Currency usd = new Currency("US Dollar", CurrencyCode.USD, 22D);
        Currency eur = new Currency("Euro", CurrencyCode.EUR, 25D);
        Currency gbp = new Currency("British Pound", CurrencyCode.GBP, 29D);
        Currency jpy = new Currency("Japanese Yen", CurrencyCode.JPY, 0.2D);
        Currency czk = new Currency("Czech Crown", CurrencyCode.CZK, 1D);

        rides.add(new Ride(null,100D, czk, 10D, DistanceUnit.Kilometer, premium, TripType.Paid, 2, Instant.now()));
        rides.add(new Ride(null, 100D, czk, 10D, DistanceUnit.Kilometer, premium, TripType.Paid, 2, Instant.now()));
        rides.add(new Ride(null, 50D, usd, 5D, DistanceUnit.Kilometer, comfort, TripType.Personal, 1, Instant.now()));
        rides.add(new Ride(null, 75D, eur, 7.5D, DistanceUnit.Kilometer, standard, TripType.Paid, 3, Instant.now()));
        rides.add(new Ride(null, 120D, gbp, 12D, DistanceUnit.Kilometer, premium, TripType.Paid, 4, Instant.now()));
        rides.add(new Ride(null, 30D, jpy, 3D, DistanceUnit.Kilometer, comfort, TripType.Personal, 1, Instant.now()));

        return rides;
    }

    private JPanel createFilterPanel(JTable table, DefaultTableModel tableModel) {
        JPanel filterPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Amount filter
        gbc.gridx = 0;
        gbc.gridy = 0;
        filterPanel.add(new JLabel("Amount (Min)"), gbc);
        JTextField minAmountField = new JTextField();
        gbc.gridx = 1;
        filterPanel.add(minAmountField, gbc);

        gbc.gridx = 2;
        filterPanel.add(new JLabel("Amount (Max)"), gbc);
        JTextField maxAmountField = new JTextField();
        gbc.gridx = 3;
        filterPanel.add(maxAmountField, gbc);

        // Currency filter
        gbc.gridx = 4;
        filterPanel.add(new JLabel("Currency"), gbc);
        JComboBox<CurrencyCode> currencyField = new JComboBox<>(CurrencyCode.values());
        gbc.gridx = 5;
        filterPanel.add(currencyField, gbc);

        // Distance filter
        gbc.gridy = 1;
        gbc.gridx = 0;
        filterPanel.add(new JLabel("Distance (Min)"), gbc);
        JTextField minDistanceField = new JTextField();
        gbc.gridx = 1;
        filterPanel.add(minDistanceField, gbc);

        gbc.gridx = 2;
        filterPanel.add(new JLabel("Distance (Max)"), gbc);
        JTextField maxDistanceField = new JTextField();
        gbc.gridx = 3;
        filterPanel.add(maxDistanceField, gbc);


        // Category filter
        gbc.gridy = 2;
        gbc.gridx = 0;
        filterPanel.add(new JLabel("Category"), gbc);
        JComboBox<String> categoryField = createCategoryComboBox();
        gbc.gridx = 1;
        filterPanel.add(categoryField, gbc);

        // Personal ride filter
        gbc.gridx = 2;
        filterPanel.add(new JLabel("Personal Ride"), gbc);
        JComboBox<TripType> tripTypeJComboBox = new JComboBox<>(TripType.values());
        gbc.gridx = 3;
        filterPanel.add(tripTypeJComboBox, gbc);

        // People filter
        gbc.gridy = 3;
        gbc.gridx = 0;
        filterPanel.add(new JLabel("People (Min)"), gbc);
        JTextField minPeopleField = new JTextField();
        gbc.gridx = 1;
        filterPanel.add(minPeopleField, gbc);

        gbc.gridx = 2;
        filterPanel.add(new JLabel("People (Max)"), gbc);
        JTextField maxPeopleField = new JTextField();
        gbc.gridx = 3;
        filterPanel.add(maxPeopleField, gbc);

        // Date filter
        gbc.gridy = 4;
        gbc.gridx = 0;
        filterPanel.add(new JLabel("Start Date"), gbc);
        JDateChooser startDateChooser = new JDateChooser();
        startDateChooser.setLocale(Locale.ENGLISH);
        gbc.gridx = 1;
        filterPanel.add(startDateChooser, gbc);

        gbc.gridx = 2;
        filterPanel.add(new JLabel("End Date"), gbc);
        JDateChooser endDateChooser = new JDateChooser();
        endDateChooser.setLocale(Locale.ENGLISH);
        gbc.gridx = 3;
        filterPanel.add(endDateChooser, gbc);

        // Filter button
        gbc.gridy = 6;
        gbc.gridx = 1;
        gbc.gridwidth = 2;
        JButton filterButton = new JButton("Filter");
        filterButton.addActionListener(e -> applyFilters(
                table, tableModel, minAmountField, maxAmountField, currencyField,
                minDistanceField, maxDistanceField, categoryField, tripTypeJComboBox,
                minPeopleField, maxPeopleField, startDateChooser, endDateChooser));
        filterPanel.add(filterButton, gbc);

        return filterPanel;
    }

    private void applyFilters(JTable table, DefaultTableModel tableModel,
                              JTextField minAmountField, JTextField maxAmountField,
                              JComboBox<CurrencyCode> currencyField, JTextField minDistanceField,
                              JTextField maxDistanceField, JComboBox<String> categoryField,
                              JComboBox<TripType> tripTypeField, JTextField minPeopleField,
                              JTextField maxPeopleField, JDateChooser startDateChooser, JDateChooser endDateChooser) {

        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(tableModel);
        table.setRowSorter(sorter);

        List<RowFilter<Object, Object>> filters = new ArrayList<>();

        Integer minAmount = parseIntField(minAmountField.getText());
        Integer maxAmount = parseIntField(maxAmountField.getText());
        if (minAmount != null || maxAmount != null) {
            filters.add(RowFilter.numberFilter(RowFilter.ComparisonType.AFTER, minAmount != null ? minAmount - 1 : Integer.MIN_VALUE, 0));
            filters.add(RowFilter.numberFilter(RowFilter.ComparisonType.BEFORE, maxAmount != null ? maxAmount + 1 : Integer.MAX_VALUE, 0));
        }

        String selectedCurrency = (String) currencyField.getSelectedItem();
        if (!"Any".equals(selectedCurrency)) {
            filters.add(RowFilter.regexFilter(selectedCurrency, 1));
        }

        Integer minDistance = parseIntField(minDistanceField.getText());
        Integer maxDistance = parseIntField(maxDistanceField.getText());
        if (minDistance != null || maxDistance != null) {
            filters.add(RowFilter.numberFilter(RowFilter.ComparisonType.AFTER, minDistance != null ? minDistance - 1 : Integer.MIN_VALUE, 2));
            filters.add(RowFilter.numberFilter(RowFilter.ComparisonType.BEFORE, maxDistance != null ? maxDistance + 1 : Integer.MAX_VALUE, 2));
        }

        String selectedCategory = (String) categoryField.getSelectedItem();
        if (!"Any".equals(selectedCategory)) {
            if ("No Category".equals(selectedCategory)) {
                filters.add(RowFilter.regexFilter("^$", 3));
            } else {
                filters.add(RowFilter.regexFilter(selectedCategory, 3));
            }
        }

        String selectedRide = (String) tripTypeField.getSelectedItem();
        if (!"Any".equals(selectedRide)) {
            filters.add(RowFilter.regexFilter(selectedRide, 4));
        }

        Integer minPeople = parseIntField(minPeopleField.getText());
        Integer maxPeople = parseIntField(maxPeopleField.getText());
        if (minPeople != null || maxPeople != null) {
            filters.add(RowFilter.numberFilter(RowFilter.ComparisonType.AFTER, minPeople != null ? minPeople - 1 : Integer.MIN_VALUE, 5));
            filters.add(RowFilter.numberFilter(RowFilter.ComparisonType.BEFORE, maxPeople != null ? maxPeople + 1 : Integer.MAX_VALUE, 5));
        }

        Date startDate = startDateChooser.getDate();
        Date endDate = endDateChooser.getDate();
        if (startDate != null || endDate != null) {
            Timestamp startTimestamp = startDate != null ? new Timestamp(startDate.getTime()) : new Timestamp(Long.MIN_VALUE);
            Timestamp endTimestamp = endDate != null ? new Timestamp(endDate.getTime()) : new Timestamp(Long.MAX_VALUE);

            filters.add(RowFilter.dateFilter(RowFilter.ComparisonType.AFTER, startTimestamp, 6));
            filters.add(RowFilter.dateFilter(RowFilter.ComparisonType.BEFORE, endTimestamp, 6));
        }

        RowFilter<Object, Object> combinedFilter = RowFilter.andFilter(filters);
        sorter.setRowFilter(combinedFilter);
    }

    //parse Integers
    private Integer parseIntField(String text) {
        try {
            return Integer.parseInt(text);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private JComboBox<String> createCategoryComboBox() {
        CategoryListModel categoryListModel = new CategoryListModel(diProvider.getCategoryService());
        List<String> categoriesNames = new ArrayList<>();

        for (int i = 0; i < categoryListModel.categories.size(); i++) {
            categoriesNames.add(categoryListModel.categories.get(i).getName());
        }
        categoriesNames.add("No Category");
        String[] categoriesNamesArray = categoriesNames.toArray(new String[0]);

        return new JComboBox<>(categoriesNamesArray);
    }
}