package cz.muni.fi.pv168.project.ui.action;

import cz.muni.fi.pv168.project.service.port.*;
import cz.muni.fi.pv168.project.ui.dialog.ImportDialog;
import cz.muni.fi.pv168.project.ui.model.ImportMode;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.ActionEvent;
import java.util.function.Consumer;

public class JsonImportAction extends AbstractAction {
    private final JComponent parent;
    private final ImportService jsonImportService;
    JProgressBar progressBar;


    public JsonImportAction(JComponent parent, ImportService jsonImportService, JProgressBar progressBar) {
        super("Import");
        this.parent = parent;
        this.jsonImportService = jsonImportService;
        this.progressBar = progressBar;
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
            } catch (DataPortException ex) {
                JOptionPane.showMessageDialog(parent, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        // TODO - refresh ridesHistory and HomePage rides and stats
    }

    private void onSuccess() {
        JOptionPane.showMessageDialog(parent, "Import successful", "Import", JOptionPane.INFORMATION_MESSAGE);
    }
}
