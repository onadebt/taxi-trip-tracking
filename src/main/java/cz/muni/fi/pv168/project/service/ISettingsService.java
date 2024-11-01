package cz.muni.fi.pv168.project.service;

import cz.muni.fi.pv168.project.model.CurrencyDbModel;
import cz.muni.fi.pv168.project.model.DistanceUnit;
import cz.muni.fi.pv168.project.ui.model.Currency;

public interface ISettingsService {

    CurrencyDbModel getDefaultCurrency();

    DistanceUnit getDefaultDistance();

    void setDefaultCurrency(Currency currency);

    void setDefaultDistance(DistanceUnit unit);
}
