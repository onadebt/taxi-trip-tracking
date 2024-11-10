package cz.muni.fi.pv168.project.service;

import cz.muni.fi.pv168.project.model.RideDbModel;
import cz.muni.fi.pv168.project.ui.model.NewRide;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.UUID;

public interface IRideService {
    /**
     * Create new ride according to model
     * @param ride
     */
    void create(NewRide ride);

    /**
     * Create new ride according to model
     * @param ride
     */
    void create(RideDbModel ride);

    /**
     * Update ride according to model
     * @param ride
     */
    void update(NewRide ride);

    /**
     * Update ride according to model
     * @param ride
     */
    void update(RideDbModel ride);

    /**
     * Delete ride with given id
     * @param rideId
     */
    void delete(int rideId);

    /**
     * Delete all rides
     */
    void deleteAll();

    /**
     * Get ride by given id
     * @param rideId
     * @return ride, null if not found
     */
    @Nullable RideDbModel getById(int rideId);

    /**
     * Get all rides
     * @return
     */
    List<RideDbModel> get();

    /**
     * Get ride by given uuid
     * @param uuid
     * @return ride, null if not found
     */
    @Nullable RideDbModel getByUuid(UUID uuid);

    /**
     * Checks whether the given ride model represents a valid ride
     * @param ride
     * @return true / false
     */
    boolean isValid(RideDbModel ride);
}
