package cz.muni.fi.pv168.project.ui.dialog;


import cz.muni.fi.pv168.project.ui.model.ImportMode;

import javax.swing.*;

public class ImportDialog extends EntityDialog<ImportMode> {
    ButtonGroup selection = new ButtonGroup();
    JRadioButton overwrite = new JRadioButton("Overwrite all rides with imported");
    JRadioButton create = new JRadioButton("Add all missing rides, do not update existing");
    JRadioButton createUpdate = new JRadioButton("Add all missing rides, update existing");

    public ImportDialog() {
        selection.add(create);
        selection.add(createUpdate);
        selection.add(overwrite);
        create.setSelected(true);
        panel.add(create);
        panel.add(createUpdate);
        panel.add(overwrite);
    }

    @Override
    ImportMode getEntity() {
        if (overwrite.isSelected()) {
            return ImportMode.Overwrite;
        } else if (create.isSelected()) {
            return ImportMode.Create;
        } else {
            return ImportMode.CreateUpdate;
        }
    }
}
