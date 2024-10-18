package cz.muni.fi.pv168.project.ui.dialog;

import cz.muni.fi.pv168.project.model.TripType;
import cz.muni.fi.pv168.project.ui.model.Category;
import cz.muni.fi.pv168.project.ui.model.ComboBoxModelAdapter;
import cz.muni.fi.pv168.project.ui.model.Currency;
import cz.muni.fi.pv168.project.ui.model.NewRide;
import cz.muni.fi.pv168.project.ui.renderers.CategoryRenderer;
import cz.muni.fi.pv168.project.ui.renderers.CurrencyRenderer;

import javax.swing.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;


public class NewRideDialog extends EntityDialog<NewRide> {
    private final JTextField amountField = new JTextField("0.0");
    private final JTextField distanceField = new JTextField("0.0");
    private final ComboBoxModel<Currency> currencyModel;
    private final ComboBoxModel<Category> categoryModel;
    private final ComboBoxModel<TripType> tripTypeModel = new DefaultComboBoxModel<>(TripType.values());

    private final NewRide newRide = new NewRide();

    public NewRideDialog(ListModel<Currency> currencyListModel, ListModel<Category> categoryListModel) {
        this.currencyModel = new ComboBoxModelAdapter<>(currencyListModel);
        this.categoryModel = new ComboBoxModelAdapter<>(categoryListModel);

        createFields();
    }

    @Override
    NewRide getEntity() {
        double amount = 0;
        double distance = 0;
        try {
            amount = Double.parseDouble(amountField.getText());
            distance = Double.parseDouble(distanceField.getText());
        } catch (NumberFormatException e) {
            // TODO - Proper validation on frontend
        }
        newRide.setAmountCurrency(amountField.isEnabled() ? amount : 0);
        newRide.setDistance(distance);
        newRide.setCurrencyType((Currency) currencyModel.getSelectedItem());
        newRide.setCategory((Category) categoryModel.getSelectedItem());
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
        add("Category:", categoryBox);
        add("Trip type", tripTypeBox);
    }

    private class TripTypeItemListener implements ItemListener {

        @Override
        public void itemStateChanged(ItemEvent e) {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                TripType item = (TripType) e.getItem();
                if (item == TripType.Personal) {
                    amountField.setEnabled(false);
                } else {
                    amountField.setEnabled(true);
                }
            }
        }
    }

}
