package cz.muni.fi.pv168.project.model;

import cz.muni.fi.pv168.project.model.enums.DistanceUnit;
import org.jetbrains.annotations.Nullable;

public class Settings extends Entity{
    private DistanceUnit defaultDistanceUnit;

    public Settings(@Nullable Long id, DistanceUnit defaultDistanceUnit) {
        super(id);
        this.defaultDistanceUnit = defaultDistanceUnit;
    }

    public Settings(DistanceUnit defaultDistanceUnit) {
        super(null);
        this.defaultDistanceUnit = defaultDistanceUnit;
    }

    public Settings() {
        super(null);
    }

    public DistanceUnit getDefaultDistanceUnit() {
        return defaultDistanceUnit;
    }

    public void setDefaultDistanceUnit(DistanceUnit defaultDistanceUnit) {
        this.defaultDistanceUnit = defaultDistanceUnit;
    }
}