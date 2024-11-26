package cz.muni.fi.pv168.project.model;

import cz.muni.fi.pv168.project.model.enums.DistanceUnit;
import cz.muni.fi.pv168.project.model.enums.TripType;

import java.time.Instant;
import java.util.UUID;

public class RidePortModel {
    private UUID uuid;
    private double amountCurrency;
    private String currencyTag;
    private double distance;
    private DistanceUnit distanceUnit;
    private String categoryName;
    private int passengers;
    private TripType tripType;
    private Instant createdDate;

    public RidePortModel(){}

    public RidePortModel(Ride ride, DistanceUnit distanceUnit) {
        this.uuid = ride.getUuid();
        this.amountCurrency = ride.getAmountCurrency();
        this.currencyTag = ride.getCurrency().getCode();
        this.distance = ride.getDistance();
        this.distanceUnit = distanceUnit;
        this.categoryName = ride.getCategory() != null ? ride.getCategory().getName() : "";
        this.passengers = ride.getNumberOfPassengers();
        this.tripType = ride.getTripType();
        this.createdDate = ride.getCreatedAt();
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public double getAmountCurrency() {
        return amountCurrency;
    }

    public void setAmountCurrency(double amountCurrency) {
        this.amountCurrency = amountCurrency;
    }

    public String getCurrencyTag() {
        return currencyTag;
    }

    public void setCurrencyTag(String currencyTag) {
        this.currencyTag = currencyTag;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public DistanceUnit getDistanceUnit() {
        return distanceUnit;
    }

    public void setDistanceUnit(DistanceUnit distanceUnit) {
        this.distanceUnit = distanceUnit;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
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
