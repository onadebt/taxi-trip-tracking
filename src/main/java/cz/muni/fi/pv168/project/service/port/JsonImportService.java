package cz.muni.fi.pv168.project.service.port;

import cz.muni.fi.pv168.project.database.TransactionExecutor;
import cz.muni.fi.pv168.project.model.*;
import cz.muni.fi.pv168.project.model.enums.DistanceUnit;
import cz.muni.fi.pv168.project.service.interfaces.*;
import cz.muni.fi.pv168.project.service.validation.Validator;
import cz.muni.fi.pv168.project.ui.model.ImportMode;
import cz.muni.fi.pv168.project.utils.DistanceConversionHelper;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;

public class JsonImportService implements ImportService {
    IRideService rideService;
    ICurrencyService currencyService;
    ICategoryService categoryService;
    ISettingsService settingsService;
    Validator<Ride> rideValidator;

    TransactionExecutor transactionExecutor;

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
                        case Create -> create(data, setProgress);
                        case CreateUpdate -> createUpdate(data, setProgress);
                        case Overwrite -> overwrite(data, setProgress);
                    }
                }
        );

    }

    private void create(PortData data, Consumer<Integer> setProgress) {
        var defaultDistUnit = settingsService.getDefaultDistance();
        var processedRows = 0;
        for (var currency : data.getCurrencies()) {
            var foundCurrency = currencyService.findByCode(currency.getCode());
            if (foundCurrency.isEmpty()) {
                currencyService.create(currency);
            }
            processedRows++;
            setProgress.accept(processedRows);
        }

        for (var category : data.getCategories()) {
            category.setIconPath(category.getIconPath());
            var foundCategory = categoryService.findByName(category.getName());
            if (foundCategory.isEmpty()) {
                categoryService.create(category);
            }
            processedRows++;
            setProgress.accept(processedRows);
        }

        for (var ride : data.getRides()) {
            if (rideService.findAll().stream().anyMatch(r -> r.getUuid().equals(ride.getUuid()))) {
                processedRows++;
                setProgress.accept(processedRows);
                continue;
            }

            var currency = currencyService.findByCode(ride.getCurrencyTag());
            var category = categoryService.findByName(ride.getCategoryName());

            rideService.create(createRideFromPortModel(ride, currency.get(), category.orElse(null), defaultDistUnit));
            processedRows++;
            setProgress.accept(processedRows);
        }
    }

    private void createUpdate(PortData data, Consumer<Integer> setProgress) {
        var defaultDistUnit = settingsService.getDefaultDistance();
        var processedRows = 0;
        for (var currency : data.getCurrencies()) {
            var foundCurrency = currencyService.findByCode(currency.getCode());
            if (foundCurrency.isEmpty()) {
                currencyService.create(currency);
            } else {
                currency.setId(foundCurrency.get().getId());
                currencyService.update(currency);
            }
            processedRows++;
            setProgress.accept(processedRows);
        }

        for (var category : data.getCategories()) {
            category.setIconPath(category.getIconPath());
            var foundCategory = categoryService.findByName(category.getName());
            if (foundCategory.isEmpty()) {
                categoryService.create(category);
            } else {
                category.setId(foundCategory.get().getId());
                categoryService.update(category);
            }
            processedRows++;
            setProgress.accept(processedRows);
        }
        for (var ride : data.getRides()) {
            var currency = currencyService.findByCode(ride.getCurrencyTag());
            var category = categoryService.findByName(ride.getCategoryName());

            if (rideService.findAll().stream().anyMatch(r -> r.getUuid().equals(ride.getUuid()))) {
                rideService.update(createRideFromPortModel(ride, currency.get(), category.orElse(null), defaultDistUnit));
            } else {
                rideService.create(createRideFromPortModel(ride, currency.get(), category.orElse(null), defaultDistUnit));
            }
            processedRows++;
            setProgress.accept(processedRows);
        }
    }

    private void overwrite(PortData data, Consumer<Integer> setProgress) {
        rideService.deleteAll();
        currencyService.deleteAll();
        categoryService.deleteAll();
        create(data, setProgress);
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
