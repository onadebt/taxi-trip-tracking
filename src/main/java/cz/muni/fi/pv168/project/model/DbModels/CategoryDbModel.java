package cz.muni.fi.pv168.project.model.DbModels;

import cz.muni.fi.pv168.project.ui.resources.Icons;

import javax.swing.*;

public class CategoryDbModel {
    private Long categoryId;
    private String categoryName;
    private String iconPath;

    public CategoryDbModel(Long categoryId, String categoryName, String iconPath) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.iconPath = iconPath;
    }

    public Icon getIcon() {
        return Icons.getByName(iconPath);
    }

    public String getIconPath() {
        return iconPath;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}
