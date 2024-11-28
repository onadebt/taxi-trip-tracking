package cz.muni.fi.pv168.project.ui.model;

import cz.muni.fi.pv168.project.model.Currency;
import cz.muni.fi.pv168.project.service.crud.CrudService;

import javax.swing.table.AbstractTableModel;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CurrencyTableModel extends AbstractTableModel implements EntityTableModel<Currency> {

    private List<Currency> currencies;
    private final CrudService<Currency> currencyCrudService;

    private final List<Column<Currency, ?>> columns = List.of(
            Column.editable("Name", String.class, Currency::getName, Currency::setName),
            Column.editable("Code", String.class, Currency::getCode, Currency::setCode),
            Column.editable("Exchange Rate", Double.class, Currency::getExchangeRate, Currency::setExchangeRate)
    );

    public CurrencyTableModel(CrudService<Currency> currencyCrudService) {
        this.currencyCrudService = currencyCrudService;
        this.currencies = new ArrayList<>(currencyCrudService.findAll());
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
        var currencyToBeDeleted = getEntity(rowIndex);
        currencyCrudService.deleteById(currencyToBeDeleted.getId());
        currencies.remove(rowIndex);
        fireTableRowsDeleted(rowIndex, rowIndex);
    }

    public void addRow(Currency currency) {
        currencyCrudService.create(currency)
                .intoException();
        int newRowIndex = currencies.size();
        currencies.add(currency);
        fireTableRowsInserted(newRowIndex, newRowIndex);
    }

    public void updateRow(Currency currency) {
        currencyCrudService.update(currency)
                .intoException();
        int rowIndex = currencies.indexOf(currency);
        fireTableRowsUpdated(rowIndex, rowIndex);
    }

    @Override
    public String getColumnName(int columnIndex) {
        return columns.get(columnIndex).getName();
    }

    public void refresh() {
        this.currencies = new ArrayList<>(currencyCrudService.findAll());
        fireTableDataChanged();
    }
}
