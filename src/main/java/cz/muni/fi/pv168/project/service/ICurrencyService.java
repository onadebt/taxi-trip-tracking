package cz.muni.fi.pv168.project.service;

import cz.muni.fi.pv168.project.model.CurrencyDbModel;
import cz.muni.fi.pv168.project.ui.model.Currency;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public interface ICurrencyService {

    void create(Currency currency);

    void update(Currency currency);

    void delete(int currencyId);

    @Nullable CurrencyDbModel getById(int currencyId);

    @Nullable CurrencyDbModel getByTag(String tag);

    List<CurrencyDbModel> get();
}
