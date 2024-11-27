package cz.muni.fi.pv168.project.repository;

import cz.muni.fi.pv168.project.model.Category;
import cz.muni.fi.pv168.project.ui.resources.Icons;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;

public class CategoryRepository implements Repository<Category> {

    @Override
    public List<Category> findAll() {
        return List.of(
                new Category(1L, "Small", Icons.getByName("small-car.png")),
                new Category(2L, "Normal", Icons.getByName("normal-car.png")),
                new Category(3L, "Sport", Icons.getByName("sport-car.png"))
        );
    }

    @Override
    public Category create(Category newEntity) {
        return null;
    }

    @Override
    public void update(Category entity) {

    }

    @Override
    public void deleteById(Long id) {

    }

    @Override
    public Optional<Category> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public void deleteAll() {

    }
}
