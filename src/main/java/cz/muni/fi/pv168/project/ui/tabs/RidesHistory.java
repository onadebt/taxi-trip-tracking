package cz.muni.fi.pv168.project.ui.tabs;

import cz.muni.fi.pv168.project.model.*;
import cz.muni.fi.pv168.project.model.enums.TripType;
import cz.muni.fi.pv168.project.service.interfaces.IRideService;
import cz.muni.fi.pv168.project.service.port.ExportService;
import cz.muni.fi.pv168.project.service.port.ImportService;
import cz.muni.fi.pv168.project.ui.action.JsonExportAction;
import cz.muni.fi.pv168.project.ui.action.JsonImportAction;
import cz.muni.fi.pv168.project.ui.action.NewRideAction;
import cz.muni.fi.pv168.project.ui.model.ComboBoxModelAdapter;
import cz.muni.fi.pv168.project.ui.model.RideTableModel;
import cz.muni.fi.pv168.project.ui.renderers.*;

import javax.swing.*;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class RidesHistory extends JPanel {

    private final JTable rideHistoryTable;
    private final List<Ride> rideHistory;
    private final IRideService rideService;
    private final RideTableModel rideTableModel;

    private final ListModel<Currency> currencyListModel;
    private final ListModel<Category> categoryListModel;
    private final ImportService importService;
    private final ExportService exportService;

    public RidesHistory(RideTableModel rideTableModel, IRideService rideService, ListModel<Currency> currencyListModel, ListModel<Category> categoryListModel, ImportService importService, ExportService exportService) {
        super(new BorderLayout());
        this.rideService = rideService;
        this.rideTableModel = rideTableModel;

        this.currencyListModel = currencyListModel;
        this.categoryListModel = categoryListModel;
        this.rideHistory = rideService.findAll();
        this.importService = importService;
        this.exportService = exportService;
        this.rideHistoryTable = createRidesTable(rideTableModel);

        JLabel label = new JLabel("History of taxi rides:");
        this.add(label, BorderLayout.NORTH);

        JScrollPane scrollPane = new JScrollPane(rideHistoryTable);

        JToolBar toolBar = createToolBar(rideHistoryTable, rideTableModel);

        RideFilterPanel filterPanel = new RideFilterPanel(rideHistoryTable, rideTableModel, rideService, categoryListModel, currencyListModel);

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));
        topPanel.add(toolBar);
        topPanel.add(filterPanel.createFilterPanel());

        this.add(topPanel, BorderLayout.NORTH);
        this.add(scrollPane, BorderLayout.CENTER);
    }


    private JToolBar createToolBar(JTable table, RideTableModel tableModel) {
        JToolBar toolBar = new JToolBar();

        toolBar.setFloatable(false);
        toolBar.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));

        JButton addButton = new JButton("Add New Ride");
        addButton.addActionListener(new NewRideAction(this, tableModel, rideService, currencyListModel, categoryListModel));
        toolBar.add(addButton);

        JButton editAmountButton = new JButton("Edit Amount");
        editAmountButton.addActionListener(e -> editAmount(table, tableModel));
        editAmountButton.setEnabled(false);
        toolBar.add(editAmountButton);

        JButton editCurrencyButton = new JButton("Edit Currency");
        editCurrencyButton.addActionListener(e -> editCurrency(table, tableModel));
        editCurrencyButton.setEnabled(false);
        toolBar.add(editCurrencyButton);

        JButton editDistanceButton = new JButton("Edit Distance");
        editDistanceButton.addActionListener(e -> editDistance(table, tableModel));
        editDistanceButton.setEnabled(false);
        toolBar.add(editDistanceButton);

        JButton editCategoryButton = new JButton("Edit Category");
        editCategoryButton.addActionListener(e -> editCategory(table, tableModel));
        editCategoryButton.setEnabled(false);
        toolBar.add(editCategoryButton);

        JButton editTripType = new JButton("Edit Trip Type");
        editTripType.addActionListener(e -> editTripType(table, tableModel));
        editTripType.setEnabled(false);
        toolBar.add(editTripType);

        JButton editNumberOfPassengers = new JButton("Edit Passengers");
        editNumberOfPassengers.addActionListener(e -> editNumberOfPassengers(table, tableModel));
        editNumberOfPassengers.setEnabled(false);
        toolBar.add(editNumberOfPassengers);

        JButton editDateButton = new JButton("Edit Date");
        editDateButton.addActionListener(e -> editDate(table, tableModel));
        editDateButton.setEnabled(false);
        toolBar.add(editDateButton);

        JButton deleteRowsButton = new JButton("Delete");
        deleteRowsButton.addActionListener(e -> deleteSelectedRows(table, tableModel));
        deleteRowsButton.setEnabled(false);
        toolBar.add(deleteRowsButton);

        JProgressBar progressBar = new JProgressBar();
        JButton importButton = new JButton("Import");
        importButton.addActionListener(new JsonImportAction(this, importService, progressBar, rideTableModel));
        toolBar.add(importButton);

        JButton exportButton = new JButton("Export");
        exportButton.addActionListener(new JsonExportAction(this, exportService, rideService));
        toolBar.add(exportButton);
        toolBar.add(progressBar);

        rideHistoryTable.getSelectionModel().addListSelectionListener(e -> {
            boolean selected = rideHistoryTable.getSelectedRowCount() == 1;
            if (selected) {
                int selectedRow = rideHistoryTable.getSelectedRow();
                TripType tripType = (TripType) tableModel.getValueAt(selectedRow, 4);
                editAmountButton.setEnabled(tripType != TripType.Personal);
            } else {
                editAmountButton.setEnabled(false);
            }
            editCurrencyButton.setEnabled(selected);
            editDistanceButton.setEnabled(selected);
            editCategoryButton.setEnabled(selected);
            editTripType.setEnabled(selected);
            editNumberOfPassengers.setEnabled(selected);
            editDateButton.setEnabled(selected);
        });

        rideHistoryTable.getSelectionModel().addListSelectionListener(e -> {
            boolean selected = rideHistoryTable.getSelectedRow() >= 0;
            deleteRowsButton.setEnabled(selected);
        });

        return toolBar;
    }


    private JTable createRidesTable(RideTableModel rideTableModel) {
        JTable table = new JTable(rideTableModel);

        TableColumn amountColumn = table.getColumnModel().getColumn(0);
        TableColumn currencyColumn = table.getColumnModel().getColumn(1);
        TableColumn categoryColumn = table.getColumnModel().getColumn(3);
        TableColumn dateColumn = table.getColumnModel().getColumn(6);
        TableColumn distanceColumn = table.getColumnModel().getColumn(2);

        amountColumn.setCellRenderer(new AmountRenderer());
        currencyColumn.setCellRenderer(new CurrencyRenderer());
        categoryColumn.setCellRenderer(new CategoryNameIconRenderer());
        dateColumn.setCellRenderer(new DateRenderer());
        distanceColumn.setCellRenderer(new DistanceRenderer());
        table.setRowHeight(32);

        table.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (e.isPopupTrigger()) {
                    showPopupMenu(e, table, rideTableModel);
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (e.isPopupTrigger()) {
                    showPopupMenu(e, table, rideTableModel);
                }
            }
        });

        return table;
    }


    private void showPopupMenu(MouseEvent e, JTable table, RideTableModel tableModel) {
        JPopupMenu popupMenu = new JPopupMenu();

        int selectedRowCount = table.getSelectedRowCount();

        if (selectedRowCount == 1) {
            int selectedRow = table.getSelectedRow();
            TripType tripType = (TripType) tableModel.getValueAt(selectedRow, 4);

            JMenuItem editAmountItem = new JMenuItem("Edit Amount");
            editAmountItem.addActionListener(event -> editAmount(table, tableModel));
            editAmountItem.setEnabled(tripType != TripType.Personal);
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

            JMenuItem editTripType = new JMenuItem("Edit Trip Type");
            editTripType.addActionListener(event -> editTripType(table, tableModel));
            popupMenu.add(editTripType);

            JMenuItem editNumberOfPassengersItem = new JMenuItem("Edit Passengers");
            editNumberOfPassengersItem.addActionListener(event -> editNumberOfPassengers(table, tableModel));
            popupMenu.add(editNumberOfPassengersItem);

            JMenuItem editDateItem = new JMenuItem("Edit Date");
            editDateItem.addActionListener(event -> editDate(table, tableModel));
            popupMenu.add(editDateItem);

            popupMenu.addSeparator();
        }

        JMenuItem deleteRowsItem = new JMenuItem("Delete Selected Rows");
        deleteRowsItem.addActionListener(event -> deleteSelectedRows(table, tableModel));
        popupMenu.add(deleteRowsItem);

        popupMenu.show(e.getComponent(), e.getX(), e.getY());
    }

    private void editAmount(JTable table, RideTableModel tableModel) {
        for (int row : table.getSelectedRows()) {
            Object currentAmount = tableModel.getValueAt(row, 0);
            String newAmountStr = JOptionPane.showInputDialog(table, "Enter new amount:", currentAmount);


            if (newAmountStr != null) {
                try {
                    BigDecimal newAmount = new BigDecimal(newAmountStr);
                    tableModel.setValueAt(newAmount, row, 0);
                    rideHistory.get(row).setAmountCurrency(newAmount);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(table, "Invalid amount entered.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

    private void editDistance(JTable table, RideTableModel tableModel) {
        for (int row : table.getSelectedRows()) {
            Object currentDistance = tableModel.getValueAt(row, 2);
            String newDistanceStr = JOptionPane.showInputDialog(table, "Enter new distance:", currentDistance);

            if (newDistanceStr != null) {
                try {
                    double newDistance = Double.parseDouble(newDistanceStr);
                    tableModel.setValueAt(newDistance, row, 2);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(table, "Invalid distance entered.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

    private void editCategory(JTable table, RideTableModel tableModel) {
        for (int row : table.getSelectedRows()) {
            Object currentCategoryName = tableModel.getValueAt(row, 3);

            JComboBox<Category> categoryBox = createCategoryComboBox(new CategoryNameIconRenderer());
            categoryBox.setSelectedItem(currentCategoryName);

            int option = JOptionPane.showConfirmDialog(table, categoryBox, "Choose new category", JOptionPane.OK_CANCEL_OPTION);
            if (option == JOptionPane.OK_OPTION) {
                tableModel.setValueAt(categoryBox.getSelectedItem(), row, 3);
            }
        }
    }

    private void editCurrency(JTable table, RideTableModel tableModel) {
        for (int row : table.getSelectedRows()) {
            Object currentCurrency = tableModel.getValueAt(row, 1);

            JComboBox<Currency> currencyComboBox = createCurrencyComboBox();
            currencyComboBox.setSelectedItem(currentCurrency);

            int option = JOptionPane.showConfirmDialog(table, currencyComboBox, "Choose new currency", JOptionPane.OK_CANCEL_OPTION);
            if (option == JOptionPane.OK_OPTION) {
                tableModel.setValueAt(currencyComboBox.getSelectedItem(), row, 1);
            }
        }
    }

    private void editTripType(JTable table, RideTableModel tableModel) {
        for (int row : table.getSelectedRows()) {
            Object currentTripType = tableModel.getValueAt(row, 4);
            JComboBox<TripType> tripTypeComboBox = new JComboBox<>(TripType.values());
            tripTypeComboBox.setSelectedItem(currentTripType);
            int option = JOptionPane.showConfirmDialog(table, tripTypeComboBox, "Trip Type", JOptionPane.OK_CANCEL_OPTION);

            if (option == JOptionPane.OK_OPTION) {
                tableModel.setValueAt(tripTypeComboBox.getSelectedItem(), row, 4);

                if (Objects.equals(tripTypeComboBox.getSelectedItem(), TripType.Personal)) {
                    tableModel.setValueAt(0D, row, 0);
                }
            }
        }
    }

    private void editNumberOfPassengers(JTable table, RideTableModel tableModel) {
        for (int row : table.getSelectedRows()) {
            Object currentNumberOfPassengers = tableModel.getValueAt(row, 5);
            String newNumberOfPassengersStr = JOptionPane.showInputDialog(table, "Enter new number of passengers:", currentNumberOfPassengers);

            if (newNumberOfPassengersStr != null) {
                try {
                    int newNumberOfPassengers = Integer.parseInt(newNumberOfPassengersStr);
                    tableModel.setValueAt(newNumberOfPassengers, row, 5);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(table, "Invalid number of passengers entered.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

    private void editDate(JTable table, RideTableModel tableModel) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        dateFormat.setLenient(false);

        for (int row : table.getSelectedRows()) {
            Object currentCreatedAt = tableModel.getValueAt(row, 6);
            Instant currentInstant = (Instant) currentCreatedAt;
            Date currentDate = Date.from(currentInstant);
            String currentCreatedAtStr = dateFormat.format(currentDate);
            String newDateStr = JOptionPane.showInputDialog(table, "Enter new date:", currentCreatedAtStr);

            if (newDateStr != null) {
                try {
                    Date parsedDate = dateFormat.parse(newDateStr);
                    Timestamp newDate = new Timestamp(parsedDate.getTime());
                    tableModel.setValueAt(newDate.toInstant(), row, 6);
                } catch (ParseException ex) {
                    JOptionPane.showMessageDialog(table, "Invalid date entered. Please use the format dd-MM-yyyy HH:mm:ss.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

    private void deleteSelectedRows(JTable table, RideTableModel tableModel) {
        int[] selectedRows = table.getSelectedRows();
        if (selectedRows.length > 0) {
            int confirm = JOptionPane.showConfirmDialog(table, "Are you sure you want to delete all the selected rows?", "Confirm Deletion", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                for (int i = selectedRows.length - 1; i >= 0; i--) {
                    tableModel.removeRow(selectedRows[i]);
                }
            }
        } else {
            JOptionPane.showMessageDialog(table, "No rows selected for deletion.", "Warning", JOptionPane.WARNING_MESSAGE);
        }
    }

    private JComboBox<Category> createCategoryComboBox(AbstractRenderer<Category> categoryRenderer) {
        ComboBoxModel<Category> categoryComboBoxModel = new ComboBoxModelAdapter<>(categoryListModel);
        JComboBox<Category> categoryComboBox = new JComboBox<>(categoryComboBoxModel);
        categoryComboBox.setRenderer(categoryRenderer);

        return categoryComboBox;
    }

    private JComboBox<Currency> createCurrencyComboBox() {
        ComboBoxModel<Currency> currencyComboBoxModel = new ComboBoxModelAdapter<>(currencyListModel);
        JComboBox<Currency> currencyComboBox = new JComboBox<>(currencyComboBoxModel);
        currencyComboBox.setRenderer(new CurrencyRenderer());

        return currencyComboBox;
    }
}