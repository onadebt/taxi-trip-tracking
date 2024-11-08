package cz.muni.fi.pv168.project.repository;

import cz.muni.fi.pv168.project.model.RideDbModel;

import java.util.List;

public interface IRideRepository {
    void create(RideDbModel ride);
    void update(RideDbModel ride);
    void delete(int rideId);
    void deleteAll();
    RideDbModel getById(int rideId);
    List<RideDbModel> get();
}
