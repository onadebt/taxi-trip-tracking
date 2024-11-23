package cz.muni.fi.pv168.project.model;

public abstract class Entity {
    protected Long id;

    protected Entity(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
