package cz.muni.fi.pv168.project.ui.model;

import cz.muni.fi.pv168.project.model.Currency;
import cz.muni.fi.pv168.project.service.ICurrencyService;

import javax.swing.*;
import java.util.List;

public class CurrencyListModel extends AbstractListModel<Currency> {
    private final List<Currency> currencies;
    private final ICurrencyService currencyService;

    public CurrencyListModel(ICurrencyService currencyService) {
        this.currencyService = currencyService;
        this.currencies = List.copyOf(currencyService.getAll());
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
