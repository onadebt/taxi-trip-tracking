package cz.muni.fi.pv168.project.database.dao;

import cz.muni.fi.pv168.project.model.DbModels.CategoryDbModel;

import java.util.Optional;

public interface CategoryDataAccessObject extends DataAccessObject<CategoryDbModel> {
    Optional<CategoryDbModel> findByName(String name);
}
