package cz.muni.fi.pv168.project.repository;

import cz.muni.fi.pv168.project.database.dao.CurrencyDataAccessObject;
import cz.muni.fi.pv168.project.database.dao.DataStorageException;
import cz.muni.fi.pv168.project.database.mapper.EntityMapper;
import cz.muni.fi.pv168.project.model.Currency;
import cz.muni.fi.pv168.project.model.CurrencyDbModel;
import org.jetbrains.annotations.Nullable;

import java.util.List;

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

    @Override
    public @Nullable Currency getById(Long currencyId) {
        return currencyDao
                .findById(currencyId)
                .map(currencyMapper::mapToBusiness).orElse(null);
    }
    @Override
    public @Nullable Currency getByCode(String code) {
        return currencyDao
                .findByCode(code)
                .map(currencyMapper::mapToBusiness).orElse(null);
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
        currencyDao.deleteAll();
    }
}
