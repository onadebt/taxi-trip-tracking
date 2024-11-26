package cz.muni.fi.pv168.project.database;

import cz.muni.fi.pv168.project.model.*;
import org.jetbrains.annotations.Nullable;

public class RideDbConverter {

    // Converts a Ride object to RideDbModel
    public static RideDbModel toDbModel(Ride ride) {
        RideDbModel rideDb = new RideDbModel();
        rideDb.setRideId(ride.getId());
        rideDb.setAmountCurrency(ride.getAmountCurrency());
        rideDb.setCurrencyId(ride.getCurrency() != null ? ride.getCurrency().getId() : null);
        rideDb.setDistance(ride.getDistance());
        rideDb.setCategoryId(ride.getCategory() != null ? ride.getCategory().getId() : null);
        rideDb.setPassengers(ride.getNumberOfPassengers());
        rideDb.setTripType(ride.getTripType());
        rideDb.setCreatedDate(ride.getCreatedAt());
        rideDb.setUuid(ride.getUuid());
        return rideDb;
    }

    // Converts a RideDbModel object to Ride
    public static Ride fromDbModel(RideDbModel rideDb, Currency currency, @Nullable Category category) {
        return new Ride(
                rideDb.getRideId(),
                rideDb.getAmountCurrency(),
                currency,
                rideDb.getDistance(),
                category,
                category.getIcon(),
                rideDb.getTripType(),
                rideDb.getPassengers(),
                rideDb.getCreatedDate(),
                rideDb.getUuid()
        );
    }
}
