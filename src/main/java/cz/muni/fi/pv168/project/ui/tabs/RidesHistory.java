package cz.muni.fi.pv168.project.ui.tabs;

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

    public static JPanel createRidesHistoryPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        JLabel label = new JLabel("History of taxi rides:");
        panel.add(label, BorderLayout.NORTH);

        // Sample data for the table (will be loaded from db)
        List<RideModel> rideHistory = getSampleRideHistory();

        JTable table = createRidesTable(rideHistory);
        JScrollPane scrollPane = new JScrollPane(table);

        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }

    private static JTable createRidesTable(List<RideModel> rideHistory) {
        String[] columnNames = {"Amount", "Currency", "Distance", "Category", "Date"};
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
                ride.getCreatedDate()
            };
            tableModel.addRow(rowData);
        }

        JTable table = new JTable(tableModel);
        table.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION); // Allow multi-row selection

        TableColumn currencyColumn = table.getColumnModel().getColumn(1);
        JComboBox<String> currencyComboBox = new JComboBox<>(new String[]{"CZK", "EUR", "USD"});
        currencyColumn.setCellEditor(new DefaultCellEditor(currencyComboBox));

        TableColumn categoryColumn = table.getColumnModel().getColumn(3);
        JComboBox<String> categoryComboBox = new JComboBox<>(new String[]{"Premium", "Comfort", "Standard"});
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
        popupMenu.addSeparator();
        popupMenu.add(deleteRowsItem);

        popupMenu.show((Component) e.getSource(), e.getX(), e.getY());
    }

    // data for testing purposes
    // this will be loaded from database in future
    public static List<RideModel> getSampleRideHistory() {
        List<RideModel> rides = new ArrayList<>();
        rides.add(new RideModel(100.50, "USD", 15.2, "Standard", Timestamp.valueOf("2024-01-10 10:10:10")));
        rides.add(new RideModel(250.00, "EUR", 35.0, "Premium", Timestamp.valueOf("2024-02-12 14:20:30")));
        rides.add(new RideModel(75.75, "CZK", 8.6, "Comfort", Timestamp.valueOf("2024-03-05 16:50:00")));
        return rides;
    }
}
