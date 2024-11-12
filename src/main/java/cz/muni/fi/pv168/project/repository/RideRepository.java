package cz.muni.fi.pv168.project.repository;

import cz.muni.fi.pv168.project.model.Ride;
import cz.muni.fi.pv168.project.model.RideDbModel;
import org.jetbrains.annotations.Nullable;

import java.util.List;


public class RideRepository implements IRideRepository {
    private ICurrencyRepository currencyRepository;
    private ICategoryRepository categoryRepository;

    public RideRepository(ICurrencyRepository currencyRepository, ICategoryRepository categoryRepository) {
        this.currencyRepository = currencyRepository;
        this.categoryRepository = categoryRepository;
    }
    @Override
    public void create(Ride ride) {

    }

    @Override
    public void update(Ride ride) {

    }

    @Override
    public void delete(Long rideId) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public @Nullable Ride getById(Long rideId) {
        return null;
    }

    @Override
    public List<Ride> getAll() {
        return null;
    }
}
