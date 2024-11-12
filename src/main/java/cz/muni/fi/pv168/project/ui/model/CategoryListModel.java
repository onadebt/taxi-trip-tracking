package cz.muni.fi.pv168.project.ui.model;

import cz.muni.fi.pv168.project.model.Category;
import cz.muni.fi.pv168.project.service.interfaces.ICategoryService;

import javax.swing.*;

import java.util.List;

public class CategoryListModel extends AbstractListModel<Category> {
    public List<Category> categories;
    private final ICategoryService categoryService;

    public CategoryListModel(ICategoryService categoryService) {
        this.categoryService = categoryService;
        this.categories = List.copyOf(categoryService.getAll());
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
        int newRowIndex = getSize();
        categoryService.create(category);
        categories.add(category);
        fireIntervalAdded(this, newRowIndex, newRowIndex);
    }

    public void removeCategory(Category category) {
        int index = categories.indexOf(category);
        if (index >= 0) {
            categoryService.deleteById(category.getId());
            categories.remove(category);
            fireIntervalRemoved(this, index, index);
        }
    }

    public void updateCategory(Category category) {
        int index = categories.indexOf(category);
        if (index >= 0) {
            categoryService.update(category);
            fireContentsChanged(this, index, index);
        }
    }

    public void setCategories(List<Category> newCategories) {
        this.categories = newCategories;
        fireContentsChanged(this, 0, getSize() - 1);
    }

    public int indexOf(Category category) {
        return categories.indexOf(category);
    }


    public void refresh() {
        this.categories = List.copyOf(categoryService.getAll());
        fireContentsChanged(this, 0, getSize() - 1);
    }

    public List<Category> getAllCategories() {
        return List.copyOf(categories);
    }
}
