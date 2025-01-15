package cz.muni.fi.pv168.project.service.port;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import cz.muni.fi.pv168.project.database.TransactionException;
import cz.muni.fi.pv168.project.database.TransactionExecutor;
import cz.muni.fi.pv168.project.model.PortData;
import cz.muni.fi.pv168.project.ui.model.ImportMode;
import cz.muni.fi.pv168.project.utils.PathHelper;

import javax.swing.*;
import java.time.Instant;
import java.util.List;
import java.util.function.Consumer;

public class ImportWorker extends SwingWorker<Void, Integer> {
    private final Gson gson = new GsonBuilder().registerTypeAdapter(Instant.class, new Gson_InstantTypeAdapter()).create();
    private final ImportService jsonImportService;

    private final PortData data;
    private final ImportMode mode;

    private final Consumer<Double> progressReporter;
    private final Runnable onFinish;


    public ImportWorker(PortData data, ImportMode mode, ImportService jsonImportService, Consumer<Double> progressReporter, Runnable onFinish ) {
        this.data = data;
        this.jsonImportService = jsonImportService;
        this.progressReporter = progressReporter;
        this.onFinish = onFinish;
        this.mode = mode;
    }

    @Override
    protected Void doInBackground() throws DataPortException {
        try {
            jsonImportService.importData(data, mode, this::publish);
        } catch (TransactionException ex) {
            throw new DataPortException(ex.getMessage(), ex);
        }
        return null;
    }

    @Override
    protected void done() {
        super.done();
        onFinish.run();
    }

    @Override
    protected void process(List<Integer> chunks) {

    }
}
