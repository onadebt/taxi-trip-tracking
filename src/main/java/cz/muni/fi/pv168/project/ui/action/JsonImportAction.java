package cz.muni.fi.pv168.project.ui.action;

import cz.muni.fi.pv168.project.model.exception.ValidationException;
import cz.muni.fi.pv168.project.service.port.*;
import cz.muni.fi.pv168.project.ui.dialog.ImportDialog;
import cz.muni.fi.pv168.project.ui.model.ImportMode;
import cz.muni.fi.pv168.project.ui.model.RideTableModel;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.ActionEvent;
import java.util.function.Consumer;

public class JsonImportAction extends AbstractAction {
    private final JComponent parent;
    private final ImportService jsonImportService;
    JProgressBar progressBar;
    private final RideTableModel rideTableModel;


    public JsonImportAction(JComponent parent, ImportService jsonImportService, JProgressBar progressBar, RideTableModel rideTableModel) {
        super("Import");
        this.parent = parent;
        this.jsonImportService = jsonImportService;
        this.progressBar = progressBar;
        this.rideTableModel = rideTableModel;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        var modeDialog = new ImportDialog();
        var result = modeDialog.show(parent, "Import mode");
        if (result.isEmpty()) {
            JOptionPane.showMessageDialog(parent, "Import mode has not been selected. Import terminated.");
            return;
        }
        performImport(result.get());
    }

    private void performImport(ImportMode mode) {
        JFileChooser chooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter(
                "JSON files", "json");
        chooser.setFileFilter(filter);
        int returnVal = chooser.showOpenDialog(null);
        if(returnVal == JFileChooser.APPROVE_OPTION) {
            try {
                new JsonAsyncImporter(jsonImportService, this::onSuccess, (i -> progressBar.setValue((int) Math.round(i) * 100))).importData(chooser.getSelectedFile().getPath(), mode);
            } catch (ValidationException ex) {
                JOptionPane.showMessageDialog(parent, ex.getMessage(), "Validation Error", JOptionPane.ERROR_MESSAGE);
            } catch (DataPortException ex) {
                JOptionPane.showMessageDialog(parent, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void onSuccess() {
        SwingUtilities.invokeLater(() -> {
            rideTableModel.refresh();
            JOptionPane.showMessageDialog(parent, "Import successful", "Import", JOptionPane.INFORMATION_MESSAGE);
        });
    }
}
