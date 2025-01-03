package cz.muni.fi.pv168.project.database.dao;

import cz.muni.fi.pv168.project.database.ConnectionHandler;
import cz.muni.fi.pv168.project.model.RideDbModel;
import cz.muni.fi.pv168.project.model.enums.TripType;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.*;
import java.util.function.Supplier;

public class RideDao implements DataAccessObject<RideDbModel> {

    private final Supplier<ConnectionHandler> connections;

    public RideDao(Supplier<ConnectionHandler> connections) {
        this.connections = connections;
    }

    @Override
    public RideDbModel create(RideDbModel newRide) {
        var sql = "INSERT INTO Ride (categoryId, currencyId, amount, distance, passengers, tripType, uuid) VALUES (?, ?, ?, ?, ?, ?, ?);";

        try (
                var connection = connections.get();
                var statement = connection.use().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)
        ) {
            statement.setObject(1, newRide.getCategoryId(), Types.BIGINT);
            statement.setLong(2, newRide.getCurrencyId());
            statement.setBigDecimal(3, newRide.getAmountCurrency());
            statement.setDouble(4, newRide.getDistance());
            statement.setInt(5, newRide.getPassengers());
            statement.setString(6, newRide.getTripType().toString());
            //statement.setObject(7, newRide.getCreatedDate());
            statement.setString(7, newRide.getUuid().toString());
            statement.executeUpdate();

            try (ResultSet keyResultSet = statement.getGeneratedKeys()) {
                long rideId;

                if (keyResultSet.next()) {
                    rideId = keyResultSet.getLong(1);
                } else {
                    throw new DataStorageException("Failed to fetch generated key for: " + newRide);
                }
                if (keyResultSet.next()) {
                    throw new DataStorageException("Multiple keys returned for: " + newRide);
                }

                return findById(rideId).orElseThrow();
            }
        } catch (SQLException ex) {
            throw new DataStorageException("Failed to store: " + newRide, ex);
        }
    }

    @Override
    public Collection<RideDbModel> findAll() {
        var sql = """
                SELECT id, categoryId, currencyId, amount, distance, passengers, tripType, createdAt, uuid
                FROM Ride
                """;
        try (
                var connection = connections.get();
                var statement = connection.use().prepareStatement(sql)
        ) {
            List<RideDbModel> rides = new ArrayList<>();
            try (var resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    var ride = rideFromResultSet(resultSet);
                    rides.add(ride);
                }
            }
            return rides;
        } catch (SQLException ex) {
            throw new DataStorageException("Failed to load all rides", ex);
        }
    }

    @Override
    public Optional<RideDbModel> findById(Long id) {
        var sql = """
                SELECT id, categoryId, currencyId, amount, distance, passengers, tripType, createdAt, uuid
                FROM Ride
                WHERE id = ?
                """;
        try (
                var connection = connections.get();
                var statement = connection.use().prepareStatement(sql)
        ) {
            statement.setLong(1, id);
            var resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(rideFromResultSet(resultSet));
            } else {
                // department not found
                return Optional.empty();
            }
        } catch (SQLException ex) {
            throw new DataStorageException("Failed to load ride by id: " + id, ex);
        }
    }

    public Optional<RideDbModel> findByUuid(UUID uuid) {
        var sql = """
                SELECT id, categoryId, currencyId, amount, distance, passengers, tripType, createdAt, uuid
                FROM Ride
                WHERE uuid = ?
                """;
        try (
                var connection = connections.get();
                var statement = connection.use().prepareStatement(sql)
        ) {
            statement.setString(1, String.valueOf(uuid));
            var resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(rideFromResultSet(resultSet));
            } else {
                // department not found
                return Optional.empty();
            }
        } catch (SQLException ex) {
            throw new DataStorageException("Failed to load ride by uuid: " + uuid, ex);
        }
    }

    @Override
    public RideDbModel update(RideDbModel ride) {
        var sql = """
                UPDATE Ride
                SET categoryId = ?, 
                    currencyId = ?, 
                    amount = ?, 
                    distance = ?, 
                    passengers = ?, 
                    tripType = ?, 
                    createdAt = ?, 
                    uuid = ?
                WHERE id = ?
                """;
        try (
                var connection = connections.get();
                var statement = connection.use().prepareStatement(sql)
        ) {
            statement.setObject(1, ride.getCategoryId(), Types.BIGINT);
            statement.setLong(2, ride.getCurrencyId());
            statement.setBigDecimal(3, ride.getAmountCurrency());
            statement.setDouble(4, ride.getDistance());
            statement.setInt(5, ride.getPassengers());
            statement.setString(6, ride.getTripType().toString());
            statement.setObject(7, ride.getCreatedDate());
            statement.setString(8, ride.getUuid().toString());
            statement.setObject(9, ride.getRideId() != null ? ride.getRideId() : null);
            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated == 0) {
                throw new DataStorageException("Ride not found, id: " + ride.getRideId());
            }
            if (rowsUpdated > 1) {
                throw new DataStorageException("More then 1 ride (rows=%d) has been updated: %s"
                        .formatted(rowsUpdated, ride));
            }
            return ride;
        } catch (SQLException ex) {
            throw new DataStorageException("Failed to update ride: " + ride, ex);
        }
    }

    @Override
    public void deleteById(Long id) {
        var sql = """
                DELETE FROM Ride
                WHERE id = ?
                """;
        try (
                var connection = connections.get();
                var statement = connection.use().prepareStatement(sql)
        ) {
            statement.setLong(1, id);
            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated == 0) {
                throw new DataStorageException("Ride not found, id: " + id);
            }
            if (rowsUpdated > 1) {
                throw new DataStorageException("More then 1 ride (rows=%d) has been deleted: %s"
                        .formatted(rowsUpdated, id));
            }
        } catch (SQLException ex) {
            throw new DataStorageException("Failed to delete ride, id: " + id, ex);
        }
    }

    @Override
    public void deleteAll() {
        var sql = "DELETE FROM Ride";
        try (
                var connection = connections.get();
                var statement = connection.use().prepareStatement(sql)
        ) {
            statement.executeUpdate();
        } catch (SQLException ex) {
            throw new DataStorageException("Failed to delete all rides", ex);
        }
    }

    public void deleteByUuid(UUID uuid) {
        var sql = """
                DELETE FROM Ride
                WHERE uuid = ?
                """;
        try (
                var connection = connections.get();
                var statement = connection.use().prepareStatement(sql)
        ) {
            statement.setString(1, String.valueOf(uuid));
            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated == 0) {
                throw new DataStorageException("Ride not found, uuid: " + uuid);
            }
            if (rowsUpdated > 1) {
                throw new DataStorageException("More then 1 ride (rows=%d) has been deleted: %s"
                        .formatted(rowsUpdated, uuid));
            }
        } catch (SQLException ex) {
            throw new DataStorageException("Failed to delete ride, uuid: " + uuid, ex);
        }
    }

    private static RideDbModel rideFromResultSet(ResultSet resultSet) throws SQLException {
        return new RideDbModel(
                resultSet.getLong("id"),
                resultSet.getBigDecimal("amount"),
                resultSet.getLong("currencyId"),
                resultSet.getDouble("distance"),
                resultSet.getLong("categoryId"),
                resultSet.getInt("passengers"),
                TripType.valueOf(resultSet.getString("tripType")),
                resultSet.getTimestamp("createdAt").toInstant(),
                UUID.fromString(resultSet.getString("uuid"))
        );
    }
}
