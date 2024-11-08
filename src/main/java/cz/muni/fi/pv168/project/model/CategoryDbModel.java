package cz.muni.fi.pv168.project.model;

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
    private byte[] icon;

    public Icon getIcon() {
        try {
            BufferedImage iconImage = ImageIO.read(new ByteArrayInputStream(icon));
            return new ImageIcon(iconImage);
        }  catch (IOException e) {
            return null;
        }
    }

    public void setIcon(Icon icon) {
        BufferedImage img = new BufferedImage(icon.getIconWidth(), icon.getIconHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = img.createGraphics();
        icon.paintIcon(null, g2d, 0, 0);
        g2d.dispose();

        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            ImageOutputStream ios = ImageIO.createImageOutputStream(baos);
            try {
                ImageIO.write(img, "png", ios);
            } finally {
                ios.close();
            }
            this.icon = baos.toByteArray();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
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
