package cz.muni.fi.pv168.project.service;

import cz.muni.fi.pv168.project.model.CurrencyDbModel;
import cz.muni.fi.pv168.project.model.DistanceUnit;
import cz.muni.fi.pv168.project.repository.ISettingsRepository;
import cz.muni.fi.pv168.project.ui.model.Currency;

public class SettingsService implements ISettingsService {
    ISettingsRepository settingsRepository;

    public SettingsService(ISettingsRepository settingsRepository) {
        this.settingsRepository = settingsRepository;
    }
    @Override
    public CurrencyDbModel getDefaultCurrency() {
        return TestSettings.getDefaultCurrency();
    }

    @Override
    public DistanceUnit getDefaultDistance() {
        return TestSettings.getDistUnit();
    }

    @Override
    public void setDefaultCurrency(Currency currency) {

    }

    @Override
    public void setDefaultDistance(DistanceUnit unit) {

    }

    private class TestSettings {
        public static DistanceUnit getDistUnit() {
            return DistanceUnit.Kilometer;
        }

        public static CurrencyDbModel getDefaultCurrency() {
            return new CurrencyDbModel(1, "Euro", "EUR", 1.0);
        }
    }
}
