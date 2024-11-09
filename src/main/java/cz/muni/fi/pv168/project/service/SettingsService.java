package cz.muni.fi.pv168.project.service;

import cz.muni.fi.pv168.project.model.CurrencyDbModel;
import cz.muni.fi.pv168.project.model.DistanceUnit;
import cz.muni.fi.pv168.project.ui.model.Currency;

public class SettingsService implements ISettingsService{
    @Override
    public CurrencyDbModel getDefaultCurrency() {
        return null;
    }

    @Override
    public DistanceUnit getDefaultDistance() {
        return null;
    }

    @Override
    public void setDefaultCurrency(Currency currency) {

    }

    @Override
    public void setDefaultDistance(DistanceUnit unit) {

    }
}
