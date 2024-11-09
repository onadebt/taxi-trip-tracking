package cz.muni.fi.pv168.project.repository;

import cz.muni.fi.pv168.project.model.CategoryDbModel;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class CategoryRepository implements ICategoryRepository {
    @Override
    public void create(CategoryDbModel category) {

    }

    @Override
    public void update(CategoryDbModel category) {

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
