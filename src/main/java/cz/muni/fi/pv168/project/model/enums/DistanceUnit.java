package cz.muni.fi.pv168.project.model.enums;

public enum DistanceUnit {
    KILOMETER("km"),
    MILE("ml");

    private final String shortcut;

    DistanceUnit(String shortcut) {
        this.shortcut = shortcut;
    }

    public String getShortcut() {
        return shortcut;
    }

    public static DistanceUnit fromShortcut(String shortcut) {
        for (DistanceUnit unit : values()) {
            if (unit.getShortcut().equalsIgnoreCase(shortcut)) {
                return unit;
            }
        }
        throw new IllegalArgumentException("No DistanceUnit found for shortcut: " + shortcut);
    }
}

