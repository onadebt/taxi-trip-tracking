package cz.muni.fi.pv168.project.model;

import javax.swing.*;
import java.util.Objects;
import cz.muni.fi.pv168.project.ui.resources.Icons;

public class Category extends Entity {
    private String name;
    private Icon icon;
    private String iconPath;

    public Category(String name, String iconPath) {
        super(0L);
        this.name = name;
        this.iconPath = iconPath;
        this.icon = new ImageIcon(Objects.requireNonNull(getClass().getResource(iconPath)));
    }

    public Category(Long id, String name, String iconPath) {
        super(id);
        this.name = name;
        this.iconPath = iconPath;
        this.icon = new ImageIcon(Objects.requireNonNull(getClass().getResource(iconPath)));
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

    public void setIconPath(String iconPath) {
        this.iconPath = iconPath;
        this.icon = new ImageIcon(Objects.requireNonNull(getClass().getResource(iconPath)));
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
        return Objects.equals(icon, category.icon);
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + (icon != null ? icon.hashCode() : 0);
        return result;
    }

    public String getIconPath() {
        return iconPath;
    }
}
