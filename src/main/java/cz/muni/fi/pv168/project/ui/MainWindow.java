package cz.muni.fi.pv168.project.ui;

import cz.muni.fi.pv168.project.providers.DIProvider;
import cz.muni.fi.pv168.project.service.interfaces.ICategoryService;
import cz.muni.fi.pv168.project.ui.model.CategoryListModel;
import cz.muni.fi.pv168.project.ui.tabs.*;

import javax.swing.*;
import java.awt.*;

public class MainWindow {

    private final JFrame frame;
    private final JTabbedPane tabbedPane;
    private final DIProvider diProvider = new DIProvider();

    public MainWindow() {
        DIProvider diProvider = new DIProvider();
        ICategoryService categoryService = diProvider.getCategoryService();
        CategoryListModel categoryListModel = new CategoryListModel(categoryService);

        frame = new JFrame("Taxi trip tracking");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1280, 800);
        frame.setLocationRelativeTo(null);

        tabbedPane = new JTabbedPane();
        tabbedPane.setFont(new Font("Arial", Font.BOLD, 18));

        JPanel homePage = HomePage.createHomePagePanel(diProvider);
        tabbedPane.addTab("Home Page", homePage);

        JPanel ridesHistory = RidesHistory.createRidesHistoryPanel(diProvider);
        tabbedPane.addTab("Rides History", ridesHistory);

        JPanel ridesCategories = RidesCategoriesPanel.createRidesCategoriesPanel(categoryListModel);
        tabbedPane.addTab("Rides Categories", ridesCategories);

        JPanel settings = Settings.createSettingsPanel();
        tabbedPane.addTab("Settings", settings);

        JPanel aboutUs = AboutUs.createAboutUsPanel();
        tabbedPane.addTab("About Us", aboutUs);


        // this refreshes page on tab change
        tabbedPane.addChangeListener(e -> {
            SwingUtilities.invokeLater(() -> {
                // TODO: update HomePage tab
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