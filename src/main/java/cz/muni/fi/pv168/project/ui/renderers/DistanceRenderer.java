package cz.muni.fi.pv168.project.ui.renderers;

import javax.swing.*;

public class DistanceRenderer extends AbstractRenderer<Double> {
    public DistanceRenderer() {
        super(Double.class);
    }

    @Override
    protected void updateLabel(JLabel label, Double value) {
        if (value != null) {
            label.setText(String.format("%.2f", value));
        } else {
            label.setText("");
        }
    }
}
