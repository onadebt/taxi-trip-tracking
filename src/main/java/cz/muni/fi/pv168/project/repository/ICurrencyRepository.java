package cz.muni.fi.pv168.project.repository;

import cz.muni.fi.pv168.project.model.CurrencyDbModel;

import java.util.List;

public interface ICurrencyRepository {
    void create(CurrencyDbModel currency);
    void update(CurrencyDbModel currencyDbModel);
    void delete(int currencyId);
    CurrencyDbModel getById();
    List<CurrencyDbModel> get();
}
