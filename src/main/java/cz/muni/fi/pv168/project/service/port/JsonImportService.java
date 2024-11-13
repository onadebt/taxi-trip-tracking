package cz.muni.fi.pv168.project.service.port;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import cz.muni.fi.pv168.project.model.*;
import cz.muni.fi.pv168.project.model.enums.DistanceUnit;
import cz.muni.fi.pv168.project.model.exception.ValidationException;
import cz.muni.fi.pv168.project.service.interfaces.*;
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

public class JsonImportService implements ImportService {
    IRideService rideService;
    ICurrencyService currencyService;
    ICategoryService categoryService;
    ISettingsService settingsService;
    Gson gson = new GsonBuilder().registerTypeAdapter(Instant.class, new Gson_InstantTypeAdapter()).create();

    public JsonImportService(IRideService rideService, ICurrencyService currencyService, ICategoryService categoryService, ISettingsService settingsService) {
        this.rideService = rideService;
        this.categoryService = categoryService;
        this.currencyService = currencyService;
        this.settingsService = settingsService;
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

        var allRides = rideService.getAll();
        var allCategories = categoryService.getAll();
        var allCurrencies = currencyService.getAll();

        switch (mode) {
            case Create -> create(data, allRides, allCurrencies, allCategories);
            case CreateUpdate -> createUpdate(data, allRides, allCurrencies, allCategories);
            case Overwrite -> overwrite(data, allRides, allCurrencies, allCategories);
        }
    }

    private void validate(List<RidePortModel> rides) {
        var allCategories = categoryService.getAll();
        var allCurrencies = currencyService.getAll();
        for (var ride : rides) {
            Category category = null;
            if (!Objects.equals(ride.getCategoryName(), "")) {
                category = allCategories.stream().filter(cat -> cat.getName().equals(ride.getCategoryName())).findFirst().orElse(null);
                if (category == null) {
                    throw new DataPortException(String.format("Category of name \"%s\" does not exist. Import aborted.", ride.getCategoryName()));
                }
            }

            var currency = allCurrencies.stream().filter(curr -> curr.getCode().toString().equals(ride.getCurrencyTag())).findFirst().orElse(null);
            if (currency == null) {
                throw new DataPortException(String.format("Currency of tag \"%s\" does not exist. Import aborted.", ride.getCurrencyTag()));
            }

            try {
                rideService.validate(createRideFromPortModel(ride, currency, category, ride.getDistanceUnit()));
            } catch (ValidationException ex) {
                throw new DataPortException(String.format("Imported data is not valid.\nRideUUID: %s\nReason: %s Import aborted.", ride.getUuid(), ex.getMessage()));
            }
        }
    }

    private void create(List<RidePortModel> rides, List<Ride> allRides, List<Currency> allCurrencies, List<Category> allCategories) {
        var defaultDistUnit = settingsService.getDefaultDistance();
        for (var ride : rides) {
            if (allRides.stream().anyMatch(r -> r.getUuid() == ride.getUuid())) {
                continue;
            }

            var currency = allCurrencies.stream().filter(c -> c.getCode().equals(ride.getCurrencyTag())).findFirst().orElseThrow();
            var category = allCategories.stream().filter(c -> c.getName().equals(ride.getCategoryName())).findFirst().orElse(null);

            rideService.create(createRideFromPortModel(ride, currency, category, defaultDistUnit));
        }
    }

    private void createUpdate(List<RidePortModel> rides, List<Ride> allRides, List<Currency> allCurrencies, List<Category> allCategories) {
        var defaultDistUnit = settingsService.getDefaultDistance();
        for (var ride : rides) {
            var currency = allCurrencies.stream().filter(c -> c.getCode().equals(ride.getCurrencyTag())).findFirst().orElseThrow();
            var category = allCategories.stream().filter(c -> c.getName().equals(ride.getCategoryName())).findFirst().orElse(null);

            if (allRides.stream().anyMatch(r -> r.getUuid().equals(ride.getUuid()))) {
                rideService.update(createRideFromPortModel(ride, currency, category, defaultDistUnit));
            } else {
                rideService.create(createRideFromPortModel(ride, currency, category, defaultDistUnit));
            }
        }
    }


    private void overwrite(List<RidePortModel> rides, List<Ride> allRides, List<Currency> allCurrencies, List<Category> allCategories) {
        rideService.deleteAll();
        create(rides, allRides, allCurrencies, allCategories);
    }

    private Ride createRideFromPortModel(RidePortModel ride, Currency currency, @Nullable Category category, DistanceUnit defaultDistanceUnit) {
        if (ride.getDistanceUnit() != defaultDistanceUnit) {
            if (defaultDistanceUnit == DistanceUnit.Kilometer) {
                ride.setDistance(DistanceConversionHelper.milesToKilometers(ride.getDistance()));
            } else {
                ride.setDistance(DistanceConversionHelper.kilometersToMiles(ride.getDistance()));
            }
        }
       return new Ride(ride, currency, category);
    }
}
