package cz.muni.fi.pv168.project.service;

import cz.muni.fi.pv168.project.model.RideDbModel;
import cz.muni.fi.pv168.project.ui.model.NewRide;

import java.util.List;

public interface IRideService {

    void create(RideDbModel ride);

    void update(RideDbModel ride);

    void delete(int rideId);

    void deleteAll();

    RideDbModel getById(int rideId);

    List<RideDbModel> get();

    boolean isValid(RideDbModel ride);
}
