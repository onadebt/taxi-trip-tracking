package cz.muni.fi.pv168.project.model;

import org.jetbrains.annotations.Nullable;

public abstract class Entity {
    protected @Nullable Long id;

    protected Entity(@Nullable Long id) {
        this.id = id;
    }

    public @Nullable Long getId() {
        return id;
    }

    public void setId(@Nullable Long id) {
        this.id = id;
    }
}
