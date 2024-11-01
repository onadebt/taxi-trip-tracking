package cz.muni.fi.pv168.project.service.port;

import cz.muni.fi.pv168.project.service.IRideService;

public class JsonExportService implements ExportService {
    IRideService rideService;

    public JsonExportService(IRideService rideService) {
        this.rideService = rideService;
    }

    @Override
    public void exportData(String path) {

    }
}
