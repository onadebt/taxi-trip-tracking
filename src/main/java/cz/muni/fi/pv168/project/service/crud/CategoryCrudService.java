package cz.muni.fi.pv168.project.service.crud;

import cz.muni.fi.pv168.project.model.Category;
import cz.muni.fi.pv168.project.model.Currency;
import cz.muni.fi.pv168.project.repository.CategoryRepository;
import cz.muni.fi.pv168.project.service.validation.ValidationResult;
import cz.muni.fi.pv168.project.service.validation.Validator;

import java.util.List;

public class CategoryCrudService implements CrudService<Category> {

    private final CategoryRepository categoryRepository;
    private final Validator<Category> categoryValidator;

    public CategoryCrudService(CategoryRepository categoryRepository, Validator<Category> categoryValidator) {
        this.categoryRepository = categoryRepository;
        this.categoryValidator = categoryValidator;
    }

    @Override
    public ValidationResult create(Category newEntity) {
        var validationResult = categoryValidator.validate(newEntity);
        if (!validationResult.isValid()) {
            return validationResult;
        }

        // Check if a category with the same name already exists
        if (categoryRepository.getByName(newEntity.getName()) != null) {
            return ValidationResult.failed("Category with this name already exists.");
        }

        // All validation passed
        Category savedEntity = categoryRepository.create(newEntity);
        newEntity.setId(savedEntity.getId());

        return validationResult;
    }

    @Override
    public ValidationResult update(Category entity) {
        var validationResult = categoryValidator.validate(entity);
        if (!validationResult.isValid()) {
            return validationResult;
        }

        // Check for duplicate name (excluding the current entity)
        var existingByName = categoryRepository.getByName(entity.getName());
        if (existingByName != null && !existingByName.getId().equals(entity.getId())) {
            return ValidationResult.failed("Another category with the same name already exists.");
        }

        //all validation passed
        categoryRepository.update(entity);

        return validationResult;
    }

    @Override
    public void deleteById(Long id) {
        categoryRepository.deleteById(id);
    }

    @Override
    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    @Override
    public void deleteAll() {
        categoryRepository.deleteAll();
    }
}
