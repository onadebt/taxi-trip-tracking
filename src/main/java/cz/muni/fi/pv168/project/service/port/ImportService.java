package cz.muni.fi.pv168.project.service.port;

import cz.muni.fi.pv168.project.model.RideDbModel;
import cz.muni.fi.pv168.project.ui.model.ImportMode;

public interface ImportService {
    /**
     * Import data
     * @param path - absolute path to JSON file
     * @param mode - import mode
     *             - create - create all unmatched rides. Matching by UUID.
     *             - createUpdate - create all missing rides and update all matched rides. Matching by UUID.
     *             - override - delete all rides and replace them with imported ones
     */
    void importData(String path, ImportMode mode);
}
