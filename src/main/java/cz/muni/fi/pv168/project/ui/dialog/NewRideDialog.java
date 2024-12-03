package cz.muni.fi.pv168.project.ui.dialog;

import cz.muni.fi.pv168.project.model.Ride;
import cz.muni.fi.pv168.project.model.enums.TripType;
import cz.muni.fi.pv168.project.model.Category;
import cz.muni.fi.pv168.project.ui.model.ComboBoxModelAdapter;
import cz.muni.fi.pv168.project.model.Currency;
import cz.muni.fi.pv168.project.ui.renderers.CategoryNameIconRenderer;
import cz.muni.fi.pv168.project.ui.renderers.CurrencyRenderer;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;



public class NewRideDialog extends EntityDialog<Ride> {
    private final JTextField amountField = new JTextField("0.0");
    private final JTextField distanceField = new JTextField("0.0");
    private final JTextField passengersField = new JTextField("1");
    private final ComboBoxModel<Currency> currencyModel;
    private final ComboBoxModel<Category> categoryModel;
    private final ComboBoxModel<TripType> tripTypeModel = new DefaultComboBoxModel<>(TripType.values());

    private final Ride ride = new Ride();

    private boolean validationOk = true;
    private JComboBox<Currency> currencyBox;

    public NewRideDialog(ListModel<Currency> currencyListModel, ListModel<Category> categoryListModel) {
        this.currencyModel = new ComboBoxModelAdapter<>(currencyListModel);
        this.categoryModel = new ComboBoxModelAdapter<>(categoryListModel);

        createFields();
    }

    @Override
    Ride getEntity() {
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
            return ride;
        }

        if (currencyModel.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(this.panel, "All available fields except category has to be filled", "Error", JOptionPane.ERROR_MESSAGE);
            validationOk = false;
            return ride;
        }

        ride.setAmountCurrency(amountField.isEnabled() ? amount : 0);
        ride.setDistance(distance);
        ride.setNumberOfPassengers(passengers);
        ride.setCurrency((Currency) currencyModel.getSelectedItem());
        ride.setCategory((@Nullable Category) categoryModel.getSelectedItem());
        ride.setTripType((TripType) tripTypeModel.getSelectedItem());
        return ride;
    }

    private void createFields(){
        currencyBox = new JComboBox<>(currencyModel);
        currencyBox.setRenderer(new CurrencyRenderer());

        var categoryBox = new JComboBox<>(categoryModel);
        categoryBox.setRenderer(new CategoryNameIconRenderer());

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
                amountField.setEnabled(item == TripType.Paid);
                currencyModel.setSelectedItem(null);

                if (!amountField.isEnabled()) {
                    amountField.setText("0.0");
                }
            }
        }
    }
}
