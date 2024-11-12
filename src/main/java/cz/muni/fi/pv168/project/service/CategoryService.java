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
        return TestCategories.getTest();
    }

//    public String[] getStringArray() {
//        return getTestCategories().stream().map(Category::getName).toArray(String[]::new);
//    }

    private class TestCategories {
        private static Map<Integer, Category> categories = new HashMap<>(){
            {put(1, new Category(1L, "CategoryA", Icons.getByName("truck-car.png")));}
            {put(2, new Category(2L, "CategoryB", Icons.getByName("truck-car.png")));}
            {put(3,  new Category(3L, "CategoryC", Icons.getByName("truck-car.png")));}
        };
        public static List<Category> getTest() {
            return categories.values().stream().toList();
        }
        public static Category getById(int id) {
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

