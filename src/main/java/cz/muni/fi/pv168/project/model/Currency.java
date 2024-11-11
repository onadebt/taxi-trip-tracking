package cz.muni.fi.pv168.project.model;

import cz.muni.fi.pv168.project.model.enums.CurrencyCode;

public class Currency {
    private Long id = 0L;
    private String name;
    private CurrencyCode code;
    private Double exchangeRate;

    public Currency(Long id, String name, CurrencyCode code, Double exchangeRate) {
        this.id = id;
        this.name = name;
        this.code = code;
        this.exchangeRate = exchangeRate;
    }

    public Currency(String name, CurrencyCode code, Double exchangeRate) {
        this.name = name;
        this.code = code;
        this.exchangeRate = exchangeRate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CurrencyCode getCode() {
        return code;
    }

    public void setCode(CurrencyCode code) {
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
