package cz.muni.fi.pv168.project.service;

import cz.muni.fi.pv168.project.model.RideDbModel;
import cz.muni.fi.pv168.project.model.TripType;
import cz.muni.fi.pv168.project.ui.model.NewRide;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;

public class RideService implements IRideService {
    private final ICategoryService categoryService;
    private final ICurrencyService currencyService;

    public RideService(ICategoryService categoryService, ICurrencyService currencyService) {
        this.categoryService = categoryService;
        this.currencyService = currencyService;
    }

    @Override
    public void create(RideDbModel ride) {

    }

    @Override
    public void update(RideDbModel ride) {

    }

    @Override
    public void delete(int rideId) {

    }

    @Override
    public RideDbModel getById(int rideId) {
        return null;
    }

    @Override
    public List<RideDbModel> get() {
        return TestRides.get();
    }

    @Override
    public boolean isValid(RideDbModel ride) {
        return ride.getDistance() > 0
                && ride.getAmountCurrency() >= 0
                && ride.getRideId() > 0
                && ride.getPassengers() >= 0
                && (ride.getCategoryId() <= 0 || categoryService.getById(ride.getCategoryId()) != null)
                && (ride.getCurrencyId() > 0 && currencyService.getById(ride.getCurrencyId()) != null);

    }

    private static class TestRides {
        public static List<RideDbModel> get() {
            return Arrays.asList(
                    new RideDbModel(1, 150, 1, 0.80, 1, 2,
                            TripType.Paid, Instant.now()),
                    new RideDbModel(2, 1500, 1, 25.2, 1, 4,
                            TripType.Paid, Instant.ofEpochSecond(1000000000L))
            );
        }
    }
}


