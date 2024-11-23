package cz.muni.fi.pv168.project.ui.model;

import cz.muni.fi.pv168.project.model.Currency;
import cz.muni.fi.pv168.project.service.crud.CrudService;
import cz.muni.fi.pv168.project.service.interfaces.ICurrencyService;

import javax.swing.*;
import java.util.List;

public class CurrencyListModel extends AbstractListModel<Currency> {
    private final List<Currency> currencies;
    private final CrudService<Currency> currencyCrudService;

    public CurrencyListModel(CrudService<Currency> currencyCrudService) {
        this.currencyCrudService = currencyCrudService;
        this.currencies = List.copyOf(currencyCrudService.findAll());
    }
    @Override
    public int getSize() {
        return currencies.size();
    }

    @Override
    public Currency getElementAt(int index) {
        return currencies.get(index);
    }
}
