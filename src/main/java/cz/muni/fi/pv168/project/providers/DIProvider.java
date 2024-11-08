package cz.muni.fi.pv168.project.providers;

import cz.muni.fi.pv168.project.repository.ICategoryRepository;
import cz.muni.fi.pv168.project.repository.ICurrencyRepository;
import cz.muni.fi.pv168.project.repository.IRideRepository;
import cz.muni.fi.pv168.project.repository.ISettingsRepository;
import cz.muni.fi.pv168.project.service.ICategoryService;
import cz.muni.fi.pv168.project.service.ICurrencyService;
import cz.muni.fi.pv168.project.service.IRideService;
import cz.muni.fi.pv168.project.service.ISettingsService;
import cz.muni.fi.pv168.project.service.port.ExportService;
import cz.muni.fi.pv168.project.service.port.ImportService;

public class DIProvider {
    private IRideRepository rideRepository;
    private ICurrencyRepository currencyRepository;
    private ICategoryRepository categoryRepository;
    private ISettingsRepository settingsRepository;

    private IRideService rideService;
    private ICategoryService categoryService;
    private ICurrencyService currencyService;
    private ISettingsService settingsService;
    private ExportService jsonExportService;
    private ImportService jsonImportService;

    public DIProvider() {
        // TODO - implement
    }
}
