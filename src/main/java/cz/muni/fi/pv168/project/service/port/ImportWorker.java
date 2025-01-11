package cz.muni.fi.pv168.project.service.port;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import cz.muni.fi.pv168.project.model.PortData;
import cz.muni.fi.pv168.project.service.interfaces.ICategoryService;
import cz.muni.fi.pv168.project.service.interfaces.ICurrencyService;
import cz.muni.fi.pv168.project.service.interfaces.IRideService;
import cz.muni.fi.pv168.project.service.interfaces.ISettingsService;
import cz.muni.fi.pv168.project.ui.model.ImportMode;
import cz.muni.fi.pv168.project.utils.PathHelper;

import javax.swing.*;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Instant;
import java.util.function.Consumer;

public class ImportWorker extends SwingWorker<Void, Integer> {
    private Gson gson = new GsonBuilder().registerTypeAdapter(Instant.class, new Gson_InstantTypeAdapter()).create();

    private String importFile;
    private Consumer<PortData> importFunction;



    public ImportWorker(String importFile, Consumer<PortData> importFunction) {
        this.importFile = importFile;
        this.importFunction = importFunction;
    }

    @Override
    protected Void doInBackground() throws Exception {
        PortData data;
        try (var reader = Files.newBufferedReader(Path.of(PathHelper.AddExtensionIfMissing(importFile, "json")), StandardCharsets.UTF_8)) {
            data = gson.fromJson(reader, PortData.class);

        } catch (IOException ex) {
            throw new DataPortException("Could not read from specified file", ex);
        }

        importFunction.accept(data);
        return null;
    }

    @Override
    protected void done() {

    }
}
