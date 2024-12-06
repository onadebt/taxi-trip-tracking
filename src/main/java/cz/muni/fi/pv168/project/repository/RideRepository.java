package cz.muni.fi.pv168.project.repository;

import cz.muni.fi.pv168.project.database.dao.DataStorageException;
import cz.muni.fi.pv168.project.database.dao.RideDao;
import cz.muni.fi.pv168.project.database.mapper.EntityMapper;
import cz.muni.fi.pv168.project.model.Category;
import cz.muni.fi.pv168.project.model.Currency;
import cz.muni.fi.pv168.project.model.Ride;
import cz.muni.fi.pv168.project.model.RideDbModel;
import cz.muni.fi.pv168.project.model.enums.DistanceConversionHelper;
import cz.muni.fi.pv168.project.model.enums.DistanceUnit;
import cz.muni.fi.pv168.project.ui.tabs.Settings;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


public class RideRepository implements Repository<Ride> {
    private Repository<Currency> currencyRepository;
    private Repository<Category> categoryRepository;
    private RideDao rideDao;
    private EntityMapper<RideDbModel, Ride> rideMapper;

    public RideRepository(Repository<Currency> currencyRepository, Repository<Category> categoryRepository
    , RideDao rideDao, EntityMapper<RideDbModel, Ride> rideMapper) {
        this.currencyRepository = currencyRepository;
        this.categoryRepository = categoryRepository;
        this.rideDao = rideDao;
        this.rideMapper = rideMapper;
    }



    public void updateAll(List<Ride> rides) {
        for (Ride ride : rides) {
            update(ride);
        }
    }

    public void deleteByUUID(UUID uuid) {

    }

    @Override
    public List<Ride> findAll() {
        return rideDao.findAll().stream().map(rideMapper::mapToBusiness).toList();
    }

    @Override
    public Ride create(Ride newEntity) {
        return rideMapper.mapToBusiness(rideDao.create(rideMapper.mapNewEntityToDatabase(newEntity)));
    }

    @Override
    public void update(Ride entity) {
        var existing = rideDao.findById(entity.getId()).orElseThrow(() -> new DataStorageException("Ride of ID " + entity.getId() + " not found"));
        var updated = rideMapper.mapExistingEntityToDatabase(entity, existing.getRideId());
        rideDao.update(updated);
    }

    @Override
    public void deleteById(Long id) {
        rideDao.deleteById(id);
    }

    @Override
    public Optional<Ride> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public void deleteAll() {

    }


    public Ride findByUUID(UUID rideUUID) {
        return null;
    }



    public void recalculateDistances(DistanceUnit newUnit) {
        if (newUnit == Settings.getDefaultDistanceUnit()) {
            return;
        }

        double conversionFactor = (newUnit == DistanceUnit.Kilometer)
                ? DistanceConversionHelper.MILES_TO_KILOMETERS_FACTOR
                : DistanceConversionHelper.KILOMETERS_TO_MILES_FACTOR;


        List<Ride> rides = findAll();
        for (Ride ride : rides) {
                ride.setDistance(ride.getDistance() * conversionFactor);
        }

        updateAll(rides);
    }
}
