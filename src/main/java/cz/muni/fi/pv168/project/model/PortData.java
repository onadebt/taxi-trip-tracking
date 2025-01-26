package cz.muni.fi.pv168.project.model;

import java.util.List;

public class PortData {
    private List<RidePortModel> rides;
    private List<Currency> currencies;
    private List<Category> categories;

    public PortData(List<RidePortModel> rides, List<Currency> currencies, List<Category> categories) {
        this.rides = rides;
        this.currencies = currencies;
        this.categories = categories;
    }

    public List<RidePortModel> getRides() {
        return rides;
    }

    public void setRides(List<RidePortModel> rides) {
        this.rides = rides;
    }

    public List<Currency> getCurrencies() {
        return currencies;
    }

    public void setCurrencies(List<Currency> currencies) {
        this.currencies = currencies;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }
}
