package cz.muni.fi.pv168.project.repository;

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

    public RideRepository(Repository<Currency> currencyRepository, Repository<Category> categoryRepository) {
        this.currencyRepository = currencyRepository;
        this.categoryRepository = categoryRepository;
    }



    public void updateAll(List<Ride> rides) {

    }

    public void deleteByUUID(UUID uuid) {

    }

    @Override
    public List<Ride> findAll() {
        return List.of();
    }

    @Override
    public Ride create(Ride newEntity) {
        return null;
    }

    @Override
    public void update(Ride entity) {

    }

    @Override
    public void deleteById(Long id) {

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
