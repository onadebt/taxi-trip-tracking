package cz.muni.fi.pv168.project.database.dao;

import cz.muni.fi.pv168.project.database.ConnectionHandler;
import cz.muni.fi.pv168.project.model.SettingsDbModel;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.*;
import java.util.function.Supplier;

public class SettingsDao {

    private final Supplier<ConnectionHandler> connections;

    public SettingsDao(Supplier<ConnectionHandler> connections) {
        this.connections = connections;
    }

    public Optional<SettingsDbModel> getSettings(String name) {
        var sql = """
                SELECT id, name, "value"
                FROM Settings
                WHERE name = ?
                """;
        try (
                var connection = connections.get();
                var statement = connection.use().prepareStatement(sql)
        ) {
            statement.setString(1, name);
            var resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(settingsFromResultSet(resultSet));
            } else {
                return Optional.empty();
            }
        } catch (SQLException ex) {
            throw new DataStorageException("Failed to load settings by name: " + name, ex);
        }
    }

    public void setSettings(SettingsDbModel settings) {
        var sql = """
                UPDATE Settings
                SET "value" = ?
                WHERE name = ?
                """;
        try (
                var connection = connections.get();
                var statement = connection.use().prepareStatement(sql)
        ) {
            statement.setString(1, settings.getValue());
            statement.setString(2, settings.getName());
            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated == 0) {
                throw new DataStorageException("Settings not found, name: " + settings.getName());
            }
            if (rowsUpdated > 1) {
                throw new DataStorageException("More then 1 settings (rows=%d) has been updated: %s"
                        .formatted(rowsUpdated, settings.getName()));
            }
        } catch (SQLException ex) {
            throw new DataStorageException("Failed to update settings: " + settings, ex);
        }
    }

    private static SettingsDbModel settingsFromResultSet(ResultSet resultSet) throws SQLException {
        return new SettingsDbModel(
                resultSet.getLong("id"),
                resultSet.getString("name"),
                resultSet.getString("value")
        );
    }
}
