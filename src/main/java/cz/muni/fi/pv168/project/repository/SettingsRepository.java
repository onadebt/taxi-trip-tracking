package cz.muni.fi.pv168.project.repository;

import cz.muni.fi.pv168.project.database.dao.DataStorageException;
import cz.muni.fi.pv168.project.database.dao.SettingsDao;
import cz.muni.fi.pv168.project.database.mapper.EntityMapper;
import cz.muni.fi.pv168.project.database.mapper.SettingsMapper;
import cz.muni.fi.pv168.project.model.Settings;
import cz.muni.fi.pv168.project.model.SettingsDbModel;
import java.util.List;
import java.util.Optional;

public class SettingsRepository implements Repository<Settings> {

    private final SettingsDao settingsDao;
    private final EntityMapper<SettingsDbModel, Settings> settingsMapper;

    public SettingsRepository(SettingsDao settingsDao, EntityMapper<SettingsDbModel, Settings> settingsMapper) {
        this.settingsDao = settingsDao;
        this.settingsMapper = settingsMapper;
    }

    @Override
    public List<Settings> findAll() {
        return settingsDao.findAll().stream()
                .map(settingsMapper::mapToBusiness)
                .toList();
    }

    @Override
    public Settings create(Settings newEntity) {
        return settingsMapper.mapToBusiness(settingsDao.create(settingsMapper.mapNewEntityToDatabase(newEntity)));

    }

    @Override
    public void update(Settings entity) {
        var existing = settingsDao.findById(entity.getId()).orElseThrow(() -> new DataStorageException("Settings of ID " + entity.getId() + " not found"));
        var updated = settingsMapper.mapExistingEntityToDatabase(entity, existing.getId());

        settingsDao.update(updated);
    }

    @Override
    public void deleteById(Long id) {
        settingsDao.deleteById(id);
    }

    @Override
    public Optional<Settings> findById(Long id) {
        return settingsDao.findById(id)
                .map(settingsMapper::mapToBusiness);
    }

    @Override
    public void deleteAll() {
        settingsDao.deleteAll();
    }
}
