package cz.muni.fi.pv168.project.service.port;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import cz.muni.fi.pv168.project.database.TransactionExecutor;
import cz.muni.fi.pv168.project.model.*;
import cz.muni.fi.pv168.project.model.enums.DistanceUnit;
import cz.muni.fi.pv168.project.model.exception.ValidationException;
import cz.muni.fi.pv168.project.repository.ICategoryRepository;
import cz.muni.fi.pv168.project.repository.ICurrencyRepository;
import cz.muni.fi.pv168.project.repository.IRideRepository;
import cz.muni.fi.pv168.project.repository.ISettingsRepository;
import cz.muni.fi.pv168.project.service.interfaces.*;
import cz.muni.fi.pv168.project.service.validation.Validator;
import cz.muni.fi.pv168.project.ui.model.ImportMode;
import cz.muni.fi.pv168.project.utils.DistanceConversionHelper;
import cz.muni.fi.pv168.project.utils.PathHelper;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;

public class JsonImportService implements ImportService {
    IRideService rideService;
    ICurrencyService currencyService;
    ICategoryService categoryService;
    ISettingsService settingsService;
    Validator<Ride> rideValidator;

    TransactionExecutor transactionExecutor;

    Gson gson = new GsonBuilder().registerTypeAdapter(Instant.class, new Gson_InstantTypeAdapter()).create();

    public JsonImportService(IRideService rideService, ICurrencyService currencyService, ICategoryService categoryService, ISettingsService settingsService, Validator<Ride> rideValidator,
                             TransactionExecutor transactionExecutor) {
        this.rideService = rideService;
        this.categoryService = categoryService;
        this.currencyService = currencyService;
        this.settingsService = settingsService;
        this.rideValidator = rideValidator;
        this.transactionExecutor = transactionExecutor;
    }

    @Override
    public void importData(PortData data, ImportMode mode, Consumer<Integer> setProgress) {
        transactionExecutor.executeInTransaction(
                () -> {
                    switch (mode) {
                        case Create -> create(data);
                        case CreateUpdate -> createUpdate(data);
                        case Overwrite -> overwrite(data);
                    }
                }
        );

    }

    private void create(PortData data) {
        var defaultDistUnit = settingsService.getDefaultDistance();
        for (var currency : data.getCurrencies()) {
            var foundCurrency = currencyService.findByCode(currency.getCode());
            if (foundCurrency.isEmpty()) {
                currencyService.create(currency);
            }
        }

        for (var category : data.getCategories()) {
            var foundCategory = categoryService.findByName(category.getName());
            if (foundCategory.isEmpty()) {
                categoryService.create(category);
            }
        }

        for (var ride : data.getRides()) {
            if (rideService.findAll().stream().anyMatch(r -> r.getUuid() == ride.getUuid())) {
                continue;
            }

            var currency = currencyService.findByCode(ride.getCurrencyTag());
            var category = categoryService.findByName(ride.getCategoryName());

            rideService.create(createRideFromPortModel(ride, currency.get(), category.orElse(null), defaultDistUnit));
        }
    }

    private void createUpdate(PortData data) {
        var defaultDistUnit = settingsService.getDefaultDistance();
        for (var currency : data.getCurrencies()) {
            var foundCurrency = currencyService.findByCode(currency.getCode());
            if (foundCurrency.isEmpty()) {
                currencyService.create(currency);
            } else {
                currency.setId(foundCurrency.get().getId());
                currencyService.update(currency);
            }
        }

        for (var category : data.getCategories()) {
            var foundCategory = categoryService.findByName(category.getName());
            if (foundCategory.isEmpty()) {
                categoryService.create(category);
            } else {
                category.setId(foundCategory.get().getId());
                categoryService.update(category);
            }
        }
        for (var ride : data.getRides()) {
            var currency = currencyService.findByCode(ride.getCurrencyTag());
            var category = categoryService.findByName(ride.getCategoryName());

            if (rideService.findAll().stream().anyMatch(r -> r.getUuid().equals(ride.getUuid()))) {
                rideService.update(createRideFromPortModel(ride, currency.get(), category.orElse(null), defaultDistUnit));
            } else {
                rideService.create(createRideFromPortModel(ride, currency.get(), category.orElse(null), defaultDistUnit));
            }
        }
    }

    private void overwrite(PortData data) {
        rideService.deleteAll();
        currencyService.deleteAll();
        categoryService.deleteAll();
        create(data);
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
