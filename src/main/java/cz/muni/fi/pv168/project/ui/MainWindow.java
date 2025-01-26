package cz.muni.fi.pv168.project.ui;

import cz.muni.fi.pv168.project.providers.DIProvider;
import cz.muni.fi.pv168.project.providers.ValidatorProvider;
import cz.muni.fi.pv168.project.service.interfaces.*;
import cz.muni.fi.pv168.project.service.port.ExportService;
import cz.muni.fi.pv168.project.service.port.ImportService;
import cz.muni.fi.pv168.project.ui.model.*;
import cz.muni.fi.pv168.project.ui.tabs.*;

import javax.swing.*;
import java.awt.*;

public class MainWindow {

    private final JFrame frame;
    private final JTabbedPane tabbedPane;

    public MainWindow() {
        DIProvider diProvider = new DIProvider();
        ValidatorProvider validatorProvider = new ValidatorProvider();

        IRideService rideService = diProvider.getRideService();
        ICategoryService categoryService = diProvider.getCategoryService();
        ICurrencyService currencyService = diProvider.getCurrencyService();
        ISettingsService settingsService = diProvider.getSettingsService();
        ImportService importService = diProvider.getJsonImportService();
        ExportService exportService = diProvider.getJsonExportService();

        RideTableModel rideTableModel = new RideTableModel(rideService);
        CurrencyTableModel currencyTableModel = new CurrencyTableModel(currencyService);
        CategoryTableModel categoryTableModel = new CategoryTableModel(categoryService);

        var categoryListModel = new EntityListModelAdapter<>(categoryTableModel);
        var currencyListModel = new EntityListModelAdapter<>(currencyTableModel);


        frame = new JFrame("Taxi trip tracking");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1280, 800);
        frame.setLocationRelativeTo(null);

        tabbedPane = new JTabbedPane();
        tabbedPane.setFont(new Font("Arial", Font.BOLD, 18));

        JPanel homePage = new HomePage(rideTableModel, rideService, currencyListModel, categoryListModel, settingsService);
        tabbedPane.addTab("Home Page", homePage);

        JPanel ridesHistory = new RidesHistory(rideTableModel, rideService, currencyListModel, categoryListModel, importService, exportService);
        tabbedPane.addTab("Rides History", ridesHistory);

        JPanel ridesCategories = new RidesCategoriesPanel(categoryTableModel);
        tabbedPane.addTab("Rides Categories", ridesCategories);

        JPanel currencies = new Currencies(currencyTableModel, validatorProvider.getCurrencyValidator());
        tabbedPane.addTab("Currencies", currencies);

        JPanel settings = new SettingsPanel(settingsService, rideService);
        tabbedPane.addTab("Settings", settings);

        JPanel aboutUs = AboutUs.createAboutUsPanel();
        tabbedPane.addTab("About Us", aboutUs);

        tabbedPane.addChangeListener(e -> {
            SwingUtilities.invokeLater(() -> {
                rideTableModel.refresh();
                currencyTableModel.refresh();
                categoryTableModel.refresh();

                if (tabbedPane.getSelectedComponent() instanceof HomePage homePageInstance) {
                    homePageInstance.refreshHomePage();
                }
            });
        });

        frame.getContentPane().add(tabbedPane, BorderLayout.CENTER);
    }

    public void show() {
        frame.setVisible(true);
    }
}
