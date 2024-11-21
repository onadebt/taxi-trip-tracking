package cz.muni.fi.pv168.project.ui.tabs;

import cz.muni.fi.pv168.project.model.Currency;
import cz.muni.fi.pv168.project.service.interfaces.ICurrencyService;
import cz.muni.fi.pv168.project.ui.dialog.NewCurrencyDialog;
import cz.muni.fi.pv168.project.ui.model.CurrencyTableModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Arrays;
import java.util.List;

public class Currencies extends JPanel {
    private final List<Currency> currencies;
    private final ICurrencyService currencyService;

    private Currencies(ICurrencyService currencyService) {
        super(new BorderLayout());
        this.currencyService = currencyService;
        this.currencies = currencyService.getAll();

        JLabel label = new JLabel("Currencies");
        this.add(label, BorderLayout.NORTH);

        JTable currenciesTable = createCurrenciesTable();
        JScrollPane scrollPane = new JScrollPane(currenciesTable);

        JToolBar toolBar = createToolBar(currenciesTable, (CurrencyTableModel) currenciesTable.getModel());
        this.add(toolBar, BorderLayout.NORTH);
        this.add(scrollPane, BorderLayout.CENTER);
    }

    public static JPanel createCurrenciesPanel(ICurrencyService currencyService) {
        return new Currencies(currencyService);
    }

    private JToolBar createToolBar(JTable table, CurrencyTableModel tableModel) {
        JToolBar toolBar = new JToolBar();

        JButton addButton = new JButton("Add New Currency");
        addButton.addActionListener(e -> {
            NewCurrencyDialog dialog = new NewCurrencyDialog((Frame) SwingUtilities.getWindowAncestor(this), currencyService);
            dialog.setVisible(true);
            refreshCurrenciesTable(tableModel);
        });
        toolBar.add(addButton);

        JButton editNameButton = new JButton("Edit Name");
        editNameButton.addActionListener(e -> editName(table, tableModel));
        toolBar.add(editNameButton);

        JButton editCodeButton = new JButton("Edit Code");
        editCodeButton.addActionListener(e -> editCode(table, tableModel));
        toolBar.add(editCodeButton);

        JButton editExchangeRateButton = new JButton("Edit Exchange Rate");
        editExchangeRateButton.addActionListener(e -> editExchangeRate(table, tableModel));
        toolBar.add(editExchangeRateButton);

        JButton deleteRowsButton = new JButton("Delete");
        deleteRowsButton.addActionListener(e -> deleteSelectedRows(table, tableModel));
        toolBar.add(deleteRowsButton);

        return toolBar;
    }

    private JTable createCurrenciesTable() {
        CurrencyTableModel tableModel = new CurrencyTableModel(currencyService);
        JTable table = new JTable(tableModel);

        table.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (e.isPopupTrigger()) {
                    showPopupMenu(e, table, tableModel);
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (e.isPopupTrigger()) {
                    showPopupMenu(e, table, tableModel);
                }
            }
        });

        return table;
    }

    private void showPopupMenu(MouseEvent e, JTable table, CurrencyTableModel tableModel) {
        JPopupMenu popupMenu = new JPopupMenu();

        JMenuItem editName = new JMenuItem("Edit Name");
        editName.addActionListener(event -> editName(table, tableModel));
        popupMenu.add(editName);

        JMenuItem editCode = new JMenuItem("Edit Code");
        editCode.addActionListener(event -> editCode(table, tableModel));
        popupMenu.add(editCode);

        JMenuItem editExchangeRate = new JMenuItem("Edit Exchange Rate");
        editExchangeRate.addActionListener(event -> editExchangeRate(table, tableModel));
        popupMenu.add(editExchangeRate);

        JMenuItem deleteRows = new JMenuItem("Delete");
        deleteRows.addActionListener(event -> deleteSelectedRows(table, tableModel));
        popupMenu.add(deleteRows);

        popupMenu.show(e.getComponent(), e.getX(), e.getY());
    }

    private void editName(JTable table, CurrencyTableModel tableModel) {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            return;
        }

        String newName = JOptionPane.showInputDialog("Enter new name:");
        if (newName == null) {
            return;
        }

        tableModel.setValueAt(newName, selectedRow, 0);
        Currency currency = currencies.get(selectedRow);
        currency.setName(newName);
        currencyService.update(currency);
    }

    private void editCode(JTable table, CurrencyTableModel tableModel) {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            return;
        }

        String newCode = JOptionPane.showInputDialog("Enter new code:");
        if (newCode == null) {
            return;
        }

        tableModel.setValueAt(newCode, selectedRow, 1);
        Currency currency = currencies.get(selectedRow);
        currency.setCode(newCode);
        currencyService.update(currency);
    }

    private void editExchangeRate(JTable table, CurrencyTableModel tableModel) {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            return;
        }

        String newExchangeRate = JOptionPane.showInputDialog("Enter new exchange rate:");
        if (newExchangeRate == null) {
            return;
        }

        tableModel.setValueAt(newExchangeRate, selectedRow, 2);
        Currency currency = currencies.get(selectedRow);
        currency.setExchangeRate(Double.parseDouble(newExchangeRate));
        currencyService.update(currency);
    }

    private void deleteSelectedRows(JTable table, CurrencyTableModel tableModel) {
        int[] selectedRows = table.getSelectedRows();
        if (selectedRows.length == 0) {
            return;
        }

        Arrays.sort(selectedRows);
        for (int i = selectedRows.length - 1; i >= 0; i--) {
            int row = selectedRows[i];
            Currency currency = currencies.get(row);
            currencyService.delete(currency.getId());
            tableModel.removeRow(row);
        }
        refreshCurrenciesTable(tableModel);
    }

    private void refreshCurrenciesTable(CurrencyTableModel tableModel) {
        List<Currency> updatedCurrencies = currencyService.getAll();
        tableModel.updateCurrencyList(updatedCurrencies);
    }
}
