package cz.muni.fi.pv168.project.database;


import cz.muni.fi.pv168.project.database.exceptions.DataStorageException;

/**
 * Thrown if some transaction operation fails.
 */
public class TransactionException extends DataStorageException {

    public TransactionException(String message) {
        super(message);
    }

    public TransactionException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
