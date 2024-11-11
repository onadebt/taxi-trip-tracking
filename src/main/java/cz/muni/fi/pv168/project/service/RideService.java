package cz.muni.fi.pv168.project.service;

import cz.muni.fi.pv168.project.model.Ride;
import cz.muni.fi.pv168.project.repository.IRideRepository;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class RideService implements IRideService {
    private final ICategoryService categoryService;
    private final ICurrencyService currencyService;
    private final IRideRepository rideRepository;

    public RideService(ICategoryService categoryService, ICurrencyService currencyService, IRideRepository rideRepository) {
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

    }

    @Override
    public void update(Ride ride) {

    }

    @Override
    public void deleteById(Long rideId) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public @Nullable Ride getById(Long rideId) {
        return null;
    }

    @Override
    public List<Ride> findAll() {
        return List.of();
    }


//    private static class TestRides {
//        public static List<RideDbModel> get() {
//            return Arrays.asList(
//                    new RideDbModel(1, 30, 1, 0.80, 1, 2,
//                            TripType.Paid, Instant.now(), UUID.randomUUID()),
//                    new RideDbModel(2, 100, 2, 25.2, 2, 4,
//                            TripType.Paid, Instant.ofEpochSecond(1000000000L), UUID.randomUUID()),
//                    new RideDbModel(3, 800, 3, 12.2, null, 3,
//                            TripType.Paid, Instant.now(), UUID.randomUUID())
//            );
//        }
//    }
//}

}
