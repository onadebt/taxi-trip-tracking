package cz.muni.fi.pv168.project.service;

import cz.muni.fi.pv168.project.repository.ICategoryRepository;
import cz.muni.fi.pv168.project.model.Category;
import cz.muni.fi.pv168.project.service.interfaces.ICategoryService;
import cz.muni.fi.pv168.project.service.mockData.CategoryTestData;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class CategoryService implements ICategoryService {
    private final ICategoryRepository categoryRepository;

    public CategoryService(ICategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public void create(Category category) {
        // TODO: Implement this method
    }

    @Override
    public void update(Category category) {
        // TODO: Implement this method
    }

    @Override
    public void deleteById(Long categoryId) {
        // TODO: Implement this method
    }

    @Override
    public void deleteAll() {
        // TODO: Implement this method
    }

    @Override
    public @Nullable Category getById(Long categoryId) {
        // TODO: Implement this method
        return null;
    }

    @Override
    public @Nullable Category getByName(String name) {
        // TODO: Implement this method
        return null;
    }

    @Override
    public List<Category> getAll() {
        return CategoryTestData.getMockCategories();
    }
}

