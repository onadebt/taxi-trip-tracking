package cz.muni.fi.pv168.project.service.port;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import cz.muni.fi.pv168.project.model.*;
import cz.muni.fi.pv168.project.model.enums.DistanceUnit;
import cz.muni.fi.pv168.project.model.exception.ValidationException;
import cz.muni.fi.pv168.project.service.interfaces.*;
import cz.muni.fi.pv168.project.service.validation.Validator;
import cz.muni.fi.pv168.project.ui.model.ImportMode;
import cz.muni.fi.pv168.project.utils.DistanceConversionHelper;
import cz.muni.fi.pv168.project.utils.PathHelper;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class JsonImportService implements ImportService {
    IRideService rideService;
    ICurrencyService currencyService;
    ICategoryService categoryService;
    ISettingsService settingsService;
    Validator<Ride> rideValidator;
    Gson gson = new GsonBuilder().registerTypeAdapter(Instant.class, new Gson_InstantTypeAdapter()).create();

    public JsonImportService(IRideService rideService, ICurrencyService currencyService, ICategoryService categoryService, ISettingsService settingsService, Validator<Ride> rideValidator) {
        this.rideService = rideService;
        this.categoryService = categoryService;
        this.currencyService = currencyService;
        this.settingsService = settingsService;
        this.rideValidator = rideValidator;
    }

    @Override
    public void importData(String path, ImportMode mode) {
        List<RidePortModel> data;
        try (var reader = Files.newBufferedReader(Path.of(PathHelper.AddExtensionIfMissing(path, "json")), StandardCharsets.UTF_8)) {
            data = Arrays.stream(gson.fromJson(reader, RidePortModel[].class)).toList();

        } catch (IOException ex) {
            throw new DataPortException("Could not read from specified file", ex);
        }

        validate(data);

        switch (mode) {
            case Create -> create(data);
            case CreateUpdate -> createUpdate(data);
            case Overwrite -> overwrite(data);
        }
    }

    private void validate(List<RidePortModel> rides) {
        for (var ride : rides) {
            Optional<Category> category = Optional.empty();
            if (!Objects.equals(ride.getCategoryName(), "")) {
                category = categoryService.findByName(ride.getCategoryName());
                if (category.isEmpty()) {
                    throw new DataPortException(String.format("Category of name \"%s\" does not exist. Import aborted.", ride.getCategoryName()));
                }
            }

            var currency = currencyService.findByCode(ride.getCurrencyTag());
            if (currency.isEmpty()) {
                throw new DataPortException(String.format("Currency of tag \"%s\" does not exist. Import aborted.", ride.getCurrencyTag()));
            }

            try {
                rideValidator.validate(createRideFromPortModel(ride, currency.get(), category.orElse(null), ride.getDistanceUnit()));
            } catch (ValidationException ex) {
                throw new DataPortException(String.format("Imported data is not valid.\nRideUUID: %s\nReason: %s Import aborted.", ride.getUuid(), ex.getMessage()));
            }
        }
    }

    private void create(List<RidePortModel> rides) {
        var defaultDistUnit = settingsService.getDefaultDistance();
        for (var ride : rides) {
            if (rideService.findAll().stream().anyMatch(r -> r.getUuid() == ride.getUuid())) {
                continue;
            }

            var currency = currencyService.findByCode(ride.getCurrencyTag());
            var category = categoryService.findByName(ride.getCategoryName());

            rideService.create(createRideFromPortModel(ride, currency.get(), category.orElse(null), defaultDistUnit));
        }
    }

    private void createUpdate(List<RidePortModel> rides) {
        var defaultDistUnit = settingsService.getDefaultDistance();
        for (var ride : rides) {
            var currency = currencyService.findByCode(ride.getCurrencyTag());
            var category = categoryService.findByName(ride.getCategoryName());

            if (rideService.findAll().stream().anyMatch(r -> r.getUuid().equals(ride.getUuid()))) {
                rideService.update(createRideFromPortModel(ride, currency.get(), category.orElse(null), defaultDistUnit));
            } else {
                rideService.create(createRideFromPortModel(ride, currency.get(), category.orElse(null), defaultDistUnit));
            }
        }
    }

    private void overwrite(List<RidePortModel> rides) {
        rideService.deleteAll();
        create(rides);
    }

    private Ride createRideFromPortModel(RidePortModel ridePortModel, Currency currency, @Nullable Category category, DistanceUnit defaultDistanceUnit) {
        if (ridePortModel.getDistanceUnit() != defaultDistanceUnit) {
            if (defaultDistanceUnit == DistanceUnit.KILOMETER) {
                ridePortModel.setDistance(DistanceConversionHelper.milesToKilometers(ridePortModel.getDistance()));
            } else {
                ridePortModel.setDistance(DistanceConversionHelper.kilometersToMiles(ridePortModel.getDistance()));
            }
            ridePortModel.setDistanceUnit(defaultDistanceUnit);
        }

        return RidePortConverter.fromPortModel(ridePortModel, currency, category);
    }
}
