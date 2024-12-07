package cz.muni.fi.pv168.project.model;

import org.jetbrains.annotations.Nullable;

public class SettingsDbModel {
    private Long id;
    private String defaultDistanceUnit;

    public SettingsDbModel() {
    }

    public SettingsDbModel(@Nullable Long id, String defaultDistanceUnit) {
        this.id = id;
        this.defaultDistanceUnit = defaultDistanceUnit;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDefaultDistanceUnit() {
        return defaultDistanceUnit;
    }

    public void setDefaultDistanceUnit(String defaultDistanceUnit) {
        this.defaultDistanceUnit = defaultDistanceUnit;
    }
}
