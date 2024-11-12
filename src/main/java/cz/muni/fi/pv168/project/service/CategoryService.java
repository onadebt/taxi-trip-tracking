package cz.muni.fi.pv168.project.service;

import cz.muni.fi.pv168.project.model.CategoryDbModel;
import cz.muni.fi.pv168.project.repository.ICategoryRepository;
import cz.muni.fi.pv168.project.model.Category;
import cz.muni.fi.pv168.project.service.interfaces.ICategoryService;
import cz.muni.fi.pv168.project.ui.resources.Icons;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static javax.swing.UIManager.put;

public class CategoryService implements ICategoryService {
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
    public List<Category> getAll() {
        return List.of();
    }

//    public String[] getStringArray() {
//        return getTestCategories().stream().map(Category::getName).toArray(String[]::new);
//    }

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


//    public List<Category> getTestCategories() {
//        return List.of(
//                new Category( "CategoryA", Icons.getByName("truck-car.png")),
//                new Category( "CategoryB", Icons.getByName("normal-car.png")),
//                new Category( "CategoryC", Icons.getByName("sport-car.png")),
//                new Category( "CategoryD", Icons.getByName("convertible-car.png")),
//                new Category( "CategoryE", Icons.getByName("truck-car.png"))
//        );
//    }
}

