package cz.muni.fi.pv168.project.ui.tabs;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableRowSorter;

import cz.muni.fi.pv168.project.service.IRideService;
import cz.muni.fi.pv168.project.service.RideService;
import cz.muni.fi.pv168.project.ui.Currencies;
import cz.muni.fi.pv168.project.ui.action.NewRideAction;
import cz.muni.fi.pv168.project.ui.model.CategoryListModel;
import cz.muni.fi.pv168.project.ui.model.CurrencyListModel;
import cz.muni.fi.pv168.project.ui.model.RideModel;

import java.awt.*;
import java.awt.event.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.toedter.calendar.JDateChooser;
import java.util.Date;
import java.util.Locale;

public class RidesHistory {

    public static List<RideModel> rideHistory = getSampleRideHistory();

    public static JPanel createRidesHistoryPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        JLabel label = new JLabel("History of taxi rides:");
        panel.add(label, BorderLayout.NORTH);

        JTable rideHistoryTable = createRidesTable();
        JScrollPane scrollPane = new JScrollPane(rideHistoryTable);

        JToolBar toolBar = createToolBar(rideHistoryTable, (DefaultTableModel) rideHistoryTable.getModel(), panel);
        panel.add(toolBar, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);

        JPanel filterPanel = createFilterPanel(rideHistoryTable, (DefaultTableModel) rideHistoryTable.getModel());
        panel.add(filterPanel, BorderLayout.SOUTH);

