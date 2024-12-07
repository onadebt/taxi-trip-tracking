package cz.muni.fi.pv168.project.repository;

import cz.muni.fi.pv168.project.model.Ride;
import cz.muni.fi.pv168.project.model.RideDbModel;
import cz.muni.fi.pv168.project.model.enums.DistanceUnit;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


public interface IRideRepository extends Repository<Ride> {
    void deleteByUuid(UUID uuid);
    Optional<Ride> findByUuid(UUID uuid);
}
