package cz.muni.fi.pv168.project.repository;

import cz.muni.fi.pv168.project.model.RideDbModel;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.UUID;

public class RideRepository implements IRideRepository{
    @Override
    public void create(RideDbModel ride) {

    }

    @Override
    public void update(RideDbModel ride) {

    }

    @Override
    public void delete(int rideId) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public @Nullable RideDbModel getById(int rideId) {
        return null;
    }

    @Override
    public @Nullable RideDbModel getByUuid(UUID uuid) {
        return null;
    }

    @Override
    public List<RideDbModel> get() {
        return List.of();
    }
}
