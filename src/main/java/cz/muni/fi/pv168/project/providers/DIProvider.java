package cz.muni.fi.pv168.project.providers;

import cz.muni.fi.pv168.project.database.DatabaseManager;
import cz.muni.fi.pv168.project.database.dao.CurrencyDao;
import cz.muni.fi.pv168.project.database.dao.CurrencyDataAccessObject;
import cz.muni.fi.pv168.project.database.mapper.CurrencyMapper;
import cz.muni.fi.pv168.project.database.mapper.EntityMapper;
import cz.muni.fi.pv168.project.model.Category;
import cz.muni.fi.pv168.project.model.Currency;
import cz.muni.fi.pv168.project.model.CurrencyDbModel;
import cz.muni.fi.pv168.project.model.Ride;
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
import cz.muni.fi.pv168.project.service.validation.CurrencyValidator;
import cz.muni.fi.pv168.project.service.validation.Validator;

public class DIProvider {
    private final DatabaseManager databaseManager;

    private CurrencyDataAccessObject currencyDao;
    private EntityMapper<CurrencyDbModel, Currency> currencyMapper;

    private RideRepository rideRepository;
    private Repository<Currency> currencyRepository;
    private Repository<Category> categoryRepository;
    private ISettingsRepository settingsRepository;

    private IRideService rideService;
    private ICategoryService categoryService;
    private ICurrencyService currencyService;
    private ISettingsService settingsService;
    private ExportService jsonExportService;
    private ImportService jsonImportService;

    public DIProvider() {

        this.databaseManager = DatabaseManager.createProductionInstance();
        this.databaseManager.initSchema();
        this.categoryRepository = new CategoryRepository();
        this.currencyDao = new CurrencyDao(databaseManager::getConnectionHandler);
        this.currencyMapper = new CurrencyMapper();
        this.currencyRepository = new CurrencyRepository(currencyDao, currencyMapper);
        this.settingsRepository = new SettingsRepository();
        this.rideRepository = new RideRepository(currencyRepository, categoryRepository);

        this.categoryService = new CategoryService(categoryRepository);
        this.settingsService = new SettingsService(settingsRepository);
        this.rideService = new RideService(categoryService, currencyService, rideRepository);
        this.jsonExportService = new JsonExportService(rideService, currencyService, categoryService, settingsService);
        this.jsonImportService = new JsonImportService(rideService, currencyService, categoryService, settingsService);

    }

    public RideRepository getRideRepository() {
        return rideRepository;
    }

    public Repository<Currency> getCurrencyRepository() {
        return currencyRepository;
    }

    public Repository<Category> getCategoryRepository() {
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
