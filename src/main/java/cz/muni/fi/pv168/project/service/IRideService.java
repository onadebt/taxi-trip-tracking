package cz.muni.fi.pv168.project.service;

import cz.muni.fi.pv168.project.model.Ride;
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

    /**
     * Get all rides
     * @return
     */
    List<Ride> findAll();
}