        return panel;
    }

    private static JToolBar createToolBar(JTable table, DefaultTableModel tableModel, JPanel parentPanel) {
        JToolBar toolBar = new JToolBar();

        JButton addButton = new JButton("Add New Ride");
        addButton.addActionListener(new NewRideAction(parentPanel, new CurrencyListModel(new ArrayList<>()), new CategoryListModel(new ArrayList<>())));
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

        JButton editPersonalRideButton = new JButton("Edit Personal Ride");
        editPersonalRideButton.addActionListener(e -> editPersonalRide(table, tableModel));
        toolBar.add(editPersonalRideButton);

        JButton deleteRowsButton = new JButton("Delete Selected Rows");
        deleteRowsButton.addActionListener(e -> deleteSelectedRows(table, tableModel));
        toolBar.add(deleteRowsButton);

        return toolBar;
    }

    private static JTable createRidesTable() {
        String[] columnNames = {"Amount", "Currency", "Distance", "Category", "Personal Ride", "Number of People", "Date"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 0 || column == 1 || column == 2 || column == 3 || column == 4 || column == 5 || column == 6;
            }
        };

        for (RideModel ride : rideHistory) {
            Object[] rowData = {
                    ride.getAmountCurrency(),
                    ride.getCurrency(),
                    ride.getDistance(),
                    ride.getCategoryName(),
                    ride.getPersonalRide() ? "YES" : "NO",
                    ride.getNumberOfPeople(),
                    ride.getCreatedDate()

            };
            tableModel.addRow(rowData);
        }

        JTable table = new JTable(tableModel);
        table.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

        TableColumn currencyColumn = table.getColumnModel().getColumn(1);
        JComboBox<String> currencyComboBox = new JComboBox<>(new String[]{"CZK", "USD", "EUR", "GBP", "JPY"});
        currencyColumn.setCellEditor(new DefaultCellEditor(currencyComboBox));

        CategoryListModel categoryListModel = RidesCategories.getCategoryListModel();
        List<String> categoriesNames = new ArrayList<>();

        for (int i = 0; i < categoryListModel.categories.size(); i++) {
            categoriesNames.add(categoryListModel.categories.get(i).getName());
        }
        String[] categoriesNamesArray = categoriesNames.toArray(new String[0]);

        TableColumn categoryColumn = table.getColumnModel().getColumn(3);
        JComboBox<String> categoryComboBox = new JComboBox<>(categoriesNamesArray);
        categoryColumn.setCellEditor(new DefaultCellEditor(categoryComboBox));

        TableColumn personalRideColumn = table.getColumnModel().getColumn(4);
        JComboBox<String> personalRideComboBox = new JComboBox<>(new String[]{"YES", "NO"});
        personalRideColumn.setCellEditor(new DefaultCellEditor(personalRideComboBox));

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

    // Show popup menu to edit multiple fields (right-click) and delete rows
    private static void showPopupMenu(MouseEvent e, JTable table, DefaultTableModel tableModel) {
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
        editPersonalRideItem.addActionListener(event -> editPersonalRide(table, tableModel));
        popupMenu.add(editPersonalRideItem);

        popupMenu.addSeparator();

        JMenuItem deleteRowsItem = new JMenuItem("Delete Selected Rows");
        deleteRowsItem.addActionListener(event -> deleteSelectedRows(table, tableModel));
        popupMenu.add(deleteRowsItem);

        popupMenu.show(e.getComponent(), e.getX(), e.getY());
    }

    private static void editAmount(JTable table, DefaultTableModel tableModel) {
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

    private static void editCurrency(JTable table, DefaultTableModel tableModel) {
        for (int row : table.getSelectedRows()) {
            Object currentCurrency = tableModel.getValueAt(row, 1);
            JComboBox<String> currencyComboBox = new JComboBox<>(new String[]{"CZK", "EUR", "USD", "GBP", "JPY"});
            currencyComboBox.setSelectedItem(currentCurrency);
            int option = JOptionPane.showConfirmDialog(table, currencyComboBox, "Choose new currency", JOptionPane.OK_CANCEL_OPTION);

            if (option == JOptionPane.OK_OPTION) {
                tableModel.setValueAt(currencyComboBox.getSelectedItem(), row, 1);
            }
        }
    }

    private static void editDistance(JTable table, DefaultTableModel tableModel) {
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

    private static void editCategory(JTable table, DefaultTableModel tableModel) {
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

    private static void editPersonalRide(JTable table, DefaultTableModel tableModel) {
        for (int row : table.getSelectedRows()) {
            Object currentPersonalRide = tableModel.getValueAt(row, 4);
            JComboBox<String> personalRideComboBox = new JComboBox<>(new String[]{"YES", "NO"});
            personalRideComboBox.setSelectedItem(currentPersonalRide);
            int option = JOptionPane.showConfirmDialog(table, personalRideComboBox, "Edit Personal Ride", JOptionPane.OK_CANCEL_OPTION);

            if (option == JOptionPane.OK_OPTION) {
                tableModel.setValueAt(personalRideComboBox.getSelectedItem(), row, 4);
            }
        }
    }

    private static void deleteSelectedRows(JTable table, DefaultTableModel tableModel) {
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

    // data for testing purposes
    // this will be loaded from database in future
    public static List<RideModel> getSampleRideHistory() {
        List<RideModel> rides = new ArrayList<>();
        // Keeping the price close to 1 EUR per km, with slight variations for ride types and currency conversions
        rides.add(new RideModel(15.20, "USD", 17.2, "Standard", Timestamp.valueOf("2024-01-10 10:10:10"), 1));  // 1 USD/km
        rides.add(new RideModel(35.00, "EUR", 35.0, "Premium", Timestamp.valueOf("2024-02-12 14:20:30"), 1));   // 1 EUR/km
        rides.add(new RideModel(86.00, "CZK", 8.6, "Comfort", Timestamp.valueOf("2024-03-05 16:50:00"), 1));    // ~10 CZK/km
        rides.add(new RideModel(10.00, "EUR", 12.0, "Standard", Timestamp.valueOf("2024-04-15 09:45:12"), 1));  // 1 EUR/km
        rides.add(new RideModel(20.00, "GBP", 29.0, "Premium", Timestamp.valueOf("2024-05-01 08:30:20"), 1));   // 1 USD/km
        rides.add(new RideModel(300.00, "CZK", 30.0, "Premium", Timestamp.valueOf("2024-06-23 18:40:00"), 1));  // 10 CZK/km
        rides.add(new RideModel(105.00, "CZK", 10.5, "Comfort", Timestamp.valueOf("2024-07-08 13:35:45"), 1));  // 10 CZK/km
        rides.add(new RideModel(22.00, "USD", 22.0, "Premium", Timestamp.valueOf("2024-08-15 19:15:10"), 1));   // 1 USD/km
        rides.add(new RideModel(5.00, "EUR", 8.0, "Standard", Timestamp.valueOf("2024-09-03 17:00:25"), 1));    // 1 EUR/km
        rides.add(new RideModel(60.00, "CZK", 6.0, "Standard", Timestamp.valueOf("2024-10-11 20:55:45"), 1));   // 10 CZK/km
        rides.add(new RideModel(9.50, "EUR", 8.5, "Comfort", Timestamp.valueOf("2024-11-20 22:30:15"), 1));     // 1 EUR/km
        rides.add(new RideModel(12.00, "USD", 10.0, "Standard", Timestamp.valueOf("2024-12-05 12:45:30"), 1));  // 1 USD/km
        rides.add(new RideModel(600.00, "JPY", 3.5, "Standard", Timestamp.valueOf("2024-12-05 12:45:30"), 1));  // 1 USD/km
        return rides;
    }

    private static JPanel createFilterPanel(JTable table, DefaultTableModel tableModel) {
        JPanel filterPanel = new JPanel(new GridLayout(0, 7));

        JTextField minAmountField = new JTextField();
        JTextField maxAmountField = new JTextField();
        JComboBox<String> currencyField = new JComboBox<>(new String[]{"Any", "CZK", "USD", "EUR", "GBP", "JPY"});
        JTextField minDistanceField = new JTextField();
        JTextField maxDistanceField = new JTextField();
        JComboBox<String> categoryField = new JComboBox<>(new String[]{"Any", "Premium", "Comfort", "Standard"});
        JComboBox<String> personalRideField = new JComboBox<>(new String[]{"Any", "YES", "NO"});
        JTextField minPeopleField = new JTextField();
        JTextField maxPeopleField = new JTextField();
        JDateChooser startDateChooser = new JDateChooser();
        startDateChooser.setLocale(Locale.ENGLISH);
        JDateChooser endDateChooser = new JDateChooser();
        endDateChooser.setLocale(Locale.ENGLISH);
        JButton filterButton = new JButton("Filter");

        filterButton.addActionListener(e -> applyFilters(
                table, tableModel, minAmountField, maxAmountField, currencyField,
                minDistanceField, maxDistanceField, categoryField, personalRideField,
                minPeopleField, maxPeopleField, startDateChooser, endDateChooser));

        filterPanel.add(new JLabel("Amount (Min)"));
        filterPanel.add(new JLabel("Amount (Max)"));
        filterPanel.add(new JLabel("Currency"));
        filterPanel.add(new JLabel("Distance (Min)"));
        filterPanel.add(new JLabel("Distance (Max)"));
        filterPanel.add(new JLabel("Category"));
        filterPanel.add(new JLabel("Personal Ride"));
        filterPanel.add(minAmountField);
        filterPanel.add(maxAmountField);
        filterPanel.add(currencyField);
        filterPanel.add(minDistanceField);
        filterPanel.add(maxDistanceField);
        filterPanel.add(categoryField);
        filterPanel.add(personalRideField);
        filterPanel.add(new JLabel("People (Min)"));
        filterPanel.add(new JLabel("People (Max)"));
        filterPanel.add(new JLabel("Start Date"));
        filterPanel.add(new JLabel("End Date"));
        filterPanel.add(new JLabel(""));
        filterPanel.add(new JLabel(""));
        filterPanel.add(new JLabel(""));
        filterPanel.add(minPeopleField);
        filterPanel.add(maxPeopleField);
        filterPanel.add(startDateChooser);
        filterPanel.add(endDateChooser);
        filterPanel.add(new JLabel(""));
        filterPanel.add(new JLabel(""));
        filterPanel.add(new JLabel(""));
        filterPanel.add(new JLabel(""));
        filterPanel.add(new JLabel(""));
        filterPanel.add(new JLabel(""));

        filterPanel.add(filterButton);

        return filterPanel;
    }

    private static void applyFilters(JTable table, DefaultTableModel tableModel,
                                     JTextField minAmountField, JTextField maxAmountField,
                                     JComboBox<String> currencyField, JTextField minDistanceField,
                                     JTextField maxDistanceField, JComboBox<String> categoryField,
                                     JComboBox<String> personalRideField, JTextField minPeopleField,
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
            filters.add(RowFilter.regexFilter(selectedCategory, 3));
        }

        String selectedRide = (String) personalRideField.getSelectedItem();
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

    //Just to parse Integers safelz
    private static Integer parseIntField(String text) {
        try {
            return Integer.parseInt(text);
        } catch (NumberFormatException e) {
            return null;
        }
    }
}