package cz.muni.fi.pv168.project.repository;

import cz.muni.fi.pv168.project.model.CategoryDbModel;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public interface ICategoryRepository {
    public void create(CategoryDbModel category);
    public void update(CategoryDbModel category);
    public void delete(int categoryId);
    public @Nullable CategoryDbModel getById(int categoryId);
    public @Nullable CategoryDbModel getByName(String name);
    public List<CategoryDbModel> get();
}
