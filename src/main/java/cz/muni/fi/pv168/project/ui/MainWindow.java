package cz.muni.fi.pv168.project.ui;

import cz.muni.fi.pv168.project.providers.DIProvider;
import cz.muni.fi.pv168.project.providers.ValidatorProvider;
import cz.muni.fi.pv168.project.repository.CurrencyRepository;
import cz.muni.fi.pv168.project.service.crud.CurrencyCrudService;
import cz.muni.fi.pv168.project.service.interfaces.*;
import cz.muni.fi.pv168.project.service.port.ExportService;
import cz.muni.fi.pv168.project.service.port.ImportService;
import cz.muni.fi.pv168.project.ui.model.CategoryListModel;
import cz.muni.fi.pv168.project.ui.model.CurrencyTableModel;
import cz.muni.fi.pv168.project.ui.tabs.*;

import javax.swing.*;
import java.awt.*;

public class MainWindow {

    private final JFrame frame;
    private final JTabbedPane tabbedPane;

    public MainWindow() {
        // Initialize DIProvider and extract services/repositories
        DIProvider diProvider = new DIProvider();
        ValidatorProvider validatorProvider = new ValidatorProvider();

        IRideService rideService = diProvider.getRideService();
        ICategoryService categoryService = diProvider.getCategoryService();
        ICurrencyService currencyService = diProvider.getCurrencyService();
        ISettingsService settingsService = diProvider.getSettingsService();
        ImportService importService = diProvider.getJsonImportService();
        ExportService exportService = diProvider.getJsonExportService();

        CurrencyCrudService currencyCrudService = new CurrencyCrudService((CurrencyRepository) diProvider.getCurrencyRepository(), validatorProvider.getCurrencyValidator());
        CurrencyTableModel currencyTableModel = new CurrencyTableModel(currencyCrudService);
        CategoryListModel categoryListModel = new CategoryListModel(categoryService);

        frame = new JFrame("Taxi trip tracking");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1280, 800);
        frame.setLocationRelativeTo(null);

        tabbedPane = new JTabbedPane();
        tabbedPane.setFont(new Font("Arial", Font.BOLD, 18));

        // Pass only necessary dependencies to each panel
        JPanel homePage = HomePage.createHomePagePanel(rideService, currencyCrudService, categoryService);
        tabbedPane.addTab("Home Page", homePage);

        JPanel ridesHistory = RidesHistory.createRidesHistoryPanel(rideService, /*currencyService,*/ currencyCrudService, categoryService, importService, exportService);
        tabbedPane.addTab("Rides History", ridesHistory);

        JPanel ridesCategories = RidesCategoriesPanel.createRidesCategoriesPanel(categoryListModel);
        tabbedPane.addTab("Rides Categories", ridesCategories);

//        JPanel currencies = Currencies.createCurrenciesPanel(currencyService);
        JPanel currencies = new Currencies(currencyTableModel, validatorProvider.getCurrencyValidator());
        tabbedPane.addTab("Currencies", currencies);

        JPanel settings = Settings.createSettingsPanel(rideService);
        tabbedPane.addTab("Settings", settings);

        JPanel aboutUs = AboutUs.createAboutUsPanel();
        tabbedPane.addTab("About Us", aboutUs);

        // Add a listener to refresh pages when tabs change
        tabbedPane.addChangeListener(e -> {
            SwingUtilities.invokeLater(() -> {
                // TODO: implement refresh logic for individual tabs
            });
        });

        // Add the tabbed pane to the frame
        frame.getContentPane().add(tabbedPane, BorderLayout.CENTER);
    }

    // Method to show the window
    public void show() {
        frame.setVisible(true);
    }
}
