package cz.muni.fi.pv168.project.providers;

import cz.muni.fi.pv168.project.database.*;
import cz.muni.fi.pv168.project.database.dao.*;
import cz.muni.fi.pv168.project.database.mappers.*;
import cz.muni.fi.pv168.project.model.*;
import cz.muni.fi.pv168.project.model.DbModels.CategoryDbModel;
import cz.muni.fi.pv168.project.model.DbModels.CurrencyDbModel;
import cz.muni.fi.pv168.project.model.DbModels.RideDbModel;
import cz.muni.fi.pv168.project.model.DbModels.SettingsDbModel;
import cz.muni.fi.pv168.project.repository.*;
import cz.muni.fi.pv168.project.repository.interfaces.*;
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
    private final DatabaseManager databaseManager;
    private final TransactionExecutor transactionExecutor;
    private final TransactionManager transactionManager;

    private CategoryDataAccessObject categoryDao;
    private CurrencyDataAccessObject currencyDao;
    private RideDao rideDao;
    private SettingsDao settingsDao;

    private EntityMapper<CategoryDbModel, Category> categoryMapper;
    private EntityMapper<CurrencyDbModel, Currency> currencyMapper;
    private EntityMapper<RideDbModel, Ride> rideMapper;
    private EntityMapper<SettingsDbModel, Settings> settingsMapper;

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

        this.databaseManager = DatabaseManager.createProductionInstance();
        this.databaseManager.initSchema();

        this.transactionManager = new TransactionManagerImpl(databaseManager);
        this.transactionExecutor = new TransactionExecutorImpl(transactionManager::beginTransaction);


        ValidatorProvider validatorProvider = new ValidatorProvider();

        this.categoryDao = new CategoryDao(databaseManager::getConnectionHandler);
        this.categoryMapper = new CategoryMapper();
        this.categoryRepository = new CategoryRepository(categoryDao, categoryMapper);

        this.currencyDao = new CurrencyDao(databaseManager::getConnectionHandler);
        this.currencyMapper = new CurrencyMapper();
        this.currencyRepository = new CurrencyRepository(currencyDao, currencyMapper);

        this.rideMapper = new RideMapper(categoryDao, categoryMapper, currencyDao, currencyMapper);
        this.rideDao = new RideDao(databaseManager::getConnectionHandler);
        this.rideRepository = new RideRepository(rideDao, rideMapper);

        this.settingsDao = new SettingsDao(databaseManager::getConnectionHandler);
        this.settingsMapper = new SettingsMapper();
        this.settingsRepository = new SettingsRepository(settingsDao, settingsMapper);


        this.categoryService = new CategoryService(categoryRepository, validatorProvider.getCategoryValidator());
        this.currencyService = new CurrencyService(currencyRepository, validatorProvider.getCurrencyValidator());
        this.rideService = new RideService(rideRepository, validatorProvider.getRideValidator());

        this.settingsService = new SettingsService(settingsRepository);

        this.jsonExportService = new JsonExportService(rideService, currencyService, categoryService, settingsService);
        this.jsonImportService = new JsonImportService(rideService, currencyService, categoryService, settingsService, validatorProvider.getPortDataValidator(), transactionExecutor);

    }

    public IRideRepository getRideRepository() {
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
