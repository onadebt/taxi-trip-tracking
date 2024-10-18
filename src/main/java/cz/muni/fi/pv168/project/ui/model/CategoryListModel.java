package cz.muni.fi.pv168.project.ui.model;

import javax.swing.*;
import java.util.List;

public class CategoryListModel extends AbstractListModel<Category> {
    public List<Category> categories;

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

    public void addCategory(Category category) {
        categories.add(category);
        fireIntervalAdded(this, categories.size() - 1, categories.size() - 1);
    }

    public void removeCategory(Category category) {
        int index = categories.indexOf(category);
        if (index >= 0) {
            categories.remove(category);
            fireIntervalRemoved(this, index, index);
        }
    }

    public void updateCategory(int index, Category category) {
        categories.set(index, category);
        fireContentsChanged(this, index, index);
    }

    public void setCategories(List<Category> newCategories) {
        this.categories = newCategories;
        fireContentsChanged(this, 0, getSize() - 1);
    }

    public int indexOf(Category category) {
        return categories.indexOf(category);
    }
}
