package cz.muni.fi.pv168.project.ui.dialog;

import cz.muni.fi.pv168.project.model.enums.TripType;
import cz.muni.fi.pv168.project.model.Category;
import cz.muni.fi.pv168.project.ui.model.ComboBoxModelAdapter;
import cz.muni.fi.pv168.project.model.Currency;
import cz.muni.fi.pv168.project.ui.renderers.CategoryRenderer;
import cz.muni.fi.pv168.project.ui.renderers.CurrencyRenderer;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;



public class NewRideDialog extends EntityDialog<NewRide> {
    private final JTextField amountField = new JTextField("0.0");
    private final JTextField distanceField = new JTextField("0.0");
    private final JTextField passengersField = new JTextField("1");
    private final ComboBoxModel<Currency> currencyModel;
    private final ComboBoxModel<Category> categoryModel;
    private final ComboBoxModel<TripType> tripTypeModel = new DefaultComboBoxModel<>(TripType.values());

    private final NewRide newRide = new NewRide();

    private boolean validationOk = true;

    public NewRideDialog(ListModel<Currency> currencyListModel, ListModel<Category> categoryListModel) {
        this.currencyModel = new ComboBoxModelAdapter<>(currencyListModel);
        this.categoryModel = new ComboBoxModelAdapter<>(categoryListModel);

        createFields();
    }

    @Override
    NewRide getEntity() {
        double amount = 0;
        double distance = 0;
        int passengers = 1;
        var err = "";
        try {
            err = "Invalid number format in \"amount\" field";
            amount = Double.parseDouble(amountField.getText());
            err = "Invalid number format in \"distance\" field";
            distance = Double.parseDouble(distanceField.getText());
            err = "Invalid number format in \"passengers\" field";
            passengers = Integer.parseInt(passengersField.getText());
        }
        catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this.panel, err, "Error", JOptionPane.ERROR_MESSAGE);
            validationOk = false;
            return newRide;
        }

        if (currencyModel.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(this.panel, "All fields except category has to be filled", "Error", JOptionPane.ERROR_MESSAGE);
            validationOk = false;
            return newRide;
        }
        newRide.setAmountCurrency(amountField.isEnabled() ? amount : 0);
        newRide.setDistance(distance);
        newRide.setPassengers(passengers);
        newRide.setCurrencyType((Currency) currencyModel.getSelectedItem());
        newRide.setCategory((@Nullable Category) categoryModel.getSelectedItem());
        newRide.setTripType((TripType) tripTypeModel.getSelectedItem());
        return newRide;
    }

    private void createFields(){
        var currencyBox = new JComboBox<>(currencyModel);
        currencyBox.setRenderer(new CurrencyRenderer());

        var categoryBox = new JComboBox<>(categoryModel);
        categoryBox.setRenderer(new CategoryRenderer());

        var tripTypeBox = new JComboBox<>(tripTypeModel);
        tripTypeBox.addItemListener(new TripTypeItemListener());

        add("Amount:", amountField);
        add("Currency:", currencyBox);
        add("Distance:", distanceField);
        add("Number of passengers:", passengersField);
        add("Category:", categoryBox);
        add("Trip type", tripTypeBox);
    }

    public boolean isValidationOk() {
        return validationOk;
    }

    private class TripTypeItemListener implements ItemListener {

        @Override
        public void itemStateChanged(ItemEvent e) {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                TripType item = (TripType) e.getItem();
                amountField.setEnabled(item != TripType.Personal);
            }
        }
    }
}
