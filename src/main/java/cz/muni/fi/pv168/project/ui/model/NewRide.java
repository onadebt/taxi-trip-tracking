package cz.muni.fi.pv168.project.ui.model;

public class NewRide {
    private double amountCurrency;
    private double distance;
    private Currency currencyType;
    private int passengers;
    private Category category;
    private boolean isPaidTravel;

    public boolean isPaidTravel() {
        return isPaidTravel;
    }

    public void setPaidTravel(boolean paidTravel) {
        isPaidTravel = paidTravel;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
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
