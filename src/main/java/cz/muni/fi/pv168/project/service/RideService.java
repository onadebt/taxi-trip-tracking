package cz.muni.fi.pv168.project.service;

import cz.muni.fi.pv168.project.model.RideDbModel;
import cz.muni.fi.pv168.project.model.TripType;
import cz.muni.fi.pv168.project.repository.IRideRepository;
import cz.muni.fi.pv168.project.ui.model.NewRide;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class RideService implements IRideService {
    private final ICategoryService categoryService;
    private final ICurrencyService currencyService;
    private final IRideRepository rideRepository;

    public RideService(ICategoryService categoryService, ICurrencyService currencyService, IRideRepository rideRepository) {
        this.rideRepository = rideRepository;
        this.categoryService = categoryService;
        this.currencyService = currencyService;
    }

    @Override
    public void create(NewRide newRide) {
        RideDbModel ride = new RideDbModel(newRide);
        create(ride);
    }
    @Override
    public void create(RideDbModel ride) {
        if (!isValid(ride)) {
            // TODO - logging, maybe exception?
        }
        rideRepository.create(ride);
    }

    @Override
    public void update(NewRide ride) {

    }

    @Override
    public void update(RideDbModel ride) {

    }

    @Override
    public void delete(int rideId) {
        rideRepository.delete(rideId);
    }

    @Override
    public void deleteAll() {
        rideRepository.deleteAll();
    }

    @Override
    public RideDbModel getById(int rideId) {
        return rideRepository.getById(rideId);
    }

    @Override
    public List<RideDbModel> get() {
        return TestRides.get();
    }

    public RideDbModel getByUuid(UUID uuid) {
        return rideRepository.getByUuid(uuid);
    }

    @Override
    public boolean isValid(RideDbModel ride) {
        return ride.getDistance() > 0
                && ride.getAmountCurrency() >= 0
                && ride.getRideId() >= 0
                && ride.getPassengers() >= 0
                && (ride.getCategoryId() == null || (ride.getCategoryId() > 0 && categoryService.getById(ride.getCategoryId()) != null))
                && (ride.getCurrencyId() > 0 && currencyService.getById(ride.getCurrencyId()) != null)
                && ride.getUuid() != null;

    }

    private static class TestRides {
        public static List<RideDbModel> get() {
            return Arrays.asList(
                    new RideDbModel(1, 30, 1, 0.80, 1, 2,
                            TripType.Paid, Instant.now(), UUID.randomUUID()),
                    new RideDbModel(2, 100, 2, 25.2, 2, 4,
                            TripType.Paid, Instant.ofEpochSecond(1000000000L), UUID.randomUUID()),
                    new RideDbModel(3, 800, 3, 12.2, null, 3,
                            TripType.Paid, Instant.now(), UUID.randomUUID())
            );
        }
    }
}


