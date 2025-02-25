package cz.muni.fi.pv168.project.database.mappers;

import cz.muni.fi.pv168.project.model.Settings;
import cz.muni.fi.pv168.project.model.DbModels.SettingsDbModel;

/**
 * Mapper from the {@link SettingsDbModel} to {@link cz.muni.fi.pv168.project.model.Settings}.
 */
public class SettingsMapper implements EntityMapper<SettingsDbModel, Settings> {

    @Override
    public Settings mapToBusiness(SettingsDbModel entity) {
        return new Settings(
                entity.getId(),
                entity.getName(),
                entity.getValue()
                );
    }

    @Override
    public SettingsDbModel mapNewEntityToDatabase(Settings entity) {
        return new SettingsDbModel(
                entity.getId(),
                entity.getName(),
                entity.getValue()
        );
    }

    @Override
    public SettingsDbModel mapExistingEntityToDatabase(Settings entity, Long dbId) {
        return new SettingsDbModel(
                dbId,
                entity.getName(),
                entity.getValue()
        );
    }
}
