package cz.muni.fi.pv168.project.ui.model;

import java.sql.Timestamp;

public class RideModel {
    private double amountCurrency;
    private String currency;
    private double distance;
    private String categoryName;
    private Boolean personalRide;
    private Timestamp createdDate;
    private int numberOfPeople;

    // Constructor
    public RideModel(double amountCurrency, String currency, double distance, String categoryName, Timestamp createdDate, int numberOfPeople) {
        this.amountCurrency = amountCurrency;
        this.currency = currency;
        this.distance = distance;
        this.categoryName = categoryName;
        this.personalRide = false;
        this.numberOfPeople = numberOfPeople;
        this.createdDate = createdDate;

    }


    // Getters
    public double getAmountCurrency() {
        return amountCurrency;
    }

    public String getCurrency() {
        return currency;
    }

    public double getDistance() {
        return distance;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public Boolean getPersonalRide() {
        return personalRide;
    }

    public Timestamp getCreatedDate() {
        return createdDate;
    }

    public int getNumberOfPeople(){ return numberOfPeople; }

    // Setters
    public void setAmountCurrency(double amountCurrency) {
        this.amountCurrency = amountCurrency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public void setPersonalRide(Boolean personalRide) {
        this.personalRide = personalRide;
    }

    public void setNumberOfPeople(int numberOfPeople) { this.numberOfPeople = numberOfPeople; }
}

