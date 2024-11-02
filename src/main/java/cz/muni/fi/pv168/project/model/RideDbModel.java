package cz.muni.fi.pv168.project.model;

import java.time.Instant;

public class RideDbModel {
    private int rideId;
    private double amountCurrency;
    private int currencyId;
    private double distance;
    private int categoryId;
    private int passengers;
    private TripType tripType;
    private Instant createdDate;

    public RideDbModel(){};
    public RideDbModel(int rideId, double amountCurrency, int currencyId, double distance, int categoryId,
                       int passengers, TripType tripType, Instant createdDate) {
        this.rideId = rideId;
        this.amountCurrency = amountCurrency;
        this.categoryId = categoryId;
        this.currencyId = currencyId;
        this.distance = distance;
        this.passengers = passengers;
        this.tripType = tripType;
        this.createdDate = createdDate;
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

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
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
}
