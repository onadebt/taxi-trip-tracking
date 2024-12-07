package cz.muni.fi.pv168.project.service;

import cz.muni.fi.pv168.project.model.Ride;
import cz.muni.fi.pv168.project.model.enums.DistanceUnit;
import cz.muni.fi.pv168.project.repository.IRideRepository;
import cz.muni.fi.pv168.project.service.crud.CrudService;
import cz.muni.fi.pv168.project.service.interfaces.IRideService;
import cz.muni.fi.pv168.project.service.validation.ValidationResult;
import cz.muni.fi.pv168.project.service.validation.Validator;
import cz.muni.fi.pv168.project.utils.DistanceConversionHelper;

import java.util.List;
import java.util.Optional;

public class RideService implements IRideService {

    private final IRideRepository rideRepository;
    private final Validator<Ride> rideValidator;

    public RideService(IRideRepository rideRepository, Validator<Ride> rideValidator) {
        this.rideRepository = rideRepository;
        this.rideValidator = rideValidator;
    }

    @Override
    public void recalculateDistances(DistanceUnit currentUnit, DistanceUnit newUnit) {
        rideRepository.findAll().forEach(ride -> {
            ride.setDistance(DistanceConversionHelper.convertDistance(ride.getDistance(), currentUnit, newUnit));
            rideRepository.update(ride);
        });
    }

    @Override
    public Optional<Ride> findById(Long id) {
        return rideRepository.findById(id);
    }

    @Override
    public List<Ride> findAll() {
        return rideRepository.findAll();
    }

    @Override
    public ValidationResult create(Ride newEntity) {
        Ride savedEntity = rideRepository.create(newEntity);
        newEntity.setId(savedEntity.getId());
        return null;
    }

    @Override
    public ValidationResult update(Ride entity) {
        rideRepository.update(entity);
        return null;
    }

    @Override
    public void deleteById(Long id) {
        rideRepository.deleteById(id);
    }

    @Override
    public void deleteAll() {
        rideRepository.deleteAll();
    }
}
