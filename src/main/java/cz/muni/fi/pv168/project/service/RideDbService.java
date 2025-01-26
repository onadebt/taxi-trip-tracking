package cz.muni.fi.pv168.project.service;

import cz.muni.fi.pv168.project.model.DbModels.RideDbModel;
import cz.muni.fi.pv168.project.service.interfaces.IRIdeDbService;

import java.util.List;

public class RideDbService implements IRIdeDbService {
    @Override
    public void create(RideDbModel ride) {

    }

    @Override
    public void update(RideDbModel ride) {

    }

    @Override
    public void deleteById(Long rideId) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public RideDbModel getById(Long rideId) {
        return null;
    }

    @Override
    public List<RideDbModel> getAll() {
        return List.of();
    }

}
