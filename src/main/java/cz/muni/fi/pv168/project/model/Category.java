package cz.muni.fi.pv168.project.model;

import javax.swing.*;

public class Category {
    private Long id = 0L;
    private String name;
    private Icon icon;

    public Category(Long id, String name, Icon icon) {
        this.id = id;
        this.name = name;
        this.icon = icon;
    }

    public Category(String name, Icon icon) {
        this.name = name;
        this.icon = icon;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Icon getIcon() {
        return icon;
    }

    public void setIcon(Icon icon) {
        this.icon = icon;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Category category = (Category) o;

        if (!name.equals(category.name)) return false;
        return icon != null ? icon.equals(category.icon) : category.icon == null;
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + (icon != null ? icon.hashCode() : 0);
        return result;
    }
}
