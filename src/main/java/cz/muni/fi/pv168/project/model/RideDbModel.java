package cz.muni.fi.pv168.project.model;

import cz.muni.fi.pv168.project.ui.model.NewRide;
import org.jetbrains.annotations.Nullable;

import java.time.Instant;
import java.util.UUID;

public class RideDbModel {
    private int rideId;
    private double amountCurrency;
    private int currencyId;
    private double distance;
    private @Nullable Integer categoryId;
    private int passengers;
    private TripType tripType;
    private Instant createdDate;
    private UUID uuid;

    public RideDbModel(){};
    public RideDbModel(int rideId, double amountCurrency, int currencyId, double distance, @Nullable Integer categoryId,
                       int passengers, TripType tripType, Instant createdDate, UUID uuid) {
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

    public RideDbModel(NewRide ride) {
        this.amountCurrency = ride.getAmountCurrency();
        if (ride.getCategory() != null) {
            this.categoryId = ride.getCategory().getId();
        }
        this.currencyId = ride.getCurrencyType().getId();
        this.distance = ride.getDistance();
        this.passengers = ride.getPassengers();
        this.tripType = ride.getTripType();
        this.createdDate = Instant.now();
        this.uuid = UUID.randomUUID();
    }

    public int getRideId() {
        return rideId;
    }

    public void setRideId(int rideId) {
        this.rideId = rideId;
    }

    public double getAmountCurrency() {
        return amountCurrency;
    }

    public void setAmountCurrency(double amountCurrency) {
        this.amountCurrency = amountCurrency;
    }

    public int getCurrencyId() {
        return currencyId;
    }

    public void setCurrencyId(int currencyId) {
        this.currencyId = currencyId;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public @Nullable Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(@Nullable Integer categoryId) {
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
