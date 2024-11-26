package cz.muni.fi.pv168.project.database;

import cz.muni.fi.pv168.project.model.DbModel;
import cz.muni.fi.pv168.project.model.Entity;

public interface EntityConverter<T extends Entity, U> {

    U toDbModel(T entity);

    T toEntity(U dbModel);
}
