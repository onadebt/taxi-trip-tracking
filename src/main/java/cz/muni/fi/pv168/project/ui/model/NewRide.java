package cz.muni.fi.pv168.project.ui.model;

import cz.muni.fi.pv168.project.model.TripType;
import org.jetbrains.annotations.Nullable;

public class NewRide {
    private double amountCurrency;
    private double distance;
    private Currency currencyType;
    private int passengers;
    private @Nullable Category category;
    private TripType tripType;

    public TripType getTripType() {
        return tripType;
    }

    public void setTripType(TripType tripType) {
        this.tripType = tripType;
    }

    public @Nullable Category getCategory() {
        return category;
    }

    public void setCategory(@Nullable Category category) {
        this.category = category;
    }

    public Currency getCurrencyType() {
        return currencyType;
    }

    public void setCurrencyType(Currency currencyType) {
        this.currencyType = currencyType;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public double getAmountCurrency() {
        return amountCurrency;
    }

    public void setAmountCurrency(double amountCurrency) {
        this.amountCurrency = amountCurrency;
    }

    public int getPassengers() { return passengers; }

    public void setPassengers(int passengers) { this.passengers = passengers; }
}
