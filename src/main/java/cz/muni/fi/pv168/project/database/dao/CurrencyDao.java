package cz.muni.fi.pv168.project.database.dao;

import cz.muni.fi.pv168.project.database.ConnectionHandler;
import cz.muni.fi.pv168.employees.storage.sql.entity.DepartmentEntity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

/**
 * DAO for {@link DepartmentEntity} entity.
 */
public final class CurrencyDao implements DataAccessObject<DepartmentEntity> {

    private final Supplier<ConnectionHandler> connections;

    public CurrencyDao(Supplier<ConnectionHandler> connections) {
        this.connections = connections;
    }

    @Override
    public DepartmentEntity create(DepartmentEntity newDepartment) {
        var sql = "INSERT INTO Department (number, name) VALUES (?, ?);";

        try (
                var connection = connections.get();
                var statement = connection.use().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)
        ) {
            statement.setString(1, newDepartment.number());
            statement.setString(2, newDepartment.name());
            statement.executeUpdate();

            try (ResultSet keyResultSet = statement.getGeneratedKeys()) {
                long departmentId;

                if (keyResultSet.next()) {
                    departmentId = keyResultSet.getLong(1);
                } else {
                    throw new DataStorageException("Failed to fetch generated key for: " + newDepartment);
                }
                if (keyResultSet.next()) {
                    throw new DataStorageException("Multiple keys returned for: " + newDepartment);
                }

                return findById(departmentId).orElseThrow();
            }
        } catch (SQLException ex) {
            throw new DataStorageException("Failed to store: " + newDepartment, ex);
        }
    }

    @Override
    public Collection<DepartmentEntity> findAll() {
        var sql = """
                SELECT id,
                       number,
                       name
                FROM Department
                """;
        try (
                var connection = connections.get();
                var statement = connection.use().prepareStatement(sql)
        ) {
            List<DepartmentEntity> departments = new ArrayList<>();
            try (var resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    var department = departmentFromResultSet(resultSet);
                    departments.add(department);
                }
            }

            return departments;
        } catch (SQLException ex) {
            throw new DataStorageException("Failed to load all departments", ex);
        }
    }

    @Override
    public Optional<DepartmentEntity> findById(Long id) {
        var sql = """
                SELECT id,
                       number,
                       name
                FROM Department
                WHERE id = ?
                """;
        try (
                var connection = connections.get();
                var statement = connection.use().prepareStatement(sql)
        ) {
            statement.setLong(1, id);
            var resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(departmentFromResultSet(resultSet));
            } else {
                // department not found
                return Optional.empty();
            }
        } catch (SQLException ex) {
            throw new DataStorageException("Failed to load department by id: " + id, ex);
        }
    }

    @Override
    public DepartmentEntity update(DepartmentEntity entity) {
        // TODO: Implement me
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public void deleteById(Long id) {
        var sql = """
                DELETE FROM Department
                WHERE id = ?
                """;
        try (
                var connection = connections.get();
                var statement = connection.use().prepareStatement(sql)
        ) {
            statement.setLong(1, id);
            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated == 0) {
                throw new DataStorageException("Department not found, id: " + id);
            }
            if (rowsUpdated > 1) {
                throw new DataStorageException("More then 1 department (rows=%d) has been deleted: %s"
                        .formatted(rowsUpdated, id));
            }
        } catch (SQLException ex) {
            throw new DataStorageException("Failed to delete department, id: " + id, ex);
        }
    }

    @Override
    public void deleteAll() {
        var sql = "DELETE FROM Department";
        try (
                var connection = connections.get();
                var statement = connection.use().prepareStatement(sql)
        ) {
            statement.executeUpdate();
        } catch (SQLException ex) {
            throw new DataStorageException("Failed to delete all departments", ex);
        }
    }

    private static DepartmentEntity departmentFromResultSet(ResultSet resultSet) throws SQLException {
        return new DepartmentEntity(
                resultSet.getLong("id"),
                resultSet.getString("number"),
                resultSet.getString("name")
        );
    }
}
