package cz.muni.fi.pv168.project.service.interfaces;

import cz.muni.fi.pv168.project.model.Ride;
import cz.muni.fi.pv168.project.model.RideDbModel;
import cz.muni.fi.pv168.project.model.enums.DistanceUnit;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public interface IRideService {
    /**
     * Create new ride according to model
     * @param ride
     */
    void create(Ride ride);

    /**
     * Update ride according to model
     * @param ride
     */
    void update(Ride ride);

    /**
     * Delete ride with given id
     * @param rideId
     */
    void deleteById(Long rideId);

    /**
     * Delete all rides
     */
    void deleteAll();

    /**
     * Get ride by given id
     * @param rideId
     * @return ride, null if not found
     */
    @Nullable Ride getById(Long rideId);

    RideDbModel getByUUID(Long rideUUID);
    void recalculateDistances(DistanceUnit newUnit);

    /**
     * Get all rides
     * @return
     */
    List<Ride> getAll();
}
