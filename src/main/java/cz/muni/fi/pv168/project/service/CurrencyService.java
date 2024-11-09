package cz.muni.fi.pv168.project.service;

import cz.muni.fi.pv168.project.model.CurrencyDbModel;
import cz.muni.fi.pv168.project.ui.model.Currency;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class CurrencyService implements ICurrencyService{
    @Override
    public void create(Currency currency) {

    }

    @Override
    public void update(Currency currency) {

    }

    @Override
    public void delete(int currencyId) {

    }

    @Override
    public @Nullable CurrencyDbModel getById(int currencyId) {
        return null;
    }

    @Override
    public @Nullable CurrencyDbModel getByTag(String tag) {
        return null;
    }

    @Override
    public List<CurrencyDbModel> get() {
        return List.of();
    }
}
