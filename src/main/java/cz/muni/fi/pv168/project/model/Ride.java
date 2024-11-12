package cz.muni.fi.pv168.project.model;

import cz.muni.fi.pv168.project.model.enums.DistanceUnit;
import cz.muni.fi.pv168.project.model.enums.TripType;
import org.jetbrains.annotations.Nullable;

import java.time.Instant;

public class Ride {
    private @Nullable Long id;
    private Double amountCurrency;
    private Currency currency;
    private Double distance;
    private DistanceUnit distanceUnit;
    private @Nullable Category category;
    private TripType tripType;
    private Instant createdAt;
    private Integer numberOfPassengers;

    public Ride(@Nullable Long id, Double amountCurrency, Currency currency, Double distance, DistanceUnit distanceUnit, @Nullable Category category, TripType tripType, Integer numberOfPassengers, Instant createdAt) {
        this.id = id;
        this.amountCurrency = amountCurrency;
        this.currency = currency;
        this.distance = distance;
        this.distanceUnit = distanceUnit;
        this.category = category;
        this.tripType = tripType;
        this.numberOfPassengers = numberOfPassengers;
        this.createdAt = createdAt;
    }

    public Ride() {
    }

    public DistanceUnit getDistanceUnit() {
        return distanceUnit;
    }

    public void setDistanceUnit(DistanceUnit distanceUnit) {
        this.distanceUnit = distanceUnit;
    }

    public @Nullable Long getId() {
        return id;
    }

    public void setId(@Nullable Long id) {
        this.id = id;
    }

    public Double getAmountCurrency() {
        return amountCurrency;
    }

    public void setAmountCurrency(Double amountCurrency) {
        this.amountCurrency = amountCurrency;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public Double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }

    public @Nullable Category getCategory() {
        return category;
    }

    public void setCategory(@Nullable Category category) {
        this.category = category;
    }

    public TripType getTripType() {
        return tripType;
    }

    public void setTripType(TripType tripType) {
        this.tripType = tripType;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Integer getNumberOfPassengers() {
        return numberOfPassengers;
    }

    public void setNumberOfPassengers(Integer numberOfPassengers) {
        this.numberOfPassengers = numberOfPassengers;
    }
}


