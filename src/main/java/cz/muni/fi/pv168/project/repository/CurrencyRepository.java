package cz.muni.fi.pv168.project.repository;

import cz.muni.fi.pv168.project.model.Currency;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class CurrencyRepository implements ICurrencyRepository {
    @Override
    public Currency create(Currency currency) {
        return currency;
    }

    @Override
    public void update(Currency currency) {

    }

    @Override
    public void deleteById(Long currencyId) {

    }

    @Override
    public @Nullable Currency getById(Long currencyId) {
        return null;
    }

    @Override
    public List<Currency> getAll() {
        return List.of(
                new Currency(1L, "Czech koruna", "CZK", 1.0),
                new Currency(2L, "Euro", "EUR", 25.0),
                new Currency(3L, "US Dollar", "USD", 20.0)
        );
    }

    @Override
    public void deleteAll() {

    }
}
