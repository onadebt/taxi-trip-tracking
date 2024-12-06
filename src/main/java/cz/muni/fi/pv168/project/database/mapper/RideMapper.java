package cz.muni.fi.pv168.project.database.mapper;

import cz.muni.fi.pv168.project.database.dao.DataAccessObject;
import cz.muni.fi.pv168.project.model.*;
import cz.muni.fi.pv168.project.database.RideDbConverter;

import javax.swing.*;

/**
 * Mapper from the {@link cz.muni.fi.pv168.project.model.RideDbModel} to {@link cz.muni.fi.pv168.project.model.Ride}.
 */
public final class RideMapper implements EntityMapper<RideDbModel, Ride> {

    private final DataAccessObject<CategoryDbModel> categoryDao;
    private final EntityMapper<CategoryDbModel, Category> categoryMapper;
    private final DataAccessObject<CurrencyDbModel> currencyDao;
    private final EntityMapper<CurrencyDbModel, Currency> currencyMapper;

    public RideMapper(
            DataAccessObject<CategoryDbModel> categoryDao,
            EntityMapper<CategoryDbModel, Category> categoryMapper,
            DataAccessObject<CurrencyDbModel> currencyDao,
            EntityMapper<CurrencyDbModel, Currency> currencyMapper) {
        this.categoryDao = categoryDao;
        this.categoryMapper = categoryMapper;
        this.currencyDao = currencyDao;
        this.currencyMapper = currencyMapper;
    }

    @Override
    public Ride mapToBusiness(RideDbModel dbRide) {
        Category category = categoryDao
                .findById(dbRide.getCategoryId())
                .map(categoryMapper::mapToBusiness)
                .orElse(null);

        Currency currency = currencyDao
                .findById(dbRide.getCurrencyId())
                .map(currencyMapper::mapToBusiness)
                .orElse(null);

        Icon icon = (category != null) ? category.getIcon() : null;

        return new Ride(
                dbRide.getRideId(),
                dbRide.getAmountCurrency(),
                currency,
                dbRide.getDistance(),
                category,
                icon,
                dbRide.getTripType(),
                dbRide.getPassengers(),
                dbRide.getCreatedDate(),
                dbRide.getUuid()
        );
    }

    @Override
    public RideDbModel mapNewEntityToDatabase(Ride entity) { return getRideEntity(entity, null);}

    @Override
    public RideDbModel mapExistingEntityToDatabase(Ride entity, Long dbId) {
        return getRideEntity(entity, dbId);
    }

    private static RideDbModel getRideEntity(Ride entity, Long dbId) {
        return new RideDbModel(
                dbId,
                entity.getAmountCurrency(),
                entity.getCurrency().getId(),
                entity.getDistance(),
                entity.getCategory() != null ? entity.getCategory().getId() : null,
                entity.getNumberOfPassengers(),
                entity.getTripType(),
                entity.getCreatedAt(),
                entity.getUuid()
        );
    }
}
