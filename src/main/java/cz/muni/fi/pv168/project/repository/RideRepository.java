package cz.muni.fi.pv168.project.repository;

import cz.muni.fi.pv168.project.database.dao.DataStorageException;
import cz.muni.fi.pv168.project.database.dao.RideDao;
import cz.muni.fi.pv168.project.database.mapper.EntityMapper;
import cz.muni.fi.pv168.project.model.Ride;
import cz.muni.fi.pv168.project.model.RideDbModel;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


public class RideRepository implements IRideRepository {
    private final RideDao rideDao;
    private final EntityMapper<RideDbModel, Ride> rideMapper;

    public RideRepository(RideDao rideDao, EntityMapper<RideDbModel, Ride> rideMapper) {
        this.rideDao = rideDao;
        this.rideMapper = rideMapper;
    }



    public void updateAll(List<Ride> rides) {
        for (Ride ride : rides) {
            update(ride);
        }
    }


    @Override
    public List<Ride> findAll() {
        return rideDao.findAll().stream().map(rideMapper::mapToBusiness).toList();
    }

    @Override
    public Ride create(Ride newEntity) {
        return rideMapper.mapToBusiness(rideDao.create(rideMapper.mapNewEntityToDatabase(newEntity)));
    }

    @Override
    public void update(Ride entity) {
        var existing = rideDao.findById(entity.getId()).orElseThrow(() -> new DataStorageException("Ride of ID " + entity.getId() + " not found"));
        var updated = rideMapper.mapExistingEntityToDatabase(entity, existing.getRideId());
        rideDao.update(updated);
    }

    @Override
    public void deleteById(Long id) {
        rideDao.deleteById(id);
    }

    public void deleteByUuid(UUID uuid) {
        rideDao.deleteByUuid(uuid);
    }


    @Override
    public void deleteAll() {
        rideDao.deleteAll();
    }

    @Override
    public Optional<Ride> findById(Long id) {
        var ride = rideDao.findById(id);
        return ride.map(rideMapper::mapToBusiness);
    }

    public Optional<Ride> findByUuid(UUID uuid) {
        var ride = rideDao.findByUuid(uuid);
        return ride.map(rideMapper::mapToBusiness);
    }
}
