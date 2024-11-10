package cz.muni.fi.pv168.project.service;

import cz.muni.fi.pv168.project.model.CategoryDbModel;
import cz.muni.fi.pv168.project.repository.ICategoryRepository;
import cz.muni.fi.pv168.project.ui.model.Category;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CategoryService implements ICategoryService{
    ICategoryRepository categoryRepository;

    public CategoryService(ICategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }
    
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
        return TestCategories.getById(categoryId);
    }

    @Override
    public @Nullable CategoryDbModel getByName(String name) {
        return null;
    }

    @Override
    public List<CategoryDbModel> get() {
        return TestCategories.getTest();
    }

    private class TestCategories {
        private static Map<Integer, CategoryDbModel> categories = new HashMap<>(){
            {put(1, new CategoryDbModel(1, "CategoryA", "truck-car.png"));}
            {put(2, new CategoryDbModel(2, "CategoryB", "truck-car.png"));}
            {put(3,  new CategoryDbModel(3, "CategoryC", "truck-car.png"));}
        };
        public static List<CategoryDbModel> getTest() {
            return categories.values().stream().toList();
        }
        public static CategoryDbModel getById(int id) {
            return categories.get(id);
        }
    }
}
