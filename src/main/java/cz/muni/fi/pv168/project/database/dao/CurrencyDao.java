package cz.muni.fi.pv168.project.database.dao;

import cz.muni.fi.pv168.project.database.ConnectionHandler;
import cz.muni.fi.pv168.project.model.CurrencyDbModel;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

/**
 * DAO for {@link cz.muni.fi.pv168.project.model.CurrencyDbModel} entity.
 */
public final class CurrencyDao implements CurrencyDataAccessObject {

    private final Supplier<ConnectionHandler> connections;

    public CurrencyDao(Supplier<ConnectionHandler> connections) {
        this.connections = connections;
    }

    @Override
    public CurrencyDbModel create(CurrencyDbModel newCurrency) {
        var sql = "INSERT INTO Currency (name, tag, rate) VALUES (?, ?, ?);";

        try (
                var connection = connections.get();
                var statement = connection.use().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)
        ) {
            statement.setString(1, newCurrency.getName());
            statement.setString(2, newCurrency.getTag());
            statement.setDouble(3, newCurrency.getRate());
            statement.executeUpdate();

            try (ResultSet keyResultSet = statement.getGeneratedKeys()) {
                long currencyId;

                if (keyResultSet.next()) {
                    currencyId = keyResultSet.getLong(1);
                } else {
                    throw new DataStorageException("Failed to fetch generated key for: " + newCurrency);
                }
                if (keyResultSet.next()) {
                    throw new DataStorageException("Multiple keys returned for: " + newCurrency);
                }

                return findById(currencyId).orElseThrow();
            }
        } catch (SQLException ex) {
            throw new DataStorageException("Failed to store: " + newCurrency, ex);
        }
    }

    @Override
    public Collection<CurrencyDbModel> findAll() {
        var sql = """
                SELECT id,
                       name,
                       tag,
                       rate
                FROM Currency
                """;
        try (
                var connection = connections.get();
                var statement = connection.use().prepareStatement(sql)
        ) {
            List<CurrencyDbModel> currencies = new ArrayList<>();
            try (var resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    var currency = currencyFromResultSet(resultSet);
                    currencies.add(currency);
                }
            }
            return currencies;
        } catch (SQLException ex) {
            throw new DataStorageException("Failed to load all currencies", ex);
        }
    }

    @Override
    public Optional<CurrencyDbModel> findById(Long id) {
        var sql = """
                SELECT id,
                       name,
                       tag,
                       rate
                FROM Currency
                WHERE id = ?
                """;
        try (
                var connection = connections.get();
                var statement = connection.use().prepareStatement(sql)
        ) {
            statement.setLong(1, id);
            var resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(currencyFromResultSet(resultSet));
            } else {
                // department not found
                return Optional.empty();
            }
        } catch (SQLException ex) {
            throw new DataStorageException("Failed to load currency by id: " + id, ex);
        }
    }

    public Optional<CurrencyDbModel> findByCode(String tag) {
        var sql = """
                SELECT id,
                       name,
                       tag,
                       rate
                FROM Currency
                WHERE tag = ?
                """;
        try (
                var connection = connections.get();
                var statement = connection.use().prepareStatement(sql)
        ) {
            statement.setString(1, tag);
            var resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(currencyFromResultSet(resultSet));
            } else {
                // department not found
                return Optional.empty();
            }
        } catch (SQLException ex) {
            throw new DataStorageException("Failed to load currency by tag: " + tag, ex);
        }
    }

    public Optional<CurrencyDbModel> findByName(String name) {
        var sql = """
                SELECT id,
                       name,
                       tag,
                       rate
                FROM Currency
                WHERE name = ?
                """;
        try (
                var connection = connections.get();
                var statement = connection.use().prepareStatement(sql)
        ) {
            statement.setString(1, name);
            var resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(currencyFromResultSet(resultSet));
            } else {
                // department not found
                return Optional.empty();
            }
        } catch (SQLException ex) {
            throw new DataStorageException("Failed to load currency by name: " + name, ex);
        }
    }

    @Override
    public CurrencyDbModel update(CurrencyDbModel entity) {
        var sql = """
                UPDATE Currency
                SET name = ?,
                    tag = ?,
                    rate = ?
                WHERE id = ?
                """;
        try (
                var connection = connections.get();
                var statement = connection.use().prepareStatement(sql)
        ) {
            statement.setString(1, entity.getName());
            statement.setString(2, entity.getTag());
            statement.setDouble(3, entity.getRate());
            statement.setLong(4, entity.getCurrencyId());
            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated == 0) {
                throw new DataStorageException("Currency not found, id: " + entity.getCurrencyId());
            }
            if (rowsUpdated > 1) {
                throw new DataStorageException("More then 1 currency (rows=%d) has been updated: %s"
                        .formatted(rowsUpdated, entity));
            }
            return entity;
        } catch (SQLException ex) {
            throw new DataStorageException("Failed to update currency: " + entity, ex);
        }
    }

    @Override
    public void deleteById(Long id) {
        var sqlUpdate = """
                UPDATE Ride
                SET currencyId = ?, amount = amount / (SELECT rate FROM Currency WHERE id = ?);
                """;
        var sql = """
                DELETE FROM Currency
                WHERE id = ?;
                
                """;
        try (
                var connection = connections.get();
                var statement = connection.use().prepareStatement(sql);
                var statementUpdate = connection.use().prepareStatement(sqlUpdate)
        ) {
            statement.setLong(1, id);
            statementUpdate.setLong(1, 15);
            statementUpdate.setLong(2, id);
            statementUpdate.executeUpdate();
            statement.executeUpdate();
            /*if (rowsUpdated == 0) {
                throw new DataStorageException("Currency not found, id: " + id);
            }
            if (rowsUpdated > 1) {
                throw new DataStorageException("More then 1 currency (rows=%d) has been deleted: %s"
                        .formatted(rowsUpdated, id));
            }*/
        } catch (SQLException ex) {
            throw new DataStorageException("Failed to delete currency, id: " + id + ex, ex);
        }
    }

    @Override
    public void deleteAll() {
        var sql = "DELETE FROM Currency";
        try (
                var connection = connections.get();
                var statement = connection.use().prepareStatement(sql)
        ) {
            statement.executeUpdate();
        } catch (SQLException ex) {
            throw new DataStorageException("Failed to delete all currencies", ex);
        }
    }

    private static CurrencyDbModel currencyFromResultSet(ResultSet resultSet) throws SQLException {
        return new CurrencyDbModel(
                resultSet.getLong("id"),
                resultSet.getString("name"),
                resultSet.getString("tag"),
                resultSet.getDouble("rate")
        );
    }
}

