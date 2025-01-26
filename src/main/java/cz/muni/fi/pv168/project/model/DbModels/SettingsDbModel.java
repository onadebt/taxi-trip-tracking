package cz.muni.fi.pv168.project.model.DbModels;

import org.jetbrains.annotations.Nullable;

public class SettingsDbModel {
    private Long id;
    private String name;
    private String value;

    public SettingsDbModel() {
    }

    public SettingsDbModel(@Nullable Long id, String name, String value) {
        this.id = id;
        this.name = name;
        this.value = value;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
