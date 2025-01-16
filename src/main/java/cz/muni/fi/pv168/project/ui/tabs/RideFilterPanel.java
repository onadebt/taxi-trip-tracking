package cz.muni.fi.pv168.project.ui.tabs;

import com.toedter.calendar.JDateChooser;
import cz.muni.fi.pv168.project.model.*;
import cz.muni.fi.pv168.project.model.enums.TripType;
import cz.muni.fi.pv168.project.service.RideFilterService;
import cz.muni.fi.pv168.project.service.interfaces.IRideService;
import cz.muni.fi.pv168.project.ui.model.ComboBoxModelAdapter;
import cz.muni.fi.pv168.project.ui.model.RideTableModel;
import cz.muni.fi.pv168.project.ui.renderers.AbstractRenderer;
import cz.muni.fi.pv168.project.ui.renderers.CategoryNameRenderer;
import cz.muni.fi.pv168.project.ui.renderers.CurrencyRenderer;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class RideFilterPanel {
    private final JTable table;
    private final RideTableModel tableModel;
    private final IRideService rideService;
    private final ListModel<Currency> currencyListModel;
    private final ListModel<Category> categoryListModel;

    public RideFilterPanel(JTable table, RideTableModel tableModel, IRideService rideService, ListModel<Category> categoryListModel, ListModel<Currency> currencyListModel) {
        this.table = table;
        this.tableModel = tableModel;
        this.rideService = rideService;
        this.categoryListModel = categoryListModel;
        this.currencyListModel = currencyListModel;
    }

    public JPanel createFilterPanel() {
        JPanel filterPanel = new JPanel(new GridBagLayout());
        filterPanel.setBorder(BorderFactory.createEmptyBorder(15, 0, 5, 0));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JTextField minAmountField = new JTextField();
        JTextField maxAmountField = new JTextField();
        JTextField minDistanceField = new JTextField();
        JTextField maxDistanceField = new JTextField();
        JTextField minPeopleField = new JTextField();
        JTextField maxPeopleField = new JTextField();
        JComboBox<Currency> currencyField = createCurrencyComboBox();
        JComboBox<Category> categoryField = createCategoryComboBox(new CategoryNameRenderer());
        JComboBox<TripType> tripTypeField = new JComboBox<>(TripType.values());
        JDateChooser startDateChooser = new JDateChooser();
        JDateChooser endDateChooser = new JDateChooser();

        RideFilterCriteriaBuilder builder = new RideFilterCriteriaBuilder(
                minAmountField, maxAmountField, minDistanceField, maxDistanceField,
                minPeopleField, maxPeopleField, currencyField, categoryField,
                tripTypeField, startDateChooser, endDateChooser
        );

        addFilterFields(filterPanel, gbc, builder);
        addActionButtons(filterPanel, gbc, builder);

        return filterPanel;
    }

    private void addFilterFields(JPanel panel, GridBagConstraints gbc, RideFilterCriteriaBuilder builder) {
        int row = 0;

        addLabeledField(panel, gbc, "Amount (Min)", builder.getMinAmountField(), row, 0);
        addLabeledField(panel, gbc, "Amount (Max)", builder.getMaxAmountField(), row, 2);
        addLabeledField(panel, gbc, "Distance (Min)", builder.getMinDistanceField(), row, 4);
        addLabeledField(panel, gbc, "Distance (Max)", builder.getMaxDistanceField(), row, 6);

        row++;

        addLabeledField(panel, gbc, "Passengers (Min)", builder.getMinPeopleField(), row, 0);
        addLabeledField(panel, gbc, "Passengers (Max)", builder.getMaxPeopleField(), row, 2);
        addDateChooser(panel, gbc, "Start Date", builder.getStartDateChooser(), row, 4);
        addDateChooser(panel, gbc, "End Date", builder.getEndDateChooser(), row, 6);

        row++;

        addDropdownField(panel, gbc, "Currency", builder.getCurrencyField(), row, 0);
        addDropdownField(panel, gbc, "Category", builder.getCategoryField(), row, 2);
        addDropdownField(panel, gbc, "Trip Type", builder.getTripTypeField(), row, 4);
    }

    private void addActionButtons(JPanel panel, GridBagConstraints gbc, RideFilterCriteriaBuilder builder) {
        gbc.gridy++;
        gbc.gridx = 2;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.LINE_START;
        gbc.insets = new Insets(10, 5, 10, 5);

        JButton filterButton = new JButton("Apply");
        filterButton.addActionListener(e -> applyFilters(builder));
        panel.add(filterButton, gbc);

        JButton clearButton = new JButton("Clear");
        clearButton.addActionListener(e -> {
            clearFilters(builder);
            applyFilters(builder);
        });
        gbc.gridx += 2;
        panel.add(clearButton, gbc);
    }

    private void applyFilters(RideFilterCriteriaBuilder builder) {
        RideFilterCriteria criteria = builder.build();
        List<Ride> filteredRides = new RideFilterService().filterRides(rideService.findAll(), criteria);
        tableModel.setRides(filteredRides);
        table.repaint();
    }

    private void clearFilters(RideFilterCriteriaBuilder builder) {
        builder.getMinAmountField().setText("");
        builder.getMaxAmountField().setText("");
        builder.getMinDistanceField().setText("");
        builder.getMaxDistanceField().setText("");
        builder.getMinPeopleField().setText("");
        builder.getMaxPeopleField().setText("");
        builder.getCurrencyField().setSelectedIndex(-1);
        builder.getCategoryField().setSelectedIndex(-1);
        builder.getTripTypeField().setSelectedIndex(-1);
        builder.getStartDateChooser().setDate(null);
        builder.getEndDateChooser().setDate(null);
    }

    private void addLabeledField(JPanel panel, GridBagConstraints gbc, String label, JTextField textField, int row, int col) {
        gbc.gridy = row;
        gbc.gridx = col;
        panel.add(new JLabel(label), gbc);

        textField.setPreferredSize(new Dimension(100, 20));
        gbc.gridx = col + 1;
        gbc.weightx = 0.5;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(textField, gbc);
    }

    private void addDateChooser(JPanel panel, GridBagConstraints gbc, String label, JDateChooser dateChooser, int row, int col) {
        gbc.gridy = row;
        gbc.gridx = col;
        panel.add(new JLabel(label), gbc);

        gbc.gridx = col + 1;
        panel.add(dateChooser, gbc);
    }

    private void addDropdownField(JPanel panel, GridBagConstraints gbc, String label, JComboBox<?> comboBox, int row, int col) {
        gbc.gridy = row;
        gbc.gridx = col;
        panel.add(new JLabel(label), gbc);

        gbc.gridx = col + 1;
        panel.add(comboBox, gbc);
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

