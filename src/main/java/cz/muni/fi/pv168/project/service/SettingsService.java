package cz.muni.fi.pv168.project.service;

import cz.muni.fi.pv168.project.model.Settings;
import cz.muni.fi.pv168.project.model.enums.DistanceUnit;
import cz.muni.fi.pv168.project.repository.ISettingsRepository;
import cz.muni.fi.pv168.project.service.interfaces.ISettingsService;
import java.util.Optional;



public class SettingsService implements ISettingsService {
    private final ISettingsRepository settingsRepository;
    private Settings settings;

    public SettingsService(ISettingsRepository settingsRepository) {
        this.settingsRepository = settingsRepository;
    }

    @Override
    public DistanceUnit getDefaultDistance() {
        var settings = settingsRepository.getSettingsByName(SettingsNames.distanceUnit);

        assert settings.isPresent();
        return DistanceUnit.valueOf(settings.get().getValue());
    }

    @Override
    public void setDefaultDistance(DistanceUnit unit) {
        var settings = getSettingsByName(SettingsNames.distanceUnit);
        assert settings.isPresent();

//        if (DistanceUnit.valueOf(settings.get().getValue()).equals(unit)) {
//            return;
//        }

        settings.get().setValue(unit.toString());
        settingsRepository.setSettings(settings.get());
    }

    @Override
    public Optional<Settings> getSettingsByName(String name) {
        return settingsRepository.getSettingsByName(name);
    }

    public static class SettingsNames {
        public static final String distanceUnit = "DefaultDistanceUnit";
    }
}