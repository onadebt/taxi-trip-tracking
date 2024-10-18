package cz.muni.fi.pv168.project.ui.model;

import javax.swing.*;
import java.util.List;

public class CategoryListModel extends AbstractListModel<Category> {
    private final List<Category> categories;

    public CategoryListModel(List<Category> categories) {
        this.categories = categories;
    }

    @Override
    public int getSize() {
        return categories.size();
    }

    @Override
    public Category getElementAt(int index) {
        return categories.get(index);
    }
}
