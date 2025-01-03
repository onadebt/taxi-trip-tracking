package cz.muni.fi.pv168.project.ui.model;

import cz.muni.fi.pv168.project.model.Currency;
import cz.muni.fi.pv168.project.repository.ICurrencyRepository;
import cz.muni.fi.pv168.project.service.crud.CrudService;
import cz.muni.fi.pv168.project.service.interfaces.ICurrencyService;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class CurrencyTableModel extends AbstractTableModel implements EntityTableModel<Currency> {

    private List<Currency> currencies;
    private final ICurrencyService currencyService;

    private final List<Column<Currency, ?>> columns = List.of(
            Column.editable("Name", String.class, Currency::getName, Currency::setName),
            Column.editable("Code", String.class, Currency::getCode, Currency::setCode),
            Column.editable("Exchange Rate", BigDecimal.class, Currency::getExchangeRate, Currency::setExchangeRate)
    );

    public CurrencyTableModel(ICurrencyService currencyService) {
        this.currencyService = currencyService;
        this.currencies = new ArrayList<>(currencyService.findAll());
    }

    @Override
    public Currency getEntity(int rowIndex) {
        return currencies.get(rowIndex);
    }

    @Override
    public int getRowCount() {
        return currencies.size();
    }

    @Override
    public int getColumnCount() {
        return columns.size();
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        var currency = getEntity(rowIndex);
        return columns.get(columnIndex).getValue(currency);
    }

    public void deleteRow(int rowIndex) {
        try {
            var currencyToBeDeleted = getEntity(rowIndex);
            currencyService.deleteById(currencyToBeDeleted.getId());
            currencies.remove(rowIndex);
            fireTableRowsDeleted(rowIndex, rowIndex);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void addRow(Currency currency) {
        try {
            currencyService.create(currency).intoException();
            int newRowIndex = currencies.size();
            currencies.add(currency);
            fireTableRowsInserted(newRowIndex, newRowIndex);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void updateRow(Currency currency) {
        try {
            currencyService.update(currency).intoException();
            int rowIndex = currencies.indexOf(currency);
            fireTableRowsUpdated(rowIndex, rowIndex);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public String getColumnName(int columnIndex) {
        return columns.get(columnIndex).getName();
    }

    public void refresh() {
        this.currencies = new ArrayList<>(currencyService.findAll());
        fireTableDataChanged();
    }
}
