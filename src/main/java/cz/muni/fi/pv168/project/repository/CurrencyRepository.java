package cz.muni.fi.pv168.project.repository;

import cz.muni.fi.pv168.project.model.Currency;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class CurrencyRepository implements ICurrencyRepository {
    @Override
    public void create(Currency currency) {

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
        return List.of();
    }
}
