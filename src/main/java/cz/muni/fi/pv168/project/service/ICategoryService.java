package cz.muni.fi.pv168.project.service;

import cz.muni.fi.pv168.project.model.CategoryDbModel;
import cz.muni.fi.pv168.project.model.Category;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public interface ICategoryService {

    /**
     * Create category according to model
     * @param category
     */
    void create(Category category);

    /**
     * Update category according to model
     * @param category
     */
    void update(Category category);

    /**
     * Delete category with given ID
     * @param categoryId
     */
    void deleteById(Long categoryId);
    void deleteAll();

    /**
     * Get category by id
     * @param categoryId
     * @return category, or null if not existing
     */
    @Nullable Category getById(Long categoryId);
    @Nullable Category getByName(String name);

    /**
     * Get all categories
     * @return all categories
     */
    List<Category> getAll();
}
