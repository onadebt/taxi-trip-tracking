package cz.muni.fi.pv168.project.repository;

import cz.muni.fi.pv168.project.model.Settings;

import java.util.Optional;

public interface ISettingsRepository {
    Optional<Settings> getSettings(Settings settings);
    void setSettings(Settings settings);
}
