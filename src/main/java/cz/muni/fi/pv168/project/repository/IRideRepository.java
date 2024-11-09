package cz.muni.fi.pv168.project.repository;

import cz.muni.fi.pv168.project.model.RideDbModel;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.UUID;

public interface IRideRepository {
    void create(RideDbModel ride);
    void update(RideDbModel ride);
    void delete(int rideId);
    void deleteAll();
    @Nullable RideDbModel getById(int rideId);
    @Nullable RideDbModel getByUuid(UUID uuid);
    List<RideDbModel> get();
}
