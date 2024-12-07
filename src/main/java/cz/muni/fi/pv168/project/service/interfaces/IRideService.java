package cz.muni.fi.pv168.project.service.interfaces;

import cz.muni.fi.pv168.project.model.Ride;
import cz.muni.fi.pv168.project.model.exception.ValidationException;
import cz.muni.fi.pv168.project.model.RideDbModel;
import cz.muni.fi.pv168.project.model.enums.DistanceUnit;
import cz.muni.fi.pv168.project.service.crud.CrudService;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public interface IRideService extends CrudService<Ride> {

    /**
     * Recalculates all distances in the database from the current unit to the new unit.
     *
     * @param currentUnit the current unit of the distances
     * @param newUnit the new unit of the distances
     */
    void recalculateDistances(DistanceUnit currentUnit, DistanceUnit newUnit);
}
