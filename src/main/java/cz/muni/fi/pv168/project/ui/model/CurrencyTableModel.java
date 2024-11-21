package cz.muni.fi.pv168.project.ui.model;

import cz.muni.fi.pv168.project.model.Currency;
import cz.muni.fi.pv168.project.service.interfaces.ICurrencyService;

import javax.swing.*;
import javax.swing.event.ListDataListener;
import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

public class CurrencyTableModel extends AbstractTableModel implements ListModel<Currency> {
    private List<Currency> currencies;
    private final ICurrencyService currencyService;
    private final List<ListDataListener> listDataListeners;

    public CurrencyTableModel(ICurrencyService currencyService) {
        this.currencyService = currencyService;
        this.currencies = currencyService.getAll();
        this.listDataListeners = new ArrayList<>();
    }

    @Override
    public int getRowCount() {
        return currencies.size();
    }

    @Override
    public int getColumnCount() {
        return 3; // Name, Code, Exchange Rate
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Currency currency = currencies.get(rowIndex);
        return switch (columnIndex) {
            case 0 -> currency.getName();
            case 1 -> currency.getCode();
            case 2 -> currency.getExchangeRate();
            default -> null;
        };
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return true; // Allow editing of all columns
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        Currency currency = currencies.get(rowIndex);
        switch (columnIndex) {
            case 0:
                currency.setName((String) aValue);
                break;
            case 1:
                currency.setCode((String) aValue);
                break;
            case 2:
                currency.setExchangeRate(Double.parseDouble((String) aValue));
                break;
        }
        currencyService.update(currency);
        fireTableCellUpdated(rowIndex, columnIndex);
    }

    @Override
    public String getColumnName(int columnIndex) {
        return switch (columnIndex) {
            case 0 -> "Name";
            case 1 -> "Code";
            case 2 -> "Exchange Rate";
            default -> "";
        };
    }

    @Override
    public int getSize() {
        return currencies.size();
    }

    @Override
    public Currency getElementAt(int index) {
        return currencies.get(index);
    }

    public void removeRow(int row) {
        if (row >= 0 && row < currencies.size()) {
            Currency currency = currencies.get(row);
            currencyService.delete(currency.getId());
            currencies.remove(row);
            fireTableRowsDeleted(row, row); // Notify the table that the row was removed
            fireListDataChanged(); // Notify listeners that the data changed
        }
    }

    public void updateCurrencyList(List<Currency> updatedCurrencies) {
        this.currencies = updatedCurrencies;
        fireTableDataChanged(); // Notify the table that the data has changed
        fireListDataChanged(); // Notify listeners that the data changed
    }

    // Notify listeners about data change in the list model
    private void fireListDataChanged() {
        for (ListDataListener listener : listDataListeners) {
            listener.contentsChanged(new javax.swing.event.ListDataEvent(this, javax.swing.event.ListDataEvent.CONTENTS_CHANGED, 0, getSize()));
        }
    }

    // ListModel Listener Methods
    @Override
    public void addListDataListener(ListDataListener listDataListener) {
        listDataListeners.add(listDataListener); // Add the listener to the list
    }

    @Override
    public void removeListDataListener(ListDataListener listDataListener) {
        listDataListeners.remove(listDataListener); // Remove the listener from the list
    }
}
