package cz.muni.fi.pv168.project.service;

import cz.muni.fi.pv168.project.model.CategoryDbModel;
import cz.muni.fi.pv168.project.repository.ICategoryRepository;
import cz.muni.fi.pv168.project.model.Category;
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
    public void deleteById(Long categoryId) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public @Nullable Category getById(Long categoryId) {
        return null;
    }

    @Override
    public @Nullable Category getByName(String name) {
        return null;
    }


    @Override
    public List<Category> findAll() {
        return null;
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
