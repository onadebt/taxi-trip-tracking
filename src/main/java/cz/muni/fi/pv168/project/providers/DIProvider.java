package cz.muni.fi.pv168.project.providers;

import cz.muni.fi.pv168.project.database.DatabaseManager;
import cz.muni.fi.pv168.project.database.dao.CurrencyDataAccessObject;
import cz.muni.fi.pv168.project.database.mapper.EntityMapper;
import cz.muni.fi.pv168.project.model.Currency;
import cz.muni.fi.pv168.project.model.CurrencyDbModel;
import cz.muni.fi.pv168.project.repository.*;
import cz.muni.fi.pv168.project.service.*;
import cz.muni.fi.pv168.project.service.interfaces.ICategoryService;
import cz.muni.fi.pv168.project.service.interfaces.ICurrencyService;
import cz.muni.fi.pv168.project.service.interfaces.IRideService;
import cz.muni.fi.pv168.project.service.interfaces.ISettingsService;
import cz.muni.fi.pv168.project.service.port.ExportService;
import cz.muni.fi.pv168.project.service.port.ImportService;
import cz.muni.fi.pv168.project.service.port.JsonExportService;
import cz.muni.fi.pv168.project.service.port.JsonImportService;

public class DIProvider {
    //private final DatabaseManager databaseManager;

    private CurrencyDataAccessObject currencyDao;
    private EntityMapper<CurrencyDbModel, Currency> currencyMapper;

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

        this.categoryRepository = new CategoryRepository();
        this.currencyRepository = new CurrencyRepository(currencyDao, currencyMapper);
        this.settingsRepository = new SettingsRepository();
        this.rideRepository = new RideRepository(currencyRepository, categoryRepository);

        this.currencyService = new CurrencyService(currencyRepository);
        this.categoryService = new CategoryService(categoryRepository);
        this.settingsService = new SettingsService(settingsRepository);
        this.rideService = new RideService(categoryService, currencyService, rideRepository);
        this.jsonExportService = new JsonExportService(rideService, currencyService, categoryService, settingsService);
        this.jsonImportService = new JsonImportService(rideService, currencyService, categoryService, settingsService);
        //this.databaseManager = DatabaseManager.createProductionInstance();
        //this.databaseManager.initSchema();
    }

    public IRideRepository getRideRepository() {
        return rideRepository;
    }

    public ICurrencyRepository getCurrencyRepository() {
        return currencyRepository;
    }

    public ICategoryRepository getCategoryRepository() {
        return categoryRepository;
    }

    public ISettingsRepository getSettingsRepository() {
        return settingsRepository;
    }

    public IRideService getRideService() {
        return rideService;
    }

    public ICategoryService getCategoryService() {
        return categoryService;
    }

    public ICurrencyService getCurrencyService() {
        return currencyService;
    }

    public ISettingsService getSettingsService() {
        return settingsService;
    }

    public ExportService getJsonExportService() {
        return jsonExportService;
    }

    public ImportService getJsonImportService() {
        return jsonImportService;
    }
}
