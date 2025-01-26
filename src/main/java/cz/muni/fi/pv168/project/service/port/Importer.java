package cz.muni.fi.pv168.project.service.port;

import cz.muni.fi.pv168.project.ui.model.ImportMode;

public interface Importer {
    void importData(String filePath, ImportMode mode);
}
