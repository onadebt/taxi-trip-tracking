package cz.muni.fi.pv168.project.ui.action;

import cz.muni.fi.pv168.project.service.interfaces.IRideService;
import cz.muni.fi.pv168.project.service.port.DataPortException;
import cz.muni.fi.pv168.project.service.port.ExportService;
import cz.muni.fi.pv168.project.service.port.ExportWorker;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.ActionEvent;

public class JsonExportAction extends AbstractAction {
    private final JComponent parent;
    private final ExportService jsonExportService;

    public JsonExportAction(JComponent parent, ExportService jsonExportService, IRideService rideService) {
        super("Export");
        this.parent = parent;
        this.jsonExportService = jsonExportService;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        JFileChooser chooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter(
                "JSON files", "json");
        chooser.setFileFilter(filter);
        int returnVal = chooser.showOpenDialog(null);
        if(returnVal == JFileChooser.APPROVE_OPTION) {
            try {
                new ExportWorker(chooser.getSelectedFile().getPath(), jsonExportService, this::onSuccess).execute();
            } catch (DataPortException ex) {
                JOptionPane.showMessageDialog(parent, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void onSuccess() {
        JOptionPane.showMessageDialog(parent, "Export successful");
    }
}
