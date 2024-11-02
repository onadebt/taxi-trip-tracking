package cz.muni.fi.pv168.project.ui.tabs;

import cz.muni.fi.pv168.project.ui.model.CategoryListModel;
import cz.muni.fi.pv168.project.ui.model.RideModel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class RidesHistory {

    // Sample data for the table (will be loaded from db)
    public static List<RideModel> rideHistory = getSampleRideHistory();

    public static JPanel createRidesHistoryPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        JLabel label = new JLabel("History of taxi rides:");
        panel.add(label, BorderLayout.NORTH);

        JTable rideHistoryTable = createRidesTable();
        JScrollPane scrollPane = new JScrollPane(rideHistoryTable);

        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }

    private static JTable createRidesTable() {
        String[] columnNames = {"Amount", "Currency", "Distance", "Category", "Personal Ride", "Date"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return true; // All cells are editable
            }
        };

        for (RideModel ride : rideHistory) {
            Object[] rowData = {
                ride.getAmountCurrency(),
                ride.getCurrency(),
                ride.getDistance(),
                ride.getCategoryName(),
                ride.getPersonalRide() ? "YES" : "NO",
                ride.getCreatedDate()
            };
            tableModel.addRow(rowData);
        }

        JTable table = new JTable(tableModel);
        table.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION); // Allow multi-row selection

        TableColumn currencyColumn = table.getColumnModel().getColumn(1);
        JComboBox<String> currencyComboBox = new JComboBox<>(new String[]{"CZK", "EUR", "USD"});
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

        // Edit Amount Option
        JMenuItem editAmountItem = new JMenuItem("Edit Amount");
        editAmountItem.addActionListener(event -> {
            for (int row : table.getSelectedRows()) {
                Object currentAmount = tableModel.getValueAt(row, 0);
                String newAmountStr = JOptionPane.showInputDialog(table, "Enter new amount:", currentAmount);

                try {
                    double newAmount = Double.parseDouble(newAmountStr);
                    tableModel.setValueAt(newAmount, row, 0);
                    rideHistory.get(row).setAmountCurrency(newAmount);

                    System.out.println("Updated Ride History:");
                    for (RideModel ride : rideHistory) {
                        System.out.println(ride.getAmountCurrency());
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(table, "Invalid amount entered.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Edit Currency Option
        JMenuItem editCurrencyItem = new JMenuItem("Edit Currency");
        editCurrencyItem.addActionListener(event -> {
            for (int row : table.getSelectedRows()) {
                Object currentCurrency = tableModel.getValueAt(row, 1);
                JComboBox<String> currencyComboBox = new JComboBox<>(new String[]{"CZK", "EUR", "USD"});
                currencyComboBox.setSelectedItem(currentCurrency);
                int option = JOptionPane.showConfirmDialog(table, currencyComboBox, "Choose new currency", JOptionPane.OK_CANCEL_OPTION);

                if (option == JOptionPane.OK_OPTION) {
                    tableModel.setValueAt(currencyComboBox.getSelectedItem(), row, 1);
                }
            }
        });

        // Edit Distance Option
        JMenuItem editDistanceItem = new JMenuItem("Edit Distance");
        editDistanceItem.addActionListener(event -> {
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
        });

        // Edit Category Name Option
        JMenuItem editCategoryItem = new JMenuItem("Edit Category");
        editCategoryItem.addActionListener(event -> {
            for (int row : table.getSelectedRows()) {
                Object currentCategory = tableModel.getValueAt(row, 3);
                JComboBox<String> categoryComboBox = new JComboBox<>(new String[]{"Premium", "Comfort", "Standard"});
                categoryComboBox.setSelectedItem(currentCategory);
                int option = JOptionPane.showConfirmDialog(table, categoryComboBox, "Choose new category", JOptionPane.OK_CANCEL_OPTION);

                if (option == JOptionPane.OK_OPTION) {
                    tableModel.setValueAt(categoryComboBox.getSelectedItem(), row, 3);
                }
            }
        });

        // Edit Personal Ride Option
        JMenuItem editPersonalRideItem = new JMenuItem("Edit Personal Opinion");
        editPersonalRideItem.addActionListener(event -> {
            for (int row : table.getSelectedRows()) {
                Object currentPersonalRide = tableModel.getValueAt(row, 4);
                JComboBox<String> personalRideComboBox = new JComboBox<>(new String[]{"YES", "NO"});
                personalRideComboBox.setSelectedItem(currentPersonalRide);
                int option = JOptionPane.showConfirmDialog(table, personalRideComboBox, "Edit Personal Ride", JOptionPane.OK_CANCEL_OPTION);

                if (option == JOptionPane.OK_OPTION) {
                    tableModel.setValueAt(personalRideComboBox.getSelectedItem(), row, 4);
                }
            }
        });

        // Delete Selected Rows Option
        JMenuItem deleteRowsItem = new JMenuItem("Delete Selected Rows");
        deleteRowsItem.addActionListener(event -> {
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
        });

        popupMenu.add(editAmountItem);
        popupMenu.add(editCurrencyItem);
        popupMenu.add(editDistanceItem);
        popupMenu.add(editCategoryItem);
        popupMenu.add(editPersonalRideItem);
        popupMenu.addSeparator();
        popupMenu.add(deleteRowsItem);

        popupMenu.show((Component) e.getSource(), e.getX(), e.getY());
    }

    // data for testing purposes
    // this will be loaded from database in future
    public static List<RideModel> getSampleRideHistory() {
        List<RideModel> rides = new ArrayList<>();
        // Keeping the price close to 1 EUR per km, with slight variations for ride types and currency conversions
        rides.add(new RideModel(15.20, "USD", 17.2, "Standard", Timestamp.valueOf("2024-01-10 10:10:10")));  // 1 USD/km
        rides.add(new RideModel(35.00, "EUR", 35.0, "Premium", Timestamp.valueOf("2024-02-12 14:20:30")));   // 1 EUR/km
        rides.add(new RideModel(86.00, "CZK", 8.6, "Comfort", Timestamp.valueOf("2024-03-05 16:50:00")));    // ~10 CZK/km
        rides.add(new RideModel(10.00, "EUR", 12.0, "Standard", Timestamp.valueOf("2024-04-15 09:45:12")));  // 1 EUR/km
        rides.add(new RideModel(20.00, "USD", 25.0, "Premium", Timestamp.valueOf("2024-05-01 08:30:20")));   // 1 USD/km
        rides.add(new RideModel(300.00, "CZK", 30.0, "Premium", Timestamp.valueOf("2024-06-23 18:40:00")));  // 10 CZK/km
        rides.add(new RideModel(105.00, "CZK", 10.5, "Comfort", Timestamp.valueOf("2024-07-08 13:35:45")));  // 10 CZK/km
        rides.add(new RideModel(22.00, "USD", 22.0, "Premium", Timestamp.valueOf("2024-08-15 19:15:10")));   // 1 USD/km
        rides.add(new RideModel(5.00, "EUR", 8.0, "Standard", Timestamp.valueOf("2024-09-03 17:00:25")));    // 1 EUR/km
        rides.add(new RideModel(60.00, "CZK", 6.0, "Standard", Timestamp.valueOf("2024-10-11 20:55:45")));   // 10 CZK/km
        rides.add(new RideModel(9.50, "EUR", 8.5, "Comfort", Timestamp.valueOf("2024-11-20 22:30:15")));     // 1 EUR/km
        rides.add(new RideModel(12.00, "USD", 10.0, "Standard", Timestamp.valueOf("2024-12-05 12:45:30")));  // 1 USD/km
        return rides;
    }
}
