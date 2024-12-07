package cz.muni.fi.pv168.project.database.mapper;

import cz.muni.fi.pv168.project.model.Settings;
import cz.muni.fi.pv168.project.model.SettingsDbModel;
import cz.muni.fi.pv168.project.model.enums.DistanceUnit;

/**
 * Mapper from the {@link cz.muni.fi.pv168.project.model.SettingsDbModel} to {@link cz.muni.fi.pv168.project.model.Settings}.
 */
public class SettingsMapper implements EntityMapper<SettingsDbModel, Settings> {

    @Override
    public Settings mapToBusiness(SettingsDbModel entity) {
        return new Settings(
                entity.getId(),
                DistanceUnit.valueOf(entity.getDefaultDistanceUnit())
        );
    }

    @Override
    public SettingsDbModel mapNewEntityToDatabase(Settings entity) {
        return new SettingsDbModel(
                entity.getId(),
                entity.getDefaultDistanceUnit().toString()
        );
    }

    @Override
    public SettingsDbModel mapExistingEntityToDatabase(Settings entity, Long dbId) {
        return new SettingsDbModel(
                dbId,
                entity.getDefaultDistanceUnit().toString()
        );
    }
}
