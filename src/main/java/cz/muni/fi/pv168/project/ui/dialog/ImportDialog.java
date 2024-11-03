package cz.muni.fi.pv168.project.ui.dialog;


import cz.muni.fi.pv168.project.ui.model.ImportMode;

import javax.swing.*;

public class ImportDialog extends EntityDialog<ImportMode> {
    ButtonGroup selection = new ButtonGroup();
    JRadioButton overwrite = new JRadioButton("Overwrite all rides");
    JRadioButton update = new JRadioButton("Update rides from source file. Matching by ride ID");
    JRadioButton addSane = new JRadioButton("Add only rides ");
    JRadioButton addAll = new JRadioButton("Add all rides from source file");

    public ImportDialog() {
        selection.add(overwrite);
        selection.add(update);
        selection.add(addSane);
        selection.add(addAll);
        panel.add(overwrite);
        panel.add(update);
        panel.add(addSane);
        panel.add(addAll);
    }

    @Override
    ImportMode getEntity() {
        if (overwrite.isSelected()) {
            return ImportMode.Overwrite;
        } else if (addSane.isSelected()) {
            return ImportMode.AddSafe;
        } else if (update.isSelected()) {
            return ImportMode.Update;
        } else {
            return ImportMode.AddAll;
        }
    }
}
