package cz.muni.fi.pv168.project.repository;

import cz.muni.fi.pv168.project.model.Category;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public interface ICategoryRepository {
    void create(Category category);
    void update(Category category);
    void delete(int categoryId);
    @Nullable Category getById(int categoryId);
    @Nullable Category getByName(String name);
    List<Category> getAll();
}
