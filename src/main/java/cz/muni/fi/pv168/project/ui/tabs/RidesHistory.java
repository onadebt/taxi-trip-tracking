package cz.muni.fi.pv168.project.ui.tabs;

import com.toedter.calendar.JDateChooser;
import cz.muni.fi.pv168.project.model.Category;
import cz.muni.fi.pv168.project.model.Currency;
import cz.muni.fi.pv168.project.model.Ride;
import cz.muni.fi.pv168.project.model.RideFilterCriteria;
import cz.muni.fi.pv168.project.model.enums.TripType;
import cz.muni.fi.pv168.project.service.RideFilterService;
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
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Locale;
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

        JPanel filterPanel = createFilterPanel(rideHistoryTable, rideTableModel);

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));
        topPanel.add(toolBar);
        topPanel.add(filterPanel);

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

        JButton importButton = new JButton("Import");
        importButton.addActionListener(new JsonImportAction(this, importService));
        toolBar.add(importButton);

        JButton exportButton = new JButton("Export");
        exportButton.addActionListener(new JsonExportAction(this, exportService, rideService));
        toolBar.add(exportButton);

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

        TableColumn currencyColumn = table.getColumnModel().getColumn(1);
        TableColumn categoryColumn = table.getColumnModel().getColumn(3);
        TableColumn dateColumn = table.getColumnModel().getColumn(6);
        TableColumn distanceColumn = table.getColumnModel().getColumn(2);

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
                    double newAmount = Double.parseDouble(newAmountStr);
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

    private JPanel createFilterPanel(JTable table, RideTableModel tableModel) {
        JPanel filterPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        filterPanel.setBorder(BorderFactory.createEmptyBorder(15, 0, 5, 0));

        gbc.weightx = 0.125;

        gbc.gridy = 0;
        gbc.gridx = 0;
        filterPanel.add(new JLabel("Amount (Min)"), gbc);
        JTextField minAmountField = new JTextField();
        gbc.gridx = 1;
        filterPanel.add(minAmountField, gbc);

        gbc.gridx = 2;
        filterPanel.add(new JLabel("Amount (Max)"), gbc);
        JTextField maxAmountField = new JTextField();
        gbc.gridx = 3;
        filterPanel.add(maxAmountField, gbc);

        gbc.gridx = 4;
        filterPanel.add(new JLabel("Currency"), gbc);
        JComboBox<Currency> currencyField = createCurrencyComboBox();
        gbc.gridx = 5;
        filterPanel.add(currencyField, gbc);

        gbc.gridx = 6;
        filterPanel.add(new JLabel("Distance (Min)"), gbc);
        JTextField minDistanceField = new JTextField();
        gbc.gridx = 7;
        filterPanel.add(minDistanceField, gbc);

        gbc.gridy = 1;
        gbc.gridx = 0;
        filterPanel.add(new JLabel("Distance (Max)"), gbc);
        JTextField maxDistanceField = new JTextField();
        gbc.gridx = 1;
        filterPanel.add(maxDistanceField, gbc);

        gbc.gridx = 2;
        filterPanel.add(new JLabel("Category"), gbc);
        JComboBox<Category> categoryField = createCategoryComboBox(new CategoryNameRenderer());
        gbc.gridx = 3;
        filterPanel.add(categoryField, gbc);

        gbc.gridx = 4;
        filterPanel.add(new JLabel("Personal Ride"), gbc);
        JComboBox<TripType> tripTypeJComboBox = new JComboBox<>(TripType.values());
        gbc.gridx = 5;
        filterPanel.add(tripTypeJComboBox, gbc);

        gbc.gridx = 6;
        filterPanel.add(new JLabel("People (Min)"), gbc);
        JTextField minPeopleField = new JTextField();
        gbc.gridx = 7;
        filterPanel.add(minPeopleField, gbc);

        gbc.gridy = 2;
        gbc.gridx = 0;
        filterPanel.add(new JLabel("People (Max)"), gbc);
        JTextField maxPeopleField = new JTextField();
        gbc.gridx = 1;
        filterPanel.add(maxPeopleField, gbc);

        gbc.gridx = 2;
        filterPanel.add(new JLabel("Start Date"), gbc);
        JDateChooser startDateChooser = new JDateChooser();
        startDateChooser.setLocale(Locale.ENGLISH);
        gbc.gridx = 3;
        filterPanel.add(startDateChooser, gbc);

        gbc.gridx = 4;
        filterPanel.add(new JLabel("End Date"), gbc);
        JDateChooser endDateChooser = new JDateChooser();
        endDateChooser.setLocale(Locale.ENGLISH);
        gbc.gridx = 5;
        filterPanel.add(endDateChooser, gbc);

        gbc.gridx = 6;
        filterPanel.add(new JLabel("Some Other Filter"), gbc);
        JTextField otherFilterField = new JTextField();
        gbc.gridx = 7;
        filterPanel.add(otherFilterField, gbc);

        gbc.gridy = 3;
        gbc.gridx = 0;
        gbc.gridwidth = 8;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.LINE_START;
        gbc.insets = new Insets(10, 5, 10, 5);

        JButton filterButton = new JButton("Apply");
        filterButton.setPreferredSize(new Dimension(150, filterButton.getPreferredSize().height));
        filterButton.addActionListener(e -> applyFilters(
                table, tableModel, minAmountField, maxAmountField, currencyField,
                minDistanceField, maxDistanceField, categoryField, tripTypeJComboBox,
                minPeopleField, maxPeopleField, startDateChooser, endDateChooser));
        filterPanel.add(filterButton, gbc);


        gbc.gridx = 1;
        JButton clearButton = new JButton("Clear Filters");
        clearButton.setPreferredSize(new Dimension(150, clearButton.getPreferredSize().height));
        clearButton.addActionListener(e -> {
            minAmountField.setText("");
            maxAmountField.setText("");
            currencyField.setSelectedIndex(-1);
            minDistanceField.setText("");
            maxDistanceField.setText("");
            categoryField.setSelectedIndex(-1);
            tripTypeJComboBox.setSelectedIndex(-1);
            minPeopleField.setText("");
            maxPeopleField.setText("");
            startDateChooser.setDate(null);
            endDateChooser.setDate(null);
        });
        filterPanel.add(clearButton, gbc);

        return filterPanel;
    }

    private void applyFilters(JTable table, RideTableModel tableModel,
                              JTextField minAmountField, JTextField maxAmountField,
                              JComboBox<Currency> currencyField, JTextField minDistanceField,
                              JTextField maxDistanceField, JComboBox<Category> categoryField,
                              JComboBox<TripType> tripTypeField, JTextField minPeopleField,
                              JTextField maxPeopleField, JDateChooser startDateChooser, JDateChooser endDateChooser) {

        RideFilterCriteria criteria = new RideFilterCriteria();

        criteria.setMinAmount(parseDoubleField(minAmountField.getText()));
        criteria.setMaxAmount(parseDoubleField(maxAmountField.getText()));
        criteria.setCurrency((Currency) currencyField.getSelectedItem());
        criteria.setMinDistance(parseDoubleField(minDistanceField.getText()));
        criteria.setMaxDistance(parseDoubleField(maxDistanceField.getText()));
        criteria.setCategory((Category) categoryField.getSelectedItem());
        criteria.setTripType((TripType) tripTypeField.getSelectedItem());
        criteria.setMinPassengers(parseIntField(minPeopleField.getText()));
        criteria.setMaxPassengers(parseIntField(maxPeopleField.getText()));
        criteria.setStartDate(startDateChooser.getDate() != null ? startDateChooser.getDate().toInstant() : null);
        criteria.setEndDate(endDateChooser.getDate() != null ? endDateChooser.getDate().toInstant() : null);

        RideFilterService filterService = new RideFilterService();
        List<Ride> filteredRides = filterService.filterRides(rideService.findAll(), criteria);

        tableModel.setRides(filteredRides);
        table.repaint();
    }


    private Double parseDoubleField(String text) {
        try {
            return text.isEmpty() ? null : Double.parseDouble(text);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private Integer parseIntField(String text) {
        try {
            return text.isEmpty() ? null : Integer.parseInt(text);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private JComboBox<Category> createCategoryComboBox(AbstractRenderer<Category> categoryRenderer) {
        ComboBoxModel<Category> categoryComboBoxModel = new ComboBoxModelAdapter<>(categoryListModel);
        var categoryComboBox = new JComboBox<>(categoryComboBoxModel);
        categoryComboBox.setRenderer(categoryRenderer);

        return categoryComboBox;
    }

    private JComboBox<Currency> createCurrencyComboBox() {
        ComboBoxModel<Currency> currencyComboBoxModel = new ComboBoxModelAdapter<>(currencyListModel);
        var currencyComboBox = new JComboBox<>(currencyComboBoxModel);
        currencyComboBox.setRenderer(new CurrencyRenderer());

        return currencyComboBox;
    }
}