package cz.muni.fi.pv168.project.database;

import cz.muni.fi.pv168.project.database.exceptions.DataStorageException;
import org.h2.jdbcx.JdbcConnectionPool;


import javax.sql.DataSource;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;

/**
 * The class is responsible for managing H2 database connection and schemas
 */
public final class DatabaseManager {

    private static final String PROJECT_NAME = "pv-168-taxi-trip-tracking";
    private static final String DB_PROPERTIES_STRING = "DB_CLOSE_DELAY=-1;DATABASE_TO_UPPER=false";

    private final DataSource dataSource;
    private final SqlFileExecutor sqlFileExecutor;
    private final String databaseConnectionString;

    private DatabaseManager(String jdbcUri) {
        // connection pool with empty credentials
        this.databaseConnectionString = jdbcUri;
        this.dataSource = JdbcConnectionPool.create(jdbcUri, "", "");

        this.sqlFileExecutor = new SqlFileExecutor(this::getTransactionHandler, DatabaseManager.class);
    }

    public ConnectionHandler getConnectionHandler() {
        try {
            return new ConnectionHandlerImpl(dataSource.getConnection());
        } catch (SQLException e) {
            throw new DataStorageException("Unable to get a new connection", e);
        }
    }

    public TransactionHandler getTransactionHandler() {
        try {
            return new TransactionHandlerImpl(dataSource.getConnection());
        } catch (SQLException e) {
            throw new DataStorageException("Unable to get a new connection", e);
        }
    }

    public static DatabaseManager createProductionInstance() {
        String connectionString = "jdbc:h2:%s;%s".formatted(createDbFileSystemPath(), DB_PROPERTIES_STRING);
        return new DatabaseManager(connectionString);
    }

    public static DatabaseManager createTestInstance() {
        String connectionString = "jdbc:h2:mem:%s;%s".formatted(PROJECT_NAME, DB_PROPERTIES_STRING);
        var databaseManager = new DatabaseManager(connectionString);
        //databaseManager.initSchema();
        //databaseManager.initData("test");

        return databaseManager;
    }

    public DataSource getDataSource() {
        return dataSource;
    }

    public String getDatabaseConnectionString() {
        return databaseConnectionString;
    }

    public void destroySchema() {
        sqlFileExecutor.execute("drop.sql");
    }

    public void initSchema() {
        sqlFileExecutor.execute("database/init.sql");
        //sqlFileExecutor.execute("data_dev.sql");
    }

    public void initData(String environment) {
        sqlFileExecutor.execute("data_%s.sql".formatted(environment));
    }

    private static Path createDbFileSystemPath() {
        String projectDir = System.getProperty("user.dir");
        Path projectDbPath = Paths.get(projectDir, "db", PROJECT_NAME);

        File parentDir = projectDbPath.getParent().toFile();




        return projectDbPath;
    }
}

