package cz.muni.fi.pv168.project.ui.action;

import cz.muni.fi.pv168.project.model.Currency;
import cz.muni.fi.pv168.project.service.validation.Validator;
import cz.muni.fi.pv168.project.ui.dialog.NewCurrencyDialog;
import cz.muni.fi.pv168.project.ui.model.CurrencyTableModel;

import javax.swing.*;
import java.awt.event.ActionEvent;

import java.math.BigDecimal;
import java.util.Objects;

public class NewCurrencyAction extends AbstractAction {

    private final JTable currencyTable;
    private final Validator<Currency> currencyValidator;

    public NewCurrencyAction(
            JTable currencyTable,
            Validator<Currency> currencyValidator) {
        super("Add");
        this.currencyValidator = Objects.requireNonNull(currencyValidator);
        this.currencyTable = currencyTable;
        putValue(SHORT_DESCRIPTION, "Adds new currency");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        var currencyTableModel = (CurrencyTableModel) currencyTable.getModel();
        var dialog = new NewCurrencyDialog(createPrefilledCurrency(), currencyValidator);
        dialog.show(currencyTable, "Add Currency")
                .ifPresent(currencyTableModel::addRow);
    }

    private Currency createPrefilledCurrency() {
        return new Currency(
                null,
                "",
                "",
                new BigDecimal("1.0")
        );
    }
}
