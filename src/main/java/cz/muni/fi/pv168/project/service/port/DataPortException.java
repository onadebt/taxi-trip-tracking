package cz.muni.fi.pv168.project.service.port;

public class DataPortException extends RuntimeException {
    public DataPortException(String message) {
        super(message);
    }

    public DataPortException(String message, Throwable cause) {
        super(message, cause);
    }
}
