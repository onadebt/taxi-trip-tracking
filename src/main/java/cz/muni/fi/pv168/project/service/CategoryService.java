package cz.muni.fi.pv168.project.service;

import cz.muni.fi.pv168.project.model.CategoryDbModel;
import cz.muni.fi.pv168.project.ui.model.Category;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class CategoryService implements ICategoryService{
    @Override
    public void create(Category category) {

    }

    @Override
    public void update(Category category) {

    }

    @Override
    public void delete(int categoryId) {

    }

    @Override
    public @Nullable CategoryDbModel getById(int categoryId) {
        return null;
    }

    @Override
    public @Nullable CategoryDbModel getByName(String name) {
        return null;
    }

    @Override
    public List<CategoryDbModel> get() {
        return List.of();
    }
}
