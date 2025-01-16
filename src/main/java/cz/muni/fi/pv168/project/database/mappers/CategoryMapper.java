package cz.muni.fi.pv168.project.database.mappers;

import cz.muni.fi.pv168.project.model.Category;
import cz.muni.fi.pv168.project.model.DbModels.CategoryDbModel;

/**
 * Mapper from the {@link CategoryDbModel} to {@link cz.muni.fi.pv168.project.model.Category}.
 */
public final class CategoryMapper implements EntityMapper<CategoryDbModel, Category> {

    @Override
    public Category mapToBusiness(CategoryDbModel dbCategory) {
        return new Category(
                dbCategory.getCategoryId(),
                dbCategory.getCategoryName(),
                dbCategory.getIconPath()
        );
    }

    @Override
    public CategoryDbModel mapNewEntityToDatabase(Category entity) {
        return getCategoryEntity(entity, null);
    }

    @Override
    public CategoryDbModel mapExistingEntityToDatabase(Category entity, Long dbId) {
        return getCategoryEntity(entity, dbId);
    }

    private static CategoryDbModel getCategoryEntity(Category entity, Long dbId) {
        return new CategoryDbModel(
                dbId,
                entity.getName(),
                entity.getIconPath()
        );
    }
}
