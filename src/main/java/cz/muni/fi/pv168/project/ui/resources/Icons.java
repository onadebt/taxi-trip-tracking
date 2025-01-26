package cz.muni.fi.pv168.project.ui.resources;

import javax.swing.*;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class Icons {
    private static final Map<String, Icon> namedIcons = new HashMap<String, Icon>() {
        {
            put("convertible-car.png", createIcon("/icons/convertible-car.png"));
            put("truck-car.png", createIcon("/icons/truck-car.png"));
            put("express.png", createIcon("/icons/Express.png"));
            put("limousine-car.png", createIcon("/icons/limousine-car.png"));
            put("normal-car.png", createIcon("/icons/normal-car.png"));
            put("small-car.png", createIcon("/icons/small-car.png"));
            put("NormalRide.png", createIcon("/icons/NormalRide.png"));
            put("sport-car.png", createIcon("/icons/sport-car.png"));
        }
    };

    public static Icon getByName(String name) {
        return namedIcons.get(name);
    }

    public static Icon createIcon(String name) {
        URL url = Icons.class.getResource(name);
        if (url == null) {
            //return new ImageIcon("/icons/small-car.png");
            throw new IllegalArgumentException("Icon resource not found on classpath: " + name);
        }
        return new ImageIcon(url);
    }
}
