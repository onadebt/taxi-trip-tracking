package cz.muni.fi.pv168.project.repository.interfaces;

import cz.muni.fi.pv168.project.model.Ride;

import java.util.Optional;
import java.util.UUID;


public interface IRideRepository extends Repository<Ride> {
    void deleteByUuid(UUID uuid);
    Optional<Ride> findByUuid(UUID uuid);
}
