package cz.muni.fi.pv168.project.model;

import cz.muni.fi.pv168.project.model.enums.TripType;

import java.time.Instant;

public class RideFilterCriteria {
    private Double minAmount;
    private Double maxAmount;
    private Currency currency;
    private Double minDistance;
    private Double maxDistance;
    private Category category;
    private TripType tripType;
    private Integer minPassengers;
    private Integer maxPassengers;
    private Instant startDate;
    private Instant endDate;

    public Double getMinAmount() {
        return minAmount;
    }

    public void setMinAmount(Double minAmount) {
        this.minAmount = minAmount;
    }

    public Double getMaxAmount() {
        return maxAmount;
    }

    public void setMaxAmount(Double maxAmount) {
        this.maxAmount = maxAmount;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public Double getMinDistance() {
        return minDistance;
    }

    public void setMinDistance(Double minDistance) {
        this.minDistance = minDistance;
    }

    public Double getMaxDistance() {
        return maxDistance;
    }

    public void setMaxDistance(Double maxDistance) {
        this.maxDistance = maxDistance;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public TripType getTripType() {
        return tripType;
    }

    public void setTripType(TripType tripType) {
        this.tripType = tripType;
    }

    public Integer getMinPassengers() {
        return minPassengers;
    }

    public void setMinPassengers(Integer minPassengers) {
        this.minPassengers = minPassengers;
    }

    public Integer getMaxPassengers() {
        return maxPassengers;
    }

    public void setMaxPassengers(Integer maxPassengers) {
        this.maxPassengers = maxPassengers;
    }

    public Instant getStartDate() {
        return startDate;
    }

    public void setStartDate(Instant startDate) {
        this.startDate = startDate;
    }

    public Instant getEndDate() {
        return endDate;
    }

    public void setEndDate(Instant endDate) {
        this.endDate = endDate;
    }
}
