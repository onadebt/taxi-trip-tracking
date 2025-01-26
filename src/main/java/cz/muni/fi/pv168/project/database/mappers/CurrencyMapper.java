package cz.muni.fi.pv168.project.database.mappers;

import cz.muni.fi.pv168.project.model.Currency;
import cz.muni.fi.pv168.project.model.DbModels.CurrencyDbModel;

/**
 * Mapper from the {@link CurrencyDbModel} to {@link cz.muni.fi.pv168.project.model.Currency}.
 */
public final class CurrencyMapper implements EntityMapper<CurrencyDbModel, Currency> {

    @Override
    public Currency mapToBusiness(CurrencyDbModel dbCurrency) {
        return new Currency(
                dbCurrency.getCurrencyId(),
                dbCurrency.getName(),
                dbCurrency.getTag(),
                dbCurrency.getRate()
        );
    }

    @Override
    public CurrencyDbModel mapNewEntityToDatabase(Currency entity) {
        return getCurrencyEntity(entity, null);
    }

    @Override
    public CurrencyDbModel mapExistingEntityToDatabase(Currency entity, Long dbId) {
        return getCurrencyEntity(entity, dbId);
    }

    private static CurrencyDbModel getCurrencyEntity(Currency entity, Long dbId) {
        return new CurrencyDbModel(
                dbId,
                entity.getName(),
                entity.getCode(),
                entity.getExchangeRate()
        );
    }
}
