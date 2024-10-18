package cz.muni.fi.pv168.project.ui.dialog;

import cz.muni.fi.pv168.project.ui.model.Category;
import cz.muni.fi.pv168.project.ui.model.ComboBoxModelAdapter;
import cz.muni.fi.pv168.project.ui.model.Currency;
import cz.muni.fi.pv168.project.ui.model.NewRide;
import cz.muni.fi.pv168.project.ui.renderers.CategoryRenderer;
import cz.muni.fi.pv168.project.ui.renderers.CurrencyRenderer;

import javax.swing.*;


public class NewRideDialog extends EntityDialog<NewRide> {
    private final JTextField amountField = new JTextField("0.0");
    private final JTextField distanceField = new JTextField("0.0");
    private final ComboBoxModel<Currency> currencyModel;
    private final ComboBoxModel<Category> categoryModel;

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
        newRide.setAmountCurrency(amount);
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


        add("Amount:", amountField);
        add("Currency:", currencyBox);
        add("Distance:", distanceField);
        add("Category:", categoryBox);
    }
}
