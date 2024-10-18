package cz.muni.fi.pv168.project.ui.model;

import javax.swing.*;
import java.util.List;

public class CurrencyListModel extends AbstractListModel<Currency> {
    private final List<Currency> currencies;

    public CurrencyListModel(List<Currency> currencies) {
        this.currencies = currencies;
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
