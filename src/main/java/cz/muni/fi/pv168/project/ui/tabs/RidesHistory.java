package cz.muni.fi.pv168.project.ui.tabs;

import javax.swing.*;
import javax.swing.table.TableColumn;
import javax.swing.table.TableRowSorter;

import cz.muni.fi.pv168.project.model.Category;
import cz.muni.fi.pv168.project.model.Currency;
import cz.muni.fi.pv168.project.model.Ride;
import cz.muni.fi.pv168.project.model.enums.TripType;
import cz.muni.fi.pv168.project.service.interfaces.IRideService;
import cz.muni.fi.pv168.project.service.port.ExportService;
import cz.muni.fi.pv168.project.service.port.ImportService;
import cz.muni.fi.pv168.project.ui.action.JsonExportAction;
import cz.muni.fi.pv168.project.ui.action.JsonImportAction;

import java.awt.*;
import java.awt.event.*;
import java.sql.Timestamp;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.List;

import com.toedter.calendar.JDateChooser;
import cz.muni.fi.pv168.project.ui.action.NewRideAction;
import cz.muni.fi.pv168.project.ui.model.ComboBoxModelAdapter;
import cz.muni.fi.pv168.project.ui.model.RideTableModel;
import cz.muni.fi.pv168.project.ui.renderers.*;

import java.util.Date;
import java.util.Locale;

public class RidesHistory extends JPanel {

    private final List<Ride> rideHistory;
    private final IRideService rideService;
//    private final CrudService<Currency> currencyCrudService;
//    private final CrudService<Category> categoryCrudService;
    private final ListModel<Currency> currencyListModel;
    private final ListModel<Category> categoryListModel;
    private final ImportService importService;
    private final ExportService exportService;

    private final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)
            .withLocale(Locale.forLanguageTag("cs-CZ"))
            .withZone(ZoneId.systemDefault());

    public RidesHistory(IRideService rideService, ListModel<Currency> currencyListModel, ListModel<Category> categoryListModel, /*CrudService<Currency> currencyCrudService, CrudService<Category> categoryCrudService,*/ ImportService importService, ExportService exportService) {
        super(new BorderLayout());
        this.rideService = rideService;
//        this.currencyCrudService = currencyCrudService;
//        this.categoryCrudService = categoryCrudService;
        this.currencyListModel = currencyListModel;
        this.categoryListModel = categoryListModel;
        this.rideHistory = rideService.getAll();
        this.importService = importService;
        this.exportService = exportService;

        JLabel label = new JLabel("History of taxi rides:");
        this.add(label, BorderLayout.NORTH);

        JTable rideHistoryTable = createRidesTable();
        JScrollPane scrollPane = new JScrollPane(rideHistoryTable);

        JToolBar toolBar = createToolBar(rideHistoryTable, (RideTableModel) rideHistoryTable.getModel());

        JPanel filterPanel = createFilterPanel(rideHistoryTable, (RideTableModel) rideHistoryTable.getModel());

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));
        topPanel.add(toolBar);
        topPanel.add(filterPanel);

        this.add(topPanel, BorderLayout.NORTH);
        this.add(scrollPane, BorderLayout.CENTER);
    }

