package cz.muni.fi.pv168.project.model.DbModels;

import cz.muni.fi.pv168.project.model.enums.TripType;
import org.jetbrains.annotations.Nullable;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public class RideDbModel {
    private @Nullable Long rideId;
    private BigDecimal amountCurrency;
    private Long currencyId;
    private double distance;
    private @Nullable Long categoryId;
    private int passengers;
    private TripType tripType;
    private Instant createdDate;
    private UUID uuid;

    public RideDbModel(){};
    public RideDbModel(@Nullable Long rideId, BigDecimal amountCurrency, Long currencyId, double distance, @Nullable Long categoryId,
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

    public @Nullable Long getRideId() {
        return rideId;
    }

    public void setRideId(@Nullable Long rideId) {
        this.rideId = rideId;
    }

    public BigDecimal getAmountCurrency() {
        return amountCurrency;
    }

    public void setAmountCurrency(BigDecimal amountCurrency) {
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
