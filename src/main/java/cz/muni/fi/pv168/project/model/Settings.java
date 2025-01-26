package cz.muni.fi.pv168.project.model;

import cz.muni.fi.pv168.project.model.enums.DistanceUnit;
import org.jetbrains.annotations.Nullable;

public class Settings extends Entity{
    private String name;
    private String value;


    public Settings(@Nullable Long id, String name, String value) {
        super(id);
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}