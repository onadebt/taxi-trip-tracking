package cz.muni.fi.pv168.project.model;

import cz.muni.fi.pv168.project.model.enums.TripType;
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
    private TripType tripType;
    private Instant createdDate;
    private UUID uuid;

    public RideDbModel(){};
    public RideDbModel(Long rideId, double amountCurrency, Long currencyId, double distance, @Nullable Long categoryId,
                       Integer passengers, TripType tripType, Instant createdDate, UUID uuid) {
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
        this.tripType = ride.getTripType();
        this.createdDate = Instant.now();
        this.uuid = UUID.randomUUID();
    }

    public Long getRideId() {
        return rideId;
    }

    public void setRideId(Long rideId) {
        this.rideId = rideId;
    }

    public double getAmountCurrency() {
        return amountCurrency;
    }

    public void setAmountCurrency(double amountCurrency) {
        this.amountCurrency = amountCurrency;
    }

    public Long getCurrencyId() {
        return currencyId;
    }

    public void setCurrencyId(Long currencyId) {
        this.currencyId = currencyId;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public @Nullable Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(@Nullable Long categoryId) {
        this.categoryId = categoryId;
    }

    public int getPassengers() {
        return passengers;
    }

    public void setPassengers(int passengers) {
        this.passengers = passengers;
    }

    public TripType getTripType() {
        return tripType;
    }

    public void setTripType(TripType tripType) {
        this.tripType = tripType;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }
}
