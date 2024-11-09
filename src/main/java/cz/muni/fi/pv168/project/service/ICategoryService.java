package cz.muni.fi.pv168.project.service;

import cz.muni.fi.pv168.project.model.CategoryDbModel;
import cz.muni.fi.pv168.project.ui.model.Category;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public interface ICategoryService {

    void create(Category category);

    void update(Category category);

    void delete(int categoryId);

    @Nullable CategoryDbModel getById(int categoryId);

    @Nullable CategoryDbModel getByName(String name);
    List<CategoryDbModel> get();
}
