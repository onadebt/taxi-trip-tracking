package cz.muni.fi.pv168.project.service;

import cz.muni.fi.pv168.project.model.CategoryDbModel;
import cz.muni.fi.pv168.project.ui.model.Category;

import java.util.List;

public interface ICategoryService {

    void create(Category category);

    void update(Category category);

    void delete(int categoryId);

    CategoryDbModel getById(int categoryId);

    List<CategoryDbModel> get();
}
