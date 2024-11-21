package cz.muni.fi.pv168.project.database;

import cz.muni.fi.pv168.project.model.*;

public class RideDbConverter {

    public static Ride fromDbModel(RideDbModel rideDb, Currency currency, Category category) {
        return new Ride(
                null,
                rideDb.getAmountCurrency(),
                currency,
                rideDb.getDistance(),
                category,
                rideDb.getTripType(),
                rideDb.getPassengers(),
                rideDb.getCreatedDate(),
                rideDb.getUuid()
        );
    }

    public static RideDbModel toDbModel(Ride ride) {
        RideDbModel rideDb = new RideDbModel();
        rideDb.setAmountCurrency(ride.getAmountCurrency());
        rideDb.setDistance(ride.getDistance());
        rideDb.setTripType(ride.getTripType());
        rideDb.setPassengers(ride.getNumberOfPassengers());
        rideDb.setCreatedDate(ride.getCreatedAt());
        rideDb.setUuid(ride.getUuid());
        return rideDb;
    }
}
