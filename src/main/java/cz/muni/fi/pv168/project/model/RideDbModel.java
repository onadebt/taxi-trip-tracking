package cz.muni.fi.pv168.project.model;

import org.jetbrains.annotations.Nullable;

import java.time.Instant;
import java.util.UUID;

public class RideDbModel {
    private Long rideId;
    private double amountCurrency;
    private Long currencyId;
    private double distance;
    private @Nullable Long categoryId;
    private int passengers;
    private String tripType;
    private Instant createdDate;
    private UUID uuid;

    public RideDbModel(){};
    public RideDbModel(Long rideId, double amountCurrency, Long currencyId, double distance, @Nullable Long categoryId,
                       Integer passengers, String tripType, Instant createdDate, UUID uuid) {
        this.rideId = rideId;
        this.amountCurrency = amountCurrency;
        this.categoryId = categoryId;
        this.currencyId = currencyId;
        this.distance = distance;
        this.passengers = passengers;
        this.tripType = tripType;
        this.createdDate = createdDate;
        this.uuid = uuid;
    }

    public RideDbModel(Ride ride) {
        this.rideId = ride.getId();
        this.amountCurrency = ride.getAmountCurrency();
        if (ride.getCategory() != null) {
            this.categoryId = ride.getCategory().getId();
        }
        this.currencyId = ride.getCurrency().getId();
        this.distance = ride.getDistance();
        this.passengers = ride.getNumberOfPassengers();
        this.tripType = ride.getTripType().name();
        this.createdDate = Instant.now();
        this.uuid = UUID.randomUUID();
    }


}
