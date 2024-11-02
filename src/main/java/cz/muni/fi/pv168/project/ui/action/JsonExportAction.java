package cz.muni.fi.pv168.project.ui.action;

import cz.muni.fi.pv168.project.service.IRideService;
import cz.muni.fi.pv168.project.service.port.DataPortException;
import cz.muni.fi.pv168.project.service.port.JsonExportService;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.ActionEvent;

public class JsonExportAction extends AbstractAction {
    private final JComponent parent;
    private final JsonExportService jsonExportService;
    private final IRideService rideService;

    public JsonExportAction(JComponent parent, JsonExportService jsonExportService, IRideService rideService) {
        this.parent = parent;
        this.jsonExportService = jsonExportService;
        this.rideService = rideService;
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
                jsonExportService.exportData(chooser.getSelectedFile().getPath(), rideService.get());
            } catch (DataPortException ex) {
                JOptionPane.showMessageDialog(parent, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }

        }
    }
}
