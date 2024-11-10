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
    private int categoryId;
    private String categoryName;
    private String iconPath;

    public CategoryDbModel(int categoryId, String categoryName, String iconPath) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.iconPath = iconPath;
    }

    public Icon getIcon() {
        return Icons.getByName(iconPath);
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}
