package cz.muni.fi.pv168.project.service.interfaces;

import cz.muni.fi.pv168.project.model.RideDbModel;

import java.util.List;

public interface IRIdeDbService {
    void create(RideDbModel ride);

    void update(RideDbModel ride);

    void deleteById(Long rideId);

    void deleteAll();

    RideDbModel getById(Long rideId);

    List<RideDbModel> getAll();
}
