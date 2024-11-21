package cz.muni.fi.pv168.project.ui.dialog;

import cz.muni.fi.pv168.project.model.Currency;
import cz.muni.fi.pv168.project.service.CurrencyService;
import cz.muni.fi.pv168.project.service.interfaces.ICurrencyService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class NewCurrencyDialog extends JDialog {

    private final JTextField nameField;
    private final JTextField codeField;
    private final JTextField exchangeRateField;
    private final ICurrencyService currencyService;

    public NewCurrencyDialog(Frame parent, ICurrencyService currencyService) {
        super(parent, "Add New Currency", true);
        this.currencyService = currencyService;

        // Set layout and size
        setLayout(new GridBagLayout());
        setSize(300, 200);
        setLocationRelativeTo(parent);

        // Create UI components
        JLabel nameLabel = new JLabel("Name:");
        nameField = new JTextField(15);

        JLabel codeLabel = new JLabel("Code:");
        codeField = new JTextField(15);

        JLabel exchangeRateLabel = new JLabel("Exchange Rate:");
        exchangeRateField = new JTextField(15);

        JButton addButton = new JButton("Add Currency");
        addButton.addActionListener(new AddCurrencyAction());

        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(e -> dispose());

        // Arrange components in the dialog
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;
        add(nameLabel, gbc);

        gbc.gridx = 1;
        add(nameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        add(codeLabel, gbc);

        gbc.gridx = 1;
        add(codeField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        add(exchangeRateLabel, gbc);

        gbc.gridx = 1;
        add(exchangeRateField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        add(addButton, gbc);

        gbc.gridy = 4;
        add(cancelButton, gbc);

        pack();
    }

    private class AddCurrencyAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String name = nameField.getText().trim();
            String code = codeField.getText().trim();
            String exchangeRateText = exchangeRateField.getText().trim();

            if (name.isEmpty() || code.isEmpty() || exchangeRateText.isEmpty()) {
                JOptionPane.showMessageDialog(NewCurrencyDialog.this, "All fields are required.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                double exchangeRate = Double.parseDouble(exchangeRateText);
                Currency currency = new Currency(null, name, code, exchangeRate); // ID is null for a new currency for now
                currencyService.create(currency);
                dispose();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(NewCurrencyDialog.this, "Invalid exchange rate.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
