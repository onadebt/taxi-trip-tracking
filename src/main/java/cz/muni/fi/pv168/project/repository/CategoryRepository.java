package cz.muni.fi.pv168.project.repository;

import cz.muni.fi.pv168.project.database.dao.CategoryDataAccessObject;
import cz.muni.fi.pv168.project.database.dao.DataStorageException;
import cz.muni.fi.pv168.project.database.mappers.EntityMapper;
import cz.muni.fi.pv168.project.model.Category;
import cz.muni.fi.pv168.project.model.DbModels.CategoryDbModel;
import cz.muni.fi.pv168.project.repository.interfaces.ICategoryRepository;

import java.util.List;
import java.util.Optional;

public class CategoryRepository implements ICategoryRepository {

    private final CategoryDataAccessObject categoryDao;
    private final EntityMapper<CategoryDbModel, Category> categoryMapper;

    public CategoryRepository(CategoryDataAccessObject categoryDao, EntityMapper<CategoryDbModel, Category> categoryMapper) {
        this.categoryDao = categoryDao;
        this.categoryMapper = categoryMapper;
    }

    @Override
    public List<Category> findAll() {
        return categoryDao.findAll().stream().map(categoryMapper::mapToBusiness).toList();
    }

    @Override
    public Category create(Category category) {
        return categoryMapper.mapToBusiness(categoryDao.create(categoryMapper.mapNewEntityToDatabase(category)));
    }

    @Override
    public void update(Category category) {
        var existing = categoryDao.findById(category.getId()).orElseThrow(() -> new DataStorageException("Category of ID " + category.getId() + " not found"));
        var updated = categoryMapper.mapExistingEntityToDatabase(category, existing.getCategoryId());
        categoryDao.update(updated);
    }

    @Override
    public void deleteById(Long id) {
        categoryDao.deleteById(id);
    }

    @Override
    public Optional<Category> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public void deleteAll() {}

    public Optional<Category> findByName(String name) {
        return categoryDao
                .findByName(name)
                .map(categoryMapper::mapToBusiness);
    }
}
