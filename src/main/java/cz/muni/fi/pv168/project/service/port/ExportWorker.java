package cz.muni.fi.pv168.project.service.port;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import cz.muni.fi.pv168.project.database.TransactionException;

import javax.swing.*;
import java.time.Instant;

public class ExportWorker extends SwingWorker<Void, Void> {
    private final Gson gson = new GsonBuilder().registerTypeAdapter(Instant.class, new Gson_InstantTypeAdapter()).create();

    private final String path;
    private final ExportService jsonExportService;
    private final Runnable onSuccess;

    public ExportWorker(String path, ExportService jsonExportService, Runnable onSuccess) {
        this.jsonExportService = jsonExportService;
        this.onSuccess = onSuccess;
        this.path = path;
    }

    @Override
    protected Void doInBackground() {
        try {
            jsonExportService.exportData(path);
        } catch (TransactionException ex) {
            throw new DataPortException(ex.getMessage(), ex);
        }
        return null;
    }

    @Override
    protected void done() {
        super.done();
        onSuccess.run();
    }
}
