package cz.muni.fi.pv168.project.repository.interfaces;

import cz.muni.fi.pv168.project.model.Currency;

import java.util.Optional;

public interface ICurrencyRepository extends Repository<Currency> {
    Optional<Currency> findByCode(String currencyCode);
    Optional<Currency> findByName(String name);
}
