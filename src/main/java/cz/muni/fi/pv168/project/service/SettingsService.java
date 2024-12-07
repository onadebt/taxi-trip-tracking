package cz.muni.fi.pv168.project.service;

import cz.muni.fi.pv168.project.model.Settings;
import cz.muni.fi.pv168.project.model.enums.DistanceUnit;
import cz.muni.fi.pv168.project.repository.Repository;
import cz.muni.fi.pv168.project.repository.SettingsRepository;
import cz.muni.fi.pv168.project.service.interfaces.ISettingsService;
import cz.muni.fi.pv168.project.service.validation.ValidationResult;

import java.util.List;
import java.util.Optional;

public class SettingsService implements ISettingsService {
    private static SettingsService instance;
    private final Repository<Settings> settingsRepository;
    private Settings settings;

    public SettingsService(Repository<Settings> settingsRepository) {
        this.settingsRepository = settingsRepository;
        this.settings = settingsRepository.findAll().stream().findFirst().orElse(null);
    }

    public static synchronized SettingsService getInstance(Repository<Settings> settingsRepository) {
        if (instance == null) {
            instance = new SettingsService(settingsRepository);
        }
        return instance;
    }

    @Override
    public Optional<Settings> getSettings() {
        return Optional.ofNullable(settings);
    }

    @Override
    public void saveSettings(Settings newSettings) {
        if (settings == null) {
            settings = settingsRepository.create(newSettings);
        } else {
            settingsRepository.update(newSettings);
            settings = newSettings;
        }
//        new ValidationResult();
    }

    @Override
    public DistanceUnit getDefaultDistance() {
        return settings.getDefaultDistanceUnit();
    }

    @Override
    public void setDefaultDistance(DistanceUnit unit) {
        settings.setDefaultDistanceUnit(unit);
    }

    @Override
    public Optional<Settings> findById(Long id) {
        return settingsRepository.findById(id);
    }

    @Override
    public List<Settings> findAll() {
        return settingsRepository.findAll();
    }

    @Override
    public ValidationResult create(Settings newEntity) {
        return null;
    }

    @Override
    public ValidationResult update(Settings entity) {
        return null;
    }

    @Override
    public void deleteById(Long id) {
        return;
    }

    @Override
    public void deleteAll() {
        return;
    }
}