package cz.muni.fi.pv168.project.service;

import cz.muni.fi.pv168.project.model.Settings;
import cz.muni.fi.pv168.project.model.enums.DistanceUnit;
import cz.muni.fi.pv168.project.repository.ISettingsRepository;
import cz.muni.fi.pv168.project.repository.Repository;
import cz.muni.fi.pv168.project.repository.SettingsRepository;
import cz.muni.fi.pv168.project.service.interfaces.ISettingsService;
import cz.muni.fi.pv168.project.service.validation.ValidationResult;

import java.util.List;
import java.util.Optional;



public class SettingsService implements ISettingsService {
    private final ISettingsRepository settingsRepository;
    private Settings settings;

    public SettingsService(ISettingsRepository settingsRepository) {
        this.settingsRepository = settingsRepository;
    }

    @Override
    public DistanceUnit getDefaultDistance() {
        var settings = settingsRepository.getSettings(new Settings(null, SettingsNames.distanceUnit, null));
        return DistanceUnit.valueOf(settings.get().getValue());
    }

    @Override
    public void setDefaultDistance(DistanceUnit unit) {
        settingsRepository.setSettings(new Settings(null, SettingsNames.distanceUnit, unit.toString()));
    }


    private static class SettingsNames {
        public static final String distanceUnit = "DefaultDistanceUnit";


    }
}