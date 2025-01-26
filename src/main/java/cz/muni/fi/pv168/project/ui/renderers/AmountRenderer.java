package cz.muni.fi.pv168.project.ui.renderers;

import javax.swing.*;

public class AmountRenderer extends AbstractRenderer<Double>{
    public AmountRenderer() {
        super(Double.class);
    }

    @Override
    protected void updateLabel(JLabel label, Double value) {
        if (value != null) {
            label.setText(String.format("%.2f", value));
        }
    }
}
