package cz.muni.fi.pv168.project.service;

import cz.muni.fi.pv168.project.model.Category;
import cz.muni.fi.pv168.project.repository.ICategoryRepository;
import cz.muni.fi.pv168.project.repository.Repository;
import cz.muni.fi.pv168.project.service.interfaces.ICategoryService;
import cz.muni.fi.pv168.project.service.mockData.CategoryTestData;
import cz.muni.fi.pv168.project.service.validation.ValidationResult;
import cz.muni.fi.pv168.project.service.validation.Validator;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class CategoryService implements ICategoryService {
    private final ICategoryRepository categoryRepository;
    private final Validator<Category> categoryValidator;

    public CategoryService(ICategoryRepository categoryRepository, Validator<Category> categoryValidator) {
        this.categoryRepository = categoryRepository;
        this.categoryValidator = categoryValidator;
    }


    @Override
    public @Nullable Optional<Category> getByName(String name) {
        return categoryRepository.findByName(name);
    }

    @Override
    public Optional<Category> findById(Long id) {
        return categoryRepository.findById(id);
    }

    @Override
    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    @Override
    public ValidationResult create(Category newEntity) {
        var validationResult = categoryValidator.validate(newEntity);
        if (!validationResult.isValid()) {
            return validationResult;
        }

        if (categoryRepository.findByName(newEntity.getName()).isPresent()) {
            return ValidationResult.failed("Category with this name already exists.");
        }

        var savedEntity = categoryRepository.create(newEntity);
        newEntity.setId(savedEntity.getId());

        return validationResult;
    }

    @Override
    public ValidationResult update(Category entity) {
        var validationResult = categoryValidator.validate(entity);
        if (!validationResult.isValid()) {
            return validationResult;
        }

        var existingByName = categoryRepository.findByName(entity.getName());
        if (existingByName.isPresent() && !Objects.equals(existingByName.get().getId(), entity.getId())) {
            return ValidationResult.failed("Another category with the same name already exists.");
        }

        categoryRepository.update(entity);
        return validationResult;
    }

    @Override
    public void deleteById(Long id) {
        categoryRepository.deleteById(id);
    }

    @Override
    public void deleteAll() {
        categoryRepository.deleteAll();
    }
}

