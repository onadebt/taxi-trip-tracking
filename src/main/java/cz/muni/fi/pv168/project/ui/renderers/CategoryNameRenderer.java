package cz.muni.fi.pv168.project.ui.renderers;

import cz.muni.fi.pv168.project.model.Category;

import javax.swing.*;

public class CategoryNameRenderer extends AbstractRenderer<Category> {
    public CategoryNameRenderer() {
        super(Category.class);
    }

    @Override
    protected void updateLabel(JLabel label, Category value) {
        if (value != null) {
            label.setText(value.getName());
        }
    }
}
