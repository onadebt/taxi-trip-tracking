package cz.muni.fi.pv168.project.repository;

import cz.muni.fi.pv168.project.model.CategoryDbModel;

import java.util.List;

public interface ICategoryRepository {
    public void create(CategoryDbModel category);
    public void update(CategoryDbModel category);
    public void delete(int categoryId);
    public CategoryDbModel getById(int categoryId);
    public List<CategoryDbModel> get();
}
