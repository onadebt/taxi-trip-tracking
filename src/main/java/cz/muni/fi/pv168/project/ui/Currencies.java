package cz.muni.fi.pv168.project.ui;

import java.util.List;
import java.util.Arrays;

public class Currencies {

    private final List<String> currencies = Arrays.asList("CZK", "USD", "EURO");
    private String currentCurrency;

    // constructor sets default currency
    public Currencies() {
        this.currentCurrency = currencies.get(0);
    }

    public String getCurrentCurrency() {
        return this.currentCurrency;
    }

    public void setCurrentCurrency(String currency) {
        if (currencies.contains(currency)) {
            this.currentCurrency = currency;
        } else {
            throw new IllegalArgumentException("Invalid currency selected");
        }
    }

    public List<String> getCurrencies() {
        return currencies;
    }
}