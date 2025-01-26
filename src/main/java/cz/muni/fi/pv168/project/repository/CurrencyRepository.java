package cz.muni.fi.pv168.project.repository;

import cz.muni.fi.pv168.project.database.dao.CurrencyDataAccessObject;
import cz.muni.fi.pv168.project.database.dao.DataStorageException;
import cz.muni.fi.pv168.project.database.mappers.EntityMapper;
import cz.muni.fi.pv168.project.model.Currency;
import cz.muni.fi.pv168.project.model.DbModels.CurrencyDbModel;
import cz.muni.fi.pv168.project.repository.interfaces.ICurrencyRepository;


import java.util.List;
import java.util.Optional;

public class CurrencyRepository implements ICurrencyRepository {
    private final CurrencyDataAccessObject currencyDao;
    private final EntityMapper<CurrencyDbModel, Currency> currencyMapper;

    public CurrencyRepository(CurrencyDataAccessObject currencyDao, EntityMapper<CurrencyDbModel, Currency> currencyMapper) {
        this.currencyDao = currencyDao;
        this.currencyMapper = currencyMapper;
    }

    @Override
    public Currency create(Currency currency) {
        return currencyMapper.mapToBusiness(currencyDao.create(currencyMapper.mapNewEntityToDatabase(currency)));
    }

    @Override
    public void update(Currency currency) {
        var existing = currencyDao.findById(currency.getId()).orElseThrow(() -> new DataStorageException("Currency of ID " + currency.getId() + " not found"));
        var updated = currencyMapper.mapExistingEntityToDatabase(currency, existing.getCurrencyId());
        currencyDao.update(updated);
    }

    @Override
    public void deleteById(Long currencyId) {
        currencyDao.deleteById(currencyId);
    }


    public Optional<Currency> findById(Long currencyId) {
        return currencyDao
                .findById(currencyId)
                .map(currencyMapper::mapToBusiness);
    }

    public Optional<Currency> findByCode(String code) {
        return currencyDao
                .findByCode(code)
                .map(currencyMapper::mapToBusiness);
    }

    public Optional<Currency> findByName(String name) {
        return currencyDao
                .findByName(name)
                .map(currencyMapper::mapToBusiness);
    }

    @Override
    public List<Currency> findAll() {
        return currencyDao.findAll().stream().map(currencyMapper::mapToBusiness).toList();
    }

    @Override
    public void deleteAll() {
        currencyDao.deleteAll();
    }
}
