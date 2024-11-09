package cz.muni.fi.pv168.project.service.port;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import cz.muni.fi.pv168.project.model.CategoryDbModel;
import cz.muni.fi.pv168.project.model.CurrencyDbModel;
import cz.muni.fi.pv168.project.model.RideDbModel;
import cz.muni.fi.pv168.project.model.RidePortModel;
import cz.muni.fi.pv168.project.service.ICategoryService;
import cz.muni.fi.pv168.project.service.ICurrencyService;
import cz.muni.fi.pv168.project.service.IRideService;
import cz.muni.fi.pv168.project.service.ISettingsService;
import cz.muni.fi.pv168.project.ui.model.ImportMode;
import cz.muni.fi.pv168.project.utils.PathHelper;

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
    Gson gson = new GsonBuilder().registerTypeAdapter(Instant.class, new Gson_InstantTypeAdapter()).create();

    public JsonImportService(IRideService rideService, ICurrencyService currencyService, ICategoryService categoryService, ISettingsService settingsService) {
        this.rideService = rideService;
        this.categoryService = categoryService;
        this.currencyService = currencyService;
    }

    @Override
    public void importData(String path, ImportMode mode) {
        List<RidePortModel> data;
        try (var reader = Files.newBufferedReader(Path.of(PathHelper.AddExtensionIfMissing(path, "json")), StandardCharsets.UTF_8)) {
            data = Arrays.stream(gson.fromJson(reader, RidePortModel[].class)).toList();

        } catch (IOException ex) {
            throw new DataPortException("Could not read from specified file", ex);
        }

        var allRides = rideService.get();
        var allCategories = categoryService.get();
        var allCurrencies = currencyService.get();

        switch (mode) {
            case Create -> create(data, allRides, allCurrencies, allCategories);
            case CreateUpdate -> createUpdate(data, allRides, allCurrencies, allCategories);
            case Overwrite -> overwrite(data, allRides, allCurrencies, allCategories);
        }
    }

    private boolean validate(RidePortModel[] rides) {
        var allCategories = categoryService.get();
        var allCurrencies = currencyService.get();
        for (var ride : rides) {
            CategoryDbModel category = null;
            if (!Objects.equals(ride.getCategoryName(), "")) {
                category = allCategories.stream().filter(cat -> cat.getCategoryName().equals(ride.getCategoryName())).findFirst().orElse(null);
                if (category == null) {
                    throw new DataPortException(String.format("Category of name \"%s\" does not exist. Import aborted.", ride.getCategoryName()));
                }
            }

            var currency = allCurrencies.stream().filter(curr -> curr.getTag().equals(ride.getCurrencyTag())).findFirst().orElse(null);
            if (currency == null) {
                throw new DataPortException(String.format("Currency of tag \"%s\" does not exist. Import aborted.", ride.getCurrencyTag()));
            }

            var rideDb = new RideDbModel(0, ride.getAmountCurrency(), currency.getCurrencyId(), ride.getDistance(), category.getCategoryId(), ride.getPassengers(), ride.getTripType(), ride.getCreatedDate(), ride.getUuid());
            if (!rideService.isValid(rideDb)) {
                throw new DataPortException(String.format("Imported data is not valid.\nRideUUID: %s\nImport aborted.", ride.getUuid()));
            }
        }
        return false;
    }

    private void create(List<RidePortModel> rides, List<RideDbModel> allRides, List<CurrencyDbModel> allCurrencies, List<CategoryDbModel> allCategories) {
        for (var ride : rides) {
            if (rideService.getByUuid(ride.getUuid()) != null) {
                continue;
            }
            RideDbModel newRide = new RideDbModel();

            rideService.create(newRide);
        }
    }

    private void createUpdate(List<RidePortModel> rides, List<RideDbModel> allRides, List<CurrencyDbModel> allCurrencies, List<CategoryDbModel> allCategories) {

    }


    private void overwrite(List<RidePortModel> rides, List<RideDbModel> allRides, List<CurrencyDbModel> allCurrencies, List<CategoryDbModel> allCategories) {
        rideService.deleteAll();
        create(rides, allRides, allCurrencies, allCategories);
    }
}
