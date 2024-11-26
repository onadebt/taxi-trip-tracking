package cz.muni.fi.pv168.project.ui.action;

import cz.muni.fi.pv168.project.ui.model.CurrencyTableModel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.Arrays;
import java.util.Comparator;

public final class DeleteCurrencyAction extends AbstractAction {

    private final JTable currencyTable;

    public DeleteCurrencyAction(JTable currencyTable) {
        super("Delete");
        this.currencyTable = currencyTable;
        putValue(SHORT_DESCRIPTION, "Deletes selected currencies");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        var currencyTableModel = (CurrencyTableModel) currencyTable.getModel();
        Arrays.stream(currencyTable.getSelectedRows())
                // view row index must be converted to model row index
                .map(currencyTable::convertRowIndexToModel).boxed()
                // We need to delete rows in descending order to not change index of rows
                // which are not deleted yet
                .sorted(Comparator.reverseOrder()).forEach(currencyTableModel::deleteRow);
    }
}
