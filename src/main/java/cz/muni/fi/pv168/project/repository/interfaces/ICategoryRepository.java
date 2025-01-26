package cz.muni.fi.pv168.project.repository.interfaces;

import cz.muni.fi.pv168.project.model.Category;

import java.util.Optional;

public interface ICategoryRepository extends Repository<Category> {
    Optional<Category> findByName(String name);
}
