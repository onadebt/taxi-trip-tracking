package cz.muni.fi.pv168.project.service;

import cz.muni.fi.pv168.project.model.Currency;
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
    void delete(Long currencyId);

    /**
     * Get currency by given id
     * @param currencyId
     * @return currency, null if not found
     */
    @Nullable Currency getById(Long currencyId);

    @Nullable Currency getByTag(String tag);

    /**
     * Get all currencies
     * @return currencies
     */
    List<Currency> getAll();
}
