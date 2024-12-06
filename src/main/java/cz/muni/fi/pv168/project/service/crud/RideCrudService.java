package cz.muni.fi.pv168.project.service.crud;

import cz.muni.fi.pv168.project.model.Ride;
import cz.muni.fi.pv168.project.model.enums.DistanceUnit;
import cz.muni.fi.pv168.project.repository.RideRepository;
import cz.muni.fi.pv168.project.service.validation.ValidationResult;
import cz.muni.fi.pv168.project.service.validation.Validator;

import java.util.List;

public class RideCrudService implements CrudService<Ride> {

    private final RideRepository rideRepository;
    private final Validator<Ride> rideValidator;

    public RideCrudService(final RideRepository rideRepository, final Validator<Ride> rideValidator) {
        this.rideRepository = rideRepository;
        this.rideValidator = rideValidator;
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

    public void recalculateDistances(DistanceUnit newUnit) {
        rideRepository.recalculateDistances(newUnit);
    }

}
