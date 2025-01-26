package cz.muni.fi.pv168.project.database.dao;

import cz.muni.fi.pv168.project.model.DbModels.CurrencyDbModel;

import java.util.Optional;

public interface CurrencyDataAccessObject extends DataAccessObject<CurrencyDbModel> {
    Optional<CurrencyDbModel> findByCode(String code);
    Optional<CurrencyDbModel> findByName(String name);
}
