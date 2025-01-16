package cz.muni.fi.pv168.project.service.port;

import cz.muni.fi.pv168.project.model.PortData;
import cz.muni.fi.pv168.project.ui.model.ImportMode;

import java.util.function.Consumer;

public interface ImportService {
    /**
     * Import data
     * @param data -
     * @param mode - import mode
     *             - create - create all unmatched rides. Matching by UUID.
     *             - createUpdate - create all missing rides and update all matched rides. Matching by UUID.
     *             - override - delete all rides and replace them with imported ones
     */
    void importData(PortData data, ImportMode mode, Consumer<Integer> setProgress);
}
