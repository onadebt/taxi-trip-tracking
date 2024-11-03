package cz.muni.fi.pv168.project.service.port;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import cz.muni.fi.pv168.project.model.RideDbModel;
import cz.muni.fi.pv168.project.service.IRideService;
import cz.muni.fi.pv168.project.ui.model.ImportMode;
import cz.muni.fi.pv168.project.utils.PathHelper;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Instant;

public class JsonImportService implements ImportService {
    IRideService rideService;
    Gson gson = new GsonBuilder().registerTypeAdapter(Instant.class, new Gson_InstantTypeAdapter()).create();

    public JsonImportService(IRideService rideService) {
        this.rideService = rideService;
    }

    @Override
    public void importData(String path, ImportMode mode) {
        RideDbModel[] data;
        try (var reader = Files.newBufferedReader(Path.of(PathHelper.AddExtensionIfMissing(path, "json")), StandardCharsets.UTF_8)) {
            data = gson.fromJson(reader, RideDbModel[].class);
        } catch (IOException ex) {
            throw new DataPortException("Could not read from specified file", ex);
        }
        if (!isValid(data)) {
            throw new DataPortException("Imported data is not in the right format");
        }

        switch (mode) {
            case AddAll -> addAll(data);
            case Update -> update(data);
            case AddSafe -> addSane(data);
            case Overwrite -> overwrite(data);
        }
    }

    @Override
    public boolean isValid(RideDbModel[] rides) {
        for (var ride : rides) {
            rideService.isValid(ride);
        }
        return false;
    }

    private void addAll(RideDbModel[] rides) {
        for (var ride : rides) {
            rideService.create(ride);
        }
    }

    private void addSane(RideDbModel[] rides) {

    }

    private void update(RideDbModel[] rides) {
        for (var ride : rides) {

        }
    }

    private void overwrite(RideDbModel[] rides) {

    }
}
