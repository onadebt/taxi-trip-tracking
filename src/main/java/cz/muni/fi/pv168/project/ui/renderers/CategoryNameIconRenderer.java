package cz.muni.fi.pv168.project.ui.renderers;

import cz.muni.fi.pv168.project.model.Category;

import javax.swing.*;
import java.awt.*;

public class CategoryNameIconRenderer extends AbstractRenderer<Category> {
    private final int iconWidth;
    private final int iconHeight;

    public CategoryNameIconRenderer(int iconWidth, int iconHeight) {
        super(Category.class);
        this.iconWidth = iconWidth;
        this.iconHeight = iconHeight;
    }

    public CategoryNameIconRenderer() {
        this(48, 48);
    }

    @Override
    protected void updateLabel(JLabel label, Category value) {
        if (value != null) {
            label.setText(value.getName());
            Icon icon = value.getIcon();
            if (icon instanceof ImageIcon imageIcon) {
                Image image = imageIcon.getImage().getScaledInstance(iconWidth, iconHeight, Image.SCALE_SMOOTH);
                label.setIcon(new ImageIcon(image));
            } else {
                label.setIcon(icon);
            }
        } else {
            label.setText("");
            label.setIcon(null);
        }
    }
}
