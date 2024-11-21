package cz.muni.fi.pv168.project.service.port;

import cz.muni.fi.pv168.project.model.*;

public class RidePortConverter {

    public static Ride fromPortModel(RidePortModel port, Currency currency, Category category) {
        return new Ride(
                null,
                port.getAmountCurrency(),
                currency,
                port.getDistance(),
                category,
                port.getTripType(),
                port.getPassengers(),
                port.getCreatedDate(),
                port.getUuid()
        );
    }

    public static RidePortModel toPortModel(Ride ride) {
        RidePortModel port = new RidePortModel();
        port.setAmountCurrency(ride.getAmountCurrency());
        port.setDistance(ride.getDistance());
        port.setTripType(ride.getTripType());
        port.setPassengers(ride.getNumberOfPassengers());
        port.setCreatedDate(ride.getCreatedAt());
        port.setUuid(ride.getUuid());
        return port;
    }
}
