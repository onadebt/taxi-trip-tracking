package cz.muni.fi.pv168.project.repository;

import cz.muni.fi.pv168.project.model.Ride;
import cz.muni.fi.pv168.project.model.RideDbModel;
import cz.muni.fi.pv168.project.model.enums.DistanceConversionHelper;
import cz.muni.fi.pv168.project.model.enums.DistanceUnit;
import cz.muni.fi.pv168.project.ui.tabs.Settings;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.UUID;


public class RideRepository implements IRideRepository {
    private ICurrencyRepository currencyRepository;
    private ICategoryRepository categoryRepository;

    public RideRepository(ICurrencyRepository currencyRepository, ICategoryRepository categoryRepository) {
        this.currencyRepository = currencyRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public void create(RideDbModel ride) {

    }

    @Override
    public void update(RideDbModel ride) {

    }

    public void updateAll(List<RideDbModel> rides) {

    }

    @Override
    public void deleteByUUID(UUID rideUUID) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public @Nullable Ride getById(Long rideId) {
        return null;
    }

    @Override
    public RideDbModel getByUUID(UUID rideUUID) {
        return null;
    }


    @Override
    public List<RideDbModel> getAll() {
        return null;
    }

    public void recalculateDistances(DistanceUnit newUnit) {
        if (newUnit == Settings.getDefaultDistanceUnit()) {
            return;
        }

        double conversionFactor = (newUnit == DistanceUnit.Kilometer)
                ? DistanceConversionHelper.MILES_TO_KILOMETERS_FACTOR
                : DistanceConversionHelper.KILOMETERS_TO_MILES_FACTOR;


        List<RideDbModel> rides = getAll();
        for (RideDbModel ride : rides) {
                ride.setDistance(ride.getDistance() * conversionFactor);
        }

        updateAll(rides);
    }
}
