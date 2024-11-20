package cz.muni.fi.pv168.project.service.mockData;

import cz.muni.fi.pv168.project.model.Category;
import cz.muni.fi.pv168.project.ui.resources.Icons;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CategoryTestData {
    public static List<Category> getMockCategories() {
        return Stream.of(
                new Category(1L, "Standard", Icons.getByName("small-car.png")),
                new Category(2L, "Comfort", Icons.getByName("normal-car.png")),
                new Category(3L, "Truck", Icons.getByName("truck-car.png")),
                new Category(4L, "Sport", Icons.getByName("sport-car.png"))
        ).toList();
    }

    public static Map<Long, Category> getMockCategoryMap() {
        return getMockCategories().stream()
                .collect(Collectors.toMap(Category::getId, category -> category));
    }
}
