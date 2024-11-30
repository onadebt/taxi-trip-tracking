package cz.muni.fi.pv168.project.model;

import javax.swing.*;
import java.util.Objects;

public class Category extends Entity {
    private String name;
    private Icon icon;
    private String iconAsString;

    public Category(Long id, String name, Icon icon) {
        super(id);
        this.name = name;
        this.icon = icon;
    }

    public Category(String name, Icon icon) {
        super(0L);
        this.name = name;
        this.icon = icon;
    }

    public Category(String name, String iconAsString) {
        super(0L);
        this.name = name;
        this.icon = new ImageIcon(iconAsString);
        this.iconAsString = iconAsString;
    }

    public Category(Long id, String name, String IconAsString) {
        super(id);
        this.name = name;
        this.iconAsString = IconAsString;
        this.icon = new ImageIcon(iconAsString);
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

    public void setIconAsString(String iconAsString) { this.iconAsString = iconAsString; }

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

    public String getIconAsString() {
        return iconAsString;
    }
}
