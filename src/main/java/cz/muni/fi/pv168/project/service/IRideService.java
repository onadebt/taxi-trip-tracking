package cz.muni.fi.pv168.project.service;

import cz.muni.fi.pv168.project.model.RideDbModel;
import cz.muni.fi.pv168.project.ui.model.NewRide;

public interface IRideService {

    void create(NewRide ride);

    void update(NewRide ride);

    void delete(int rideId);

    RideDbModel getById(int rideId);
}
