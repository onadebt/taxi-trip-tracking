package cz.muni.fi.pv168.project.ui;

import cz.muni.fi.pv168.project.ui.tabs.*;

import javax.swing.*;
import java.awt.*;

public class MainWindow {

    private final JFrame frame;
    private final JTabbedPane tabbedPane;

    public MainWindow() {
        frame = new JFrame("Taxi trip tracking");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1280, 800);
        frame.setLocationRelativeTo(null);

        tabbedPane = new JTabbedPane();
        tabbedPane.setFont(new Font("Arial", Font.BOLD, 18));

        JPanel homePage = HomePage.createHomePagePanel();
        tabbedPane.addTab("Home Page", homePage);

        JPanel ridesHistory = RidesHistory.createRidesHistoryPanel();
        tabbedPane.addTab("Rides History", ridesHistory);

        JPanel ridesCategories = RidesCategories.createRidesCategoriesPanel();
        tabbedPane.addTab("Rides Categories", ridesCategories);

        JPanel settings = Settings.createSettingsPanel();
        tabbedPane.addTab("Settings", settings);

        JPanel aboutUs = AboutUs.createAboutUsPanel();
        tabbedPane.addTab("About Us", aboutUs);


        // Add the tabbed pane to the frame
        frame.getContentPane().add(tabbedPane, BorderLayout.CENTER);
    }

    // Method to show the window
    public void show() {
        frame.setVisible(true);
    }
}