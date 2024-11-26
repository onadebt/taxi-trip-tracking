package cz.muni.fi.pv168.project.ui.action;



import cz.muni.fi.pv168.project.model.Currency;
import cz.muni.fi.pv168.project.service.validation.Validator;
import cz.muni.fi.pv168.project.ui.dialog.NewCurrencyDialog;
import cz.muni.fi.pv168.project.ui.model.CurrencyTableModel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.Objects;

public final class EditCurrencyAction extends AbstractAction {

    private final JTable currencyTable;
    private final Validator<Currency> currencyValidator;

    public EditCurrencyAction(
            JTable currencyTable,
            Validator<Currency> currencyValidator) {
        super("Edit");
        this.currencyTable = currencyTable;
        this.currencyValidator = Objects.requireNonNull(currencyValidator);
        putValue(SHORT_DESCRIPTION, "Edits selected currency");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        int[] selectedRows = currencyTable.getSelectedRows();
        if (selectedRows.length != 1) {
            throw new IllegalStateException("Invalid selected rows count (must be 1): " + selectedRows.length);
        }
        if (currencyTable.isEditing()) {
            currencyTable.getCellEditor().cancelCellEditing();
        }
        var currencyTableModel = (CurrencyTableModel) currencyTable.getModel();
        int modelRow = currencyTable.convertRowIndexToModel(selectedRows[0]);
        var currency = currencyTableModel.getEntity(modelRow);
        var dialog = new NewCurrencyDialog(currency, currencyValidator);
        dialog.show(currencyTable, "Edit Employee")
                .ifPresent(currencyTableModel::updateRow);
    }
}
