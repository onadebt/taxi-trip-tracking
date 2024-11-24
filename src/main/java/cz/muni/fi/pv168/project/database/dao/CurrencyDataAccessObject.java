package cz.muni.fi.pv168.project.database.dao;

import cz.muni.fi.pv168.project.model.Currency;
import cz.muni.fi.pv168.project.model.CurrencyDbModel;

import java.util.Optional;

public interface CurrencyDataAccessObject extends DataAccessObject<CurrencyDbModel> {
    Optional<CurrencyDbModel> findByCode(String code);
}
