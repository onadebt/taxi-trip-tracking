package cz.muni.fi.pv168.project.repository;

import cz.muni.fi.pv168.project.model.Category;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public interface ICategoryRepository extends Repository<Category> {
    Optional<Category> findByName(String name);
}