//    public static JPanel createRidesHistoryPanel(IRideService rideService, CrudService<Currency> currencyCrudService, CrudService<Category> categoryCrudService, ImportService importService, ExportService exportService) {
//        return new RidesHistory(rideService, currencyCrudService, categoryCrudService, importService, exportService);
//    }

    private JToolBar createToolBar(JTable table, RideTableModel tableModel) {
        JToolBar toolBar = new JToolBar();

        toolBar.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 0));

        JButton addButton = new JButton("Add New Ride");
        addButton.addActionListener(new NewRideAction(this, rideService, currencyListModel, categoryListModel));
        addButton.setMargin(new Insets(5, 10, 5, 10));
        toolBar.add(addButton);

        JButton editAmountButton = new JButton("Edit Amount");
        editAmountButton.setMargin(new Insets(5, 10, 5, 10));
        editAmountButton.addActionListener(e -> editAmount(table, tableModel));
        toolBar.add(editAmountButton);

        JButton editCurrencyButton = new JButton("Edit Currency");
        editCurrencyButton.setMargin(new Insets(5, 10, 5, 10));
        editCurrencyButton.addActionListener(e -> editCurrency(table, tableModel));
        toolBar.add(editCurrencyButton);

        JButton editDistanceButton = new JButton("Edit Distance");
        editDistanceButton.setMargin(new Insets(5, 10, 5, 10));
        editDistanceButton.addActionListener(e -> editDistance(table, tableModel));
        toolBar.add(editDistanceButton);

        JButton editCategoryButton = new JButton("Edit Category");
        editCategoryButton.setMargin(new Insets(5, 10, 5, 10));
        editCategoryButton.addActionListener(e -> editCategory(table, tableModel));
        toolBar.add(editCategoryButton);

        JButton editTripType = new JButton("Edit Trip Type");
        editTripType.setMargin(new Insets(5, 10, 5, 10));
        editTripType.addActionListener(e -> editTripType(table, tableModel));
        toolBar.add(editTripType);

        JButton editNumberOfPassengers = new JButton("Edit Number of Passengers");
        editNumberOfPassengers.setMargin(new Insets(5, 10, 5, 10));
        editNumberOfPassengers.addActionListener(e -> editNumberOfPassengers(table, tableModel));
        toolBar.add(editNumberOfPassengers);

        JButton deleteRowsButton = new JButton("Delete Selected Rows");
        deleteRowsButton.setMargin(new Insets(5, 10, 5, 10));
        deleteRowsButton.addActionListener(e -> deleteSelectedRows(table, tableModel));
        toolBar.add(deleteRowsButton);

        JButton importButton = new JButton("Import");
        importButton.setMargin(new Insets(5, 10, 5, 10));
        importButton.addActionListener(new JsonImportAction(this, importService));
        toolBar.add(importButton);

        JButton exportButton = new JButton("Export");
        exportButton.setMargin(new Insets(5, 10, 5, 10));
        exportButton.addActionListener(new JsonExportAction(this, exportService, rideService));
        toolBar.add(exportButton);

        return toolBar;
    }


    private JTable createRidesTable() {
        RideTableModel rideTableModel = new RideTableModel(this.rideService);
        JTable table = new JTable(rideTableModel);
        TableColumn categoryColumn = table.getColumnModel().getColumn(3);
        categoryColumn.setCellRenderer(new CategoryNameIconRenderer());
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

        JMenuItem editTripType = new JMenuItem("Edit Trip type");
        editTripType.addActionListener(event -> editTripType(table, tableModel));
        popupMenu.add(editTripType);

        JMenuItem editNumberOfPassengersItem = new JMenuItem("Edit Number of Passengers");
        editNumberOfPassengersItem.addActionListener(event -> editNumberOfPassengers(table, tableModel));
        popupMenu.add(editNumberOfPassengersItem);

        popupMenu.addSeparator();

        JMenuItem deleteRowsItem = new JMenuItem("Delete Selected Rows");
        deleteRowsItem.addActionListener(event -> deleteSelectedRows(table, tableModel));
        popupMenu.add(deleteRowsItem);

        popupMenu.show(e.getComponent(), e.getX(), e.getY());
    }

    private void editAmount(JTable table, RideTableModel tableModel) {
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


    private void editDistance(JTable table, RideTableModel tableModel) {
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

    private void editTripType(JTable table, RideTableModel tableModel) {
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

    private void deleteSelectedRows(JTable table, RideTableModel tableModel) {
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



    private JPanel createFilterPanel(JTable table, RideTableModel tableModel) {
        // TODO: automatic update of ComboBoxes on DB change
        JPanel filterPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);  // Space between components
        gbc.fill = GridBagConstraints.HORIZONTAL;

        filterPanel.setBorder(BorderFactory.createEmptyBorder(15, 0, 5, 0));

        // Set horizontal weight to make sure components stretch across the available space
        gbc.weightx = 0.125;

        // Row 0: Amount filter
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

        // Row 1: Distance filter and Category filter
        gbc.gridy = 1;
        gbc.gridx = 0;
        filterPanel.add(new JLabel("Distance (Max)"), gbc);
        JTextField maxDistanceField = new JTextField();
        gbc.gridx = 1;
        filterPanel.add(maxDistanceField, gbc);

        gbc.gridx = 2;
        filterPanel.add(new JLabel("Category"), gbc);
        JComboBox<Category> categoryField = createCategoryComboBox(new CategoryNameRenderer());
//        categoryField.setRenderer(new CategoryRenderer());
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

        // Row 2: People filter and Date filter
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

        // Row 3: Filter button
        gbc.gridy = 3;
        gbc.gridx = 0;
        gbc.gridwidth = 8;
        gbc.fill = GridBagConstraints.NONE;  // Prevent button from stretching
        gbc.anchor = GridBagConstraints.CENTER; // Center align the button
        gbc.insets = new Insets(10, 5, 10, 5); // Top and bottom padding = 10px

        JButton filterButton = new JButton("Filter");
        filterButton.setPreferredSize(new Dimension(150, filterButton.getPreferredSize().height));
        filterButton.addActionListener(e -> applyFilters(
                table, tableModel, minAmountField, maxAmountField, currencyField,
                minDistanceField, maxDistanceField, categoryField, tripTypeJComboBox,
                minPeopleField, maxPeopleField, startDateChooser, endDateChooser));
        filterPanel.add(filterButton, gbc);

        return filterPanel;
    }

    private void applyFilters(JTable table, RideTableModel tableModel,
                              JTextField minAmountField, JTextField maxAmountField,
                              JComboBox<Currency> currencyField, JTextField minDistanceField,
                              JTextField maxDistanceField, JComboBox<Category> categoryField,
                              JComboBox<TripType> tripTypeField, JTextField minPeopleField,
                              JTextField maxPeopleField, JDateChooser startDateChooser, JDateChooser endDateChooser) {

        TableRowSorter<RideTableModel> sorter = new TableRowSorter<>(tableModel);
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

        TripType selectedRide = (TripType) tripTypeField.getSelectedItem();
        if (selectedRide != null) {
            filters.add(RowFilter.regexFilter(selectedRide.name(), 4));
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