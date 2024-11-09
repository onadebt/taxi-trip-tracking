package cz.muni.fi.pv168.project.service;

import cz.muni.fi.pv168.project.model.CurrencyDbModel;
import cz.muni.fi.pv168.project.ui.model.Currency;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public interface ICurrencyService {
    /**
     * Create new currency according to model
     * @param currency
     */
    void create(Currency currency);

    /**
     * Update currency according to model
     * @param currency
     */
    void update(Currency currency);

    /**
     * Delete currency with specified id
     * @param currencyId
     */
    void delete(int currencyId);

    /**
     * Get currency by given id
     * @param currencyId
     * @return currency, null if not found
     */
    @Nullable CurrencyDbModel getById(int currencyId);

    @Nullable CurrencyDbModel getByTag(String tag);

    /**
     * Get all currencies
     * @return currencies
     */
    List<CurrencyDbModel> get();
}
