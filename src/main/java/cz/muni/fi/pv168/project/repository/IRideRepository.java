package cz.muni.fi.pv168.project.repository;

import cz.muni.fi.pv168.project.model.Ride;
import cz.muni.fi.pv168.project.model.RideDbModel;
import cz.muni.fi.pv168.project.model.enums.DistanceUnit;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.UUID;


public interface IRideRepository {
    void create(RideDbModel ride);
    void update(RideDbModel ride);
    void deleteByUUID(UUID rideUUID);
    void deleteAll();
    @Nullable Ride getById(Long rideId);
    RideDbModel getByUUID(UUID rideUUID);
    void recalculateDistances(DistanceUnit newUnit);
    List<RideDbModel> getAll();
}
