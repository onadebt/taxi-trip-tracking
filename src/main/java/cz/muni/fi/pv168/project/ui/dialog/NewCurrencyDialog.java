package cz.muni.fi.pv168.project.ui.dialog;

import cz.muni.fi.pv168.project.model.Currency;
import cz.muni.fi.pv168.project.service.validation.Validator;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class NewCurrencyDialog extends EntityDialog<Currency> {

    private final JTextField nameField = new JTextField();
    private final JTextField codeField = new JTextField();
    private final JTextField exchangeRateField = new JTextField();

    private final Currency currency;

    public NewCurrencyDialog(
            Currency currency,
            Validator<Currency> entityValidator) {
        super(Objects.requireNonNull(entityValidator));
        this.currency = currency;

        nameField.setPreferredSize(new Dimension(100, 20));
        codeField.setPreferredSize(new Dimension(35, 20));
        exchangeRateField.setPreferredSize(new Dimension(35, 20));


        setValues();
        addFields();
    }

    private void setValues() {
        nameField.setText(currency.getName());
        codeField.setText(currency.getCode());
        exchangeRateField.setText(String.valueOf(currency.getExchangeRate()));
    }

    private void addFields() {
        add("Name:", nameField);
        add("Code:", codeField);
        add("Exchange Rate:", exchangeRateField);
    }

    @Override
    Currency getEntity() {
        currency.setName(nameField.getText());
        currency.setCode(codeField.getText());
        currency.setExchangeRate(Double.parseDouble(exchangeRateField.getText()));
        return currency;
    }
}
