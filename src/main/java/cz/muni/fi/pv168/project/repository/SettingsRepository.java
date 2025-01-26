package cz.muni.fi.pv168.project.repository;

import cz.muni.fi.pv168.project.database.dao.SettingsDao;
import cz.muni.fi.pv168.project.database.mappers.EntityMapper;
import cz.muni.fi.pv168.project.model.Settings;
import cz.muni.fi.pv168.project.model.DbModels.SettingsDbModel;
import cz.muni.fi.pv168.project.repository.interfaces.ISettingsRepository;

import java.util.Optional;

public class SettingsRepository implements ISettingsRepository {

    private final SettingsDao settingsDao;
    private final EntityMapper<SettingsDbModel, Settings> settingsMapper;

    public SettingsRepository(SettingsDao settingsDao, EntityMapper<SettingsDbModel, Settings> settingsMapper) {
        this.settingsDao = settingsDao;
        this.settingsMapper = settingsMapper;
    }

    public Optional<Settings> getSettings(Settings settings) {
        return Optional.ofNullable(settingsMapper.mapToBusiness(settingsDao.getSettings(settings.getName()).get()));
    }

    @Override
    public void setSettings(Settings settings) {
        settingsDao.setSettings(settingsMapper.mapNewEntityToDatabase(settings));
    }

    @Override
    public Optional<Settings> getSettingsByName(String name) {
        return settingsDao.getSettings(name).map(settingsMapper::mapToBusiness);

    }
}
