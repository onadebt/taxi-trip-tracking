package cz.muni.fi.pv168.project.service.port;

import cz.muni.fi.pv168.project.model.RideDbModel;
import cz.muni.fi.pv168.project.ui.model.ImportMode;

public interface ImportService {
    void importData(String path, ImportMode mode);
}
