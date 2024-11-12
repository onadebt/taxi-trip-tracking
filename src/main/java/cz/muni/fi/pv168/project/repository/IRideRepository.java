package cz.muni.fi.pv168.project.repository;

import cz.muni.fi.pv168.project.model.Ride;
import org.jetbrains.annotations.Nullable;

import java.util.List;


public interface IRideRepository {
    void create(Ride ride);
    void update(Ride ride);
    void delete(Long rideId);
    void deleteAll();
    @Nullable Ride getById(Long rideId);
    List<Ride> getAll();
}
