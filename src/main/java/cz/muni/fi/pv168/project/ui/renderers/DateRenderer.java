package cz.muni.fi.pv168.project.ui.renderers;

import javax.swing.*;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;

public class DateRenderer extends AbstractRenderer<Instant> {
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

    public DateRenderer() {
        super(Instant.class);
    }

    @Override
    protected void updateLabel(JLabel label, Instant value) {
        if (value != null) {
            Date date = Date.from(value);
            label.setText(dateFormat.format(date));
        } else {
            label.setText("");
        }
    }
}
