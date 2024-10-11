package cz.muni.fi.pv168.project.ui.tabs;

import javax.swing.*;
import java.awt.*;

public class RidesHistory {
    public static JPanel createRidesHistoryPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());

        JLabel label = new JLabel("history of taxi drives, (table with all data).");
        panel.add(label);

        return panel;
    }
}
