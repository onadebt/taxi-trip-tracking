package cz.muni.fi.pv168.project.service.interfaces;

import cz.muni.fi.pv168.project.model.Ride;
import cz.muni.fi.pv168.project.model.enums.DistanceUnit;
import cz.muni.fi.pv168.project.service.crud.CrudService;

public interface IRideService extends CrudService<Ride> {

    /**
     * Recalculates all distances in the database from the current unit to the new unit.
     *
     * @param currentUnit the current unit of the distances
     * @param newUnit the new unit of the distances
     */
    void recalculateDistances(DistanceUnit currentUnit, DistanceUnit newUnit);
}
