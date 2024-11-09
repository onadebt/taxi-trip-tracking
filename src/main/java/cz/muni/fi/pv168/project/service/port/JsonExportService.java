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
import cz.muni.fi.pv168.project.utils.PathHelper;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class JsonExportService implements ExportService {
    IRideService rideService;
    ICurrencyService currencyService;
    ICategoryService categoryService;
    ISettingsService settingsService;
    Gson gson = new GsonBuilder().registerTypeAdapter(Instant.class, new Gson_InstantTypeAdapter()).create();

    public JsonExportService(IRideService rideService, ICurrencyService currencyService, ICategoryService categoryService, ISettingsService settingsService) {
        this.rideService = rideService;
        this.currencyService = currencyService;
        this.categoryService = categoryService;
        this.settingsService = settingsService;
    }

    @Override
    public void exportData(String path) {
        List<RideDbModel> rides = rideService.get();
        List<CategoryDbModel> categories = categoryService.get();
        List<CurrencyDbModel> currencies = currencyService.get();

        List<RidePortModel> rideExports = new ArrayList<>();
        for (var ride : rides) {
            var catName = ride.getCategoryId() == null ?
                    categories.stream()
                    .filter(cat -> cat.getCategoryId() == ride.getCategoryId())
                    .map(CategoryDbModel::getCategoryName)
                    .findFirst().orElse("")
                    : "";
            var currTag = currencies.stream()
                    .filter(curr -> curr.getCurrencyId() == ride.getCurrencyId())
                    .map(CurrencyDbModel::getTag)
                    .findFirst().orElseThrow();
            var distUnit = settingsService.getDefaultDistance();

            rideExports.add(new RidePortModel(ride, catName, currTag, distUnit));
        }
        try (var writer = Files.newBufferedWriter(Path.of(PathHelper.AddExtensionIfMissing(path, "json")), StandardCharsets.UTF_8)) {
            gson.toJson(rideExports, writer);
        } catch (IOException e) {
            throw new DataPortException("Could not write into specified file", e);
        }
    }
}
