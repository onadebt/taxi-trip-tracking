package cz.muni.fi.pv168.project.service.validation;

import cz.muni.fi.pv168.project.model.Category;


/**
 * Category entity {@link Validator}.
 */
public class CategoryValidator implements Validator<Category> {

    @Override
    public ValidationResult validate(Category model) {
        ValidationResult result = new ValidationResult();

        //TODO: Implement more advanced validation logic

        if (model.getName() == null || model.getName().trim().isEmpty()) {
            result.add("Category name cannot be empty.");
        }

        if (model.getIcon() == null) {
            result.add("Category icon cannot be empty.");
        }

        return result;
    }
}
