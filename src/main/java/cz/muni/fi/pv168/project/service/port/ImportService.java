package cz.muni.fi.pv168.project.service.port;

public interface ImportService {
    void importData(String path);

    boolean isValid();
}
