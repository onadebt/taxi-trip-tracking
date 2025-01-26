package cz.muni.fi.pv168.project.service.crud;

import cz.muni.fi.pv168.project.model.Category;
import cz.muni.fi.pv168.project.model.Currency;
import cz.muni.fi.pv168.project.service.validation.ValidationResult;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;

/**
 * Service for creation, read, update, and delete operations.
 *
 * @param <T> entity type.
 */
public interface CrudService<T> {

    /**
     * Find entity by given {@code id}.
     */
    Optional<T> findById(Long id);

    /**
     * Find all entities.
     */
    List<T> findAll();

    /**
     * Validate and store the given {@code newEntity}.
     */
    ValidationResult create(T newEntity);

    /**
     * Updates the given {@code entity}.
     */
    ValidationResult update(T entity);

    /**
     * Delete entity with given {@code id}.
     */
    void deleteById(Long id);

    /**
     * Delete all entities.
     */
    void deleteAll();

}
