package cz.muni.fi.pv168.project.database.actions;

import cz.muni.fi.pv168.project.database.DatabaseManager;

public final class CreateDatabase {
    public static void main(String[] args) {
        var dbManager = DatabaseManager.createProductionInstance();
        System.out.println("Database connection string: " + dbManager.getDatabaseConnectionString());
        dbManager.initSchema();
        System.out.println("Database created...");
    }
}
