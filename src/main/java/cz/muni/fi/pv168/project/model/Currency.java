package cz.muni.fi.pv168.project.model;

import org.jetbrains.annotations.Nullable;

public class Currency {
    private @Nullable Long id;
    private String name;
    private String code;
    private Double exchangeRate;

    public Currency(@Nullable Long id, String name, String code, Double exchangeRate) {
        this.id = id;
        this.name = name;
        this.code = code;
        this.exchangeRate = exchangeRate;
    }


    public @Nullable Long getId() {
        return id;
    }

    public void setId(@Nullable Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Double getExchangeRate() {
        return exchangeRate;
    }

    public void setExchangeRate(Double exchangeRate) {
        this.exchangeRate = exchangeRate;
    }

    @Override
    public String toString() {
        return "Currency{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", currencyCode='" + code + '\'' +
                ", exchangeRate='" + exchangeRate + '\'' +
                '}';
    }
}
