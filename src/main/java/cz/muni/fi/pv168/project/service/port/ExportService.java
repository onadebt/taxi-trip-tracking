package cz.muni.fi.pv168.project.service.port;

import cz.muni.fi.pv168.project.model.RideDbModel;

import java.util.List;

public interface ExportService {
    void exportData(String path, List<RideDbModel> data);
}
