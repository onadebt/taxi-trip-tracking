package cz.muni.fi.pv168.project.service.port;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import cz.muni.fi.pv168.project.model.RideDbModel;
import cz.muni.fi.pv168.project.service.IRideService;
import cz.muni.fi.pv168.project.utils.PathHelper;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Instant;
import java.util.List;

public class JsonExportService implements ExportService {
    IRideService rideService;
    Gson gson = new GsonBuilder().registerTypeAdapter(Instant.class, new Gson_InstantTypeAdapter()).create();

    public JsonExportService(IRideService rideService) {
        this.rideService = rideService;
    }

    @Override
    public void exportData(String path) {
        List<RideDbModel> rides = rideService.get();
        try (var writer = Files.newBufferedWriter(Path.of(PathHelper.AddExtensionIfMissing(path, "json")), StandardCharsets.UTF_8)) {
            gson.toJson(rides, writer);
        } catch (IOException e) {
            throw new DataPortException("Could not write into specified file", e);
        }
    }
}
