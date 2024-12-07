package cz.muni.fi.pv168.project.service.interfaces;

import cz.muni.fi.pv168.project.model.Category;
import cz.muni.fi.pv168.project.service.crud.CrudService;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;

public interface ICategoryService extends CrudService<Category> {

    Optional<Category> findByName(String name);

}
