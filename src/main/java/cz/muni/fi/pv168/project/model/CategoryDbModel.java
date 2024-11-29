package cz.muni.fi.pv168.project.model;

import cz.muni.fi.pv168.project.ui.resources.Icons;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageOutputStream;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

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
