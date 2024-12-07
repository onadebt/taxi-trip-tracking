package cz.muni.fi.pv168.project.database.dao;

import cz.muni.fi.pv168.project.database.ConnectionHandler;
import cz.muni.fi.pv168.project.model.SettingsDbModel;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;
import java.util.function.Supplier;

public class SettingsDao implements DataAccessObject<SettingsDbModel> {

    private final Supplier<ConnectionHandler> connections;

    public SettingsDao(Supplier<ConnectionHandler> connections) {
        this.connections = connections;
    }

    @Override
    public SettingsDbModel create(SettingsDbModel newSettings) {
        var sql = "INSERT INTO Settings (defaultDistanceUnit) VALUES (?);";

        try (
                var connection = connections.get();
                var statement = connection.use().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)
        ) {
            statement.setString(1, newSettings.getDefaultDistanceUnit());
            statement.executeUpdate();

            try (ResultSet keyResultSet = statement.getGeneratedKeys()) {
                long settingsId;

                if (keyResultSet.next()) {
                    settingsId = keyResultSet.getLong(1);
                } else {
                    throw new DataStorageException("Failed to fetch generated key for: " + newSettings);
                }
                if (keyResultSet.next()) {
                    throw new DataStorageException("Multiple keys returned for: " + newSettings);
                }

                return findById(settingsId).orElseThrow();
            }
        } catch (SQLException ex) {
            throw new DataStorageException("Failed to store: " + newSettings, ex);
        }
    }

    @Override
    public Collection<SettingsDbModel> findAll() {
        var sql = """
                SELECT id,
                       defaultDistanceUnit
                FROM Settings
                """;
        try (
                var connection = connections.get();
                var statement = connection.use().prepareStatement(sql)
        ) {
            List<SettingsDbModel> settingsList = new ArrayList<>();
            try (var resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    var settings = settingsFromResultSet(resultSet);
                    settingsList.add(settings);
                }
            }
            return settingsList;
        } catch (SQLException ex) {
            throw new DataStorageException("Failed to load all settings", ex);
        }
    }

    @Override
    public Optional<SettingsDbModel> findById(Long id) {
        var sql = """
                SELECT id, defaultDistanceUnit
                FROM Settings
                WHERE id = ?
                """;
        try (
                var connection = connections.get();
                var statement = connection.use().prepareStatement(sql)
        ) {
            statement.setLong(1, id);
            var resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(settingsFromResultSet(resultSet));
            } else {
                return Optional.empty();
            }
        } catch (SQLException ex) {
            throw new DataStorageException("Failed to load settings by id: " + id, ex);
        }
    }

    @Override
    public SettingsDbModel update(SettingsDbModel settings) {
        var sql = """
                UPDATE Settings
                SET defaultDistanceUnit = ?
                WHERE id = ?
                """;
        try (
                var connection = connections.get();
                var statement = connection.use().prepareStatement(sql)
        ) {
            statement.setString(1, settings.getDefaultDistanceUnit());
            statement.setLong(2, settings.getId());
            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated == 0) {
                throw new DataStorageException("Settings not found, id: " + settings.getId());
            }
            if (rowsUpdated > 1) {
                throw new DataStorageException("More than 1 settings (rows=%d) has been updated: %s"
                        .formatted(rowsUpdated, settings));
            }
            return settings;
        } catch (SQLException ex) {
            throw new DataStorageException("Failed to update settings: " + settings, ex);
        }
    }

    @Override
    public void deleteById(Long id) {
        var sql = """
                DELETE FROM Settings
                WHERE id = ?
                """;
        try (
                var connection = connections.get();
                var statement = connection.use().prepareStatement(sql)
        ) {
            statement.setLong(1, id);
            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated == 0) {
                throw new DataStorageException("Settings not found, id: " + id);
            }
            if (rowsUpdated > 1) {
                throw new DataStorageException("More than 1 settings (rows=%d) has been deleted: %s"
                        .formatted(rowsUpdated, id));
            }
        } catch (SQLException ex) {
            throw new DataStorageException("Failed to delete settings, id: " + id, ex);
        }
    }

    @Override
    public void deleteAll() {
        var sql = "DELETE FROM Settings";
        try (
                var connection = connections.get();
                var statement = connection.use().prepareStatement(sql)
        ) {
            statement.executeUpdate();
        } catch (SQLException ex) {
            throw new DataStorageException("Failed to delete all settings", ex);
        }
    }

    private static SettingsDbModel settingsFromResultSet(ResultSet resultSet) throws SQLException {
        return new SettingsDbModel(
                resultSet.getLong("id"),
                resultSet.getString("defaultDistanceUnit")
        );
    }
}
