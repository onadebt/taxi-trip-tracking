package cz.muni.fi.pv168.project.service.port;

public interface ExportService {
    /**
     * Export rides into JSON file
     * @param path - Absolute path to JSON file
     */
    void exportData(String path);
}
