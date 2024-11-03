package cz.muni.fi.pv168.project.ui.action;

import cz.muni.fi.pv168.project.service.port.DataPortException;
import cz.muni.fi.pv168.project.service.port.ImportService;
import cz.muni.fi.pv168.project.ui.dialog.ImportDialog;
import cz.muni.fi.pv168.project.ui.model.ImportMode;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.ActionEvent;

public class JsonImportAction extends AbstractAction {
    private final JComponent parent;
    private final ImportService jsonImportService;
    public JsonImportAction(JComponent parent, ImportService jsonImportService) {
        super("Import");
        this.parent = parent;
        this.jsonImportService = jsonImportService;
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
                jsonImportService.importData(chooser.getSelectedFile().getPath(), mode);
            } catch (DataPortException ex) {
                JOptionPane.showMessageDialog(parent, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }

        }
    }
}
