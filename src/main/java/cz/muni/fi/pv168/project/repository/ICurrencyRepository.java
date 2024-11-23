package cz.muni.fi.pv168.project.repository;

import cz.muni.fi.pv168.project.model.Currency;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public interface ICurrencyRepository {
    Currency create(Currency currency);
    void update(Currency currency);
    void deleteById(Long currencyId);
    @Nullable Currency getById(Long currencyId);
    List<Currency> getAll();
    void deleteAll();
}
