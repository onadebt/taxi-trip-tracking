package cz.muni.fi.pv168.project.service.port;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import cz.muni.fi.pv168.project.model.PortData;
import cz.muni.fi.pv168.project.ui.model.ImportMode;
import cz.muni.fi.pv168.project.utils.PathHelper;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Instant;
import java.util.function.Consumer;

public class AsyncImporter implements Importer {
    private final Gson gson = new GsonBuilder().registerTypeAdapter(Instant.class, new Gson_InstantTypeAdapter()).create();
    private final ImportService jsonImportService;
    private final Runnable onSuccess;
    private final Consumer<Double> progressReporter;

    public AsyncImporter(ImportService jsonImportService, Runnable onSuccess, Consumer<Double> progressReporter) {
        this.jsonImportService = jsonImportService;
        this.onSuccess = onSuccess;
        this.progressReporter = progressReporter;
    }

    @Override
    public void importData(String filePath, ImportMode mode) {
        PortData data;
        try (var reader = Files.newBufferedReader(Path.of(PathHelper.AddExtensionIfMissing(filePath, "json")), StandardCharsets.UTF_8)) {
            data = gson.fromJson(reader, PortData.class);
            ImportWorker importWorker = new ImportWorker(data, mode, jsonImportService, progressReporter, onSuccess);
            importWorker.execute();

        } catch (IOException ex) {
            throw new DataPortException("Could not read from specified file", ex);
        }
    }
}
