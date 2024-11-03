package cz.muni.fi.pv168.project.utils;

public class PathHelper {
    public static String AddExtensionIfMissing(String path, String extension) {
        int dot = path.lastIndexOf('.');
        if (dot == -1 || !path.substring(path.lastIndexOf('.') + 1).equals(extension)) {
            path += "." + extension;
        }
        return path;
    }
}
