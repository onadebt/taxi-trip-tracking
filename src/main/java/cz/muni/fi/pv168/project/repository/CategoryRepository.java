package cz.muni.fi.pv168.project.repository;

import cz.muni.fi.pv168.project.model.Category;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class CategoryRepository implements ICategoryRepository {
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
    public @Nullable Category getById(int categoryId) {
        return null;
    }

    @Override
    public @Nullable Category getByName(String name) {
        return null;
    }

    @Override
    public List<Category> getAll() {
        return null;
    }
}
