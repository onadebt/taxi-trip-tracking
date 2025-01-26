package cz.muni.fi.pv168.project.service.port;

import cz.muni.fi.pv168.project.database.TransactionException;
import cz.muni.fi.pv168.project.model.PortData;
import cz.muni.fi.pv168.project.model.exception.ValidationException;
import cz.muni.fi.pv168.project.ui.model.ImportMode;


import javax.swing.*;
import java.util.List;
import java.util.function.Consumer;

public class ImportWorker extends SwingWorker<Void, Integer> {
    private final ImportService jsonImportService;

    private final PortData data;
    private final ImportMode mode;
    private final double numberOfRows;

    private final Consumer<Double> progressReporter;
    private final Runnable onFinish;


    public ImportWorker(PortData data, ImportMode mode, ImportService jsonImportService, Consumer<Double> progressReporter, Runnable onFinish ) {
        this.data = data;
        this.jsonImportService = jsonImportService;
        this.progressReporter = progressReporter;
        this.onFinish = onFinish;
        this.mode = mode;
        this.numberOfRows = data.getRides().size() + data.getCategories().size() + data.getCurrencies().size();
    }

    @Override
    protected Void doInBackground() throws DataPortException {
        try {
            jsonImportService.importData(data, mode, this::publish);
        } catch (TransactionException | ValidationException ex) {
            publish(0);
            throw ex;
        }
        return null;
    }

    @Override
    protected void done() {
        super.done();
        progressReporter.accept((double) 0);
        try{
            get();
            onFinish.run();
        } catch (Exception ex) {
            SwingUtilities.invokeLater(() -> JOptionPane.showMessageDialog(null, ex.getCause().getMessage(), "Error", JOptionPane.ERROR_MESSAGE));
        }
    }

    @Override
    protected void process(List<Integer> chunks) {
        progressReporter.accept(numberOfRows / chunks.get(chunks.size() - 1));
    }
}
