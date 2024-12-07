package cz.muni.fi.pv168.project.service;

import cz.muni.fi.pv168.project.database.RideDbConverter;
import cz.muni.fi.pv168.project.model.Category;
import cz.muni.fi.pv168.project.model.Currency;
import cz.muni.fi.pv168.project.model.Ride;
import cz.muni.fi.pv168.project.model.RideDbModel;
import cz.muni.fi.pv168.project.model.enums.DistanceUnit;
import cz.muni.fi.pv168.project.model.enums.TripType;
import cz.muni.fi.pv168.project.model.exception.ValidationException;
import cz.muni.fi.pv168.project.repository.IRideRepository;
import cz.muni.fi.pv168.project.repository.Repository;
import cz.muni.fi.pv168.project.repository.RideRepository;
import cz.muni.fi.pv168.project.service.interfaces.ICategoryService;
import cz.muni.fi.pv168.project.service.interfaces.ICurrencyService;
import cz.muni.fi.pv168.project.service.interfaces.IRideService;
import cz.muni.fi.pv168.project.service.mockData.RidesTestData;
import cz.muni.fi.pv168.project.ui.resources.Icons;
import org.jetbrains.annotations.Nullable;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class RideService implements IRideService {
    private final ICategoryService categoryService;
    private final ICurrencyService currencyService;
    private final RideRepository rideRepository;

    public RideService(ICategoryService categoryService, ICurrencyService currencyService, RideRepository rideRepository) {
        this.rideRepository = rideRepository;
        this.categoryService = categoryService;
        this.currencyService = currencyService;
    }


//    @Override
//    public void create(RideDbModel ride) {
//        if (!isValid(ride)) {
//            // TODO - logging, maybe exception?
//            throw new ValidationException("Created ride is not valid");
//        }
//        rideRepository.create(ride);
//    }


    @Override
    public void create(Ride ride) {
        /*
        validate(ride);
        RideDbModel rideDbModel = RideDbConverter.toDbModel(ride);
        rideRepository.create(ride);
        */
    }

    @Override
    public void update(Ride ride) {
        validate(ride);
        RideDbModel rideDbModel = RideDbConverter.toDbModel(ride);
        rideRepository.update(ride);
    }

    @Override
    public void deleteById(Long rideId) {
        rideRepository.deleteById(rideId);
    }

    @Override
    public void deleteAll() {

    }

    @Override
    public @Nullable Ride getById(Long rideId) {
        return null;
    }

    @Override
    public RideDbModel getByUUID(Long rideUUID) {
        return null;
    }

    @Override
    public void recalculateDistances(DistanceUnit newUnit) {
        rideRepository.recalculateDistances(newUnit);
    }


    @Override
    public List<Ride> findAll() {
        return rideRepository.findAll();
    }

    @Override
    public void validate(Ride ride) throws ValidationException {
        String error = "";
        if (ride.getAmountCurrency() < 0) {
            error = "Amount of money obtained cannot be negative";
        }

        if (ride.getDistance() < 0) {
            error = "Distance driven cannot be negative";
        }

        if (ride.getNumberOfPassengers() < 0) {
            error = "Number of passengers driven cannot be negative";
        }
        if (!error.isEmpty()) {
            throw new ValidationException(error);
        }
    }
}
