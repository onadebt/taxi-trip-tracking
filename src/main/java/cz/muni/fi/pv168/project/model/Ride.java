package cz.muni.fi.pv168.project.model;

import cz.muni.fi.pv168.project.model.enums.DistanceUnit;
import cz.muni.fi.pv168.project.model.enums.TripType;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.time.Instant;
import java.util.UUID;

public class Ride {
    private @Nullable Long id;
    private Double amountCurrency;
    private Currency currency;
    private Double distance;
    private DistanceUnit distanceUnit;
    private @Nullable Category category;
    private @Nullable Icon categoryIcon;
    private TripType tripType;
    private Instant createdAt;
    private Integer numberOfPassengers;
    private UUID uuid;

    /* New ride */
    public Ride(@Nullable Long id, Double amountCurrency, Currency currency, Double distance, @Nullable Category category, @Nullable Icon categoryIcon, TripType tripType, Integer numberOfPassengers) {
        this.id = id;
        this.amountCurrency = amountCurrency;
        this.currency = currency;
        this.distance = distance;
        this.category = category;
        this.categoryIcon = categoryIcon;
        this.tripType = tripType;
        this.numberOfPassengers = numberOfPassengers;
        this.createdAt = Instant.now();
        this.uuid = UUID.randomUUID();
    }


    /*Ride general*/
    public Ride(Long id, Double amountCurrency, Currency currency, Double distance, @Nullable Category category, @Nullable Icon categoryIcon, TripType tripType, Integer numberOfPassengers, Instant createdAt, UUID uuid) {
        this.id = id;
        this.amountCurrency = amountCurrency;
        this.currency = currency;
        this.distance = distance;
        this.category = category;
        this.categoryIcon = categoryIcon;
        this.tripType = tripType;
        this.numberOfPassengers = numberOfPassengers;
        this.createdAt = createdAt;
        this.uuid = uuid;
    }

    public Ride() {}

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

    public String getCurrencyCode() {
        return currency.getCode();
    }

    public void setCurrencyCode(String currencyCode) {
        this.currency.setCode(currencyCode);
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

    public @Nullable Icon getCategoryIcon() { return this.categoryIcon; }

    public void setCategoryIcon(@Nullable Icon categoryIcon) { this.categoryIcon = categoryIcon; }

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

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }
}


