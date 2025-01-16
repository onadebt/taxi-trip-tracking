package cz.muni.fi.pv168.project.service.port;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import cz.muni.fi.pv168.project.model.*;
import cz.muni.fi.pv168.project.service.interfaces.ICategoryService;
import cz.muni.fi.pv168.project.service.interfaces.ICurrencyService;
import cz.muni.fi.pv168.project.service.interfaces.IRideService;
import cz.muni.fi.pv168.project.service.interfaces.ISettingsService;
import cz.muni.fi.pv168.project.utils.PathHelper;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Instant;
import java.util.List;

public class JsonExportService implements ExportService {
    IRideService rideService;
    ICurrencyService currencyService;
    ICategoryService categoryService;
    ISettingsService settingsService;
    Gson gson = new GsonBuilder().registerTypeAdapter(Instant.class, new Gson_InstantTypeAdapter()).setExclusionStrategies(new JsonExclusionStrategy()).create();

    public JsonExportService(IRideService rideService, ICurrencyService currencyService, ICategoryService categoryService, ISettingsService settingsService) {
        this.rideService = rideService;
        this.currencyService = currencyService;
        this.categoryService = categoryService;
        this.settingsService = settingsService;
    }

    @Override
    public void exportData(String path) {
        List<Ride> rides = rideService.findAll();
        List<Currency> currencies = currencyService.findAll();
        List<Category> categories = categoryService.findAll();

        var distUnit = settingsService.getDefaultDistance();
        List<RidePortModel> rideExports = rides.stream().map(RidePortConverter::toPortModel).toList();
        rideExports.forEach(r -> r.setDistanceUnit(distUnit));

        PortData data = new PortData(rideExports, currencies, categories);

        try (var writer = Files.newBufferedWriter(Path.of(PathHelper.AddExtensionIfMissing(path, "json")), StandardCharsets.UTF_8)) {
            gson.toJson(data, writer);
        } catch (IOException e) {
            throw new DataPortException("Could not write into specified file", e);
        }
    }
}
