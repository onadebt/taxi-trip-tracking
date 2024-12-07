package cz.muni.fi.pv168.project.repository;

import cz.muni.fi.pv168.project.model.Currency;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;

public interface ICurrencyRepository extends Repository<Currency> {
    Optional<Currency> findByCode(String currencyCode);
    Optional<Currency> findByName(String name);
}
