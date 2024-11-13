package cz.muni.fi.pv168.project.ui.tabs;

import cz.muni.fi.pv168.project.model.Currency;
import cz.muni.fi.pv168.project.providers.DIProvider;
import cz.muni.fi.pv168.project.ui.action.NewRideAction;
import cz.muni.fi.pv168.project.ui.dialog.NewCurrencyDialog;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Arrays;
import java.util.List;

public class Currencies extends JPanel{
    public List<Currency> currencies;
    private final DIProvider diProvider;


    private Currencies(DIProvider diProvider) {
        super(new BorderLayout());
        this.diProvider = diProvider;
        currencies = diProvider.getCurrencyService().getAll();


        JLabel label = new JLabel("Currencies");
        this.add(label, BorderLayout.NORTH);

        JTable currenciesTable = createCurrenciesTable();
        JScrollPane scrollPane = new JScrollPane(currenciesTable);

        JToolBar toolBar = createToolBar(currenciesTable, (DefaultTableModel) currenciesTable.getModel());
        this.add(toolBar, BorderLayout.NORTH);
        this.add(scrollPane, BorderLayout.CENTER);
    }

    public static JPanel createCurrenciesPanel(DIProvider diProvider) {
        return new Currencies(diProvider);
    }

    private JToolBar createToolBar(JTable table, DefaultTableModel tableModel) {
        JToolBar toolBar = new JToolBar();

        JButton addButton = new JButton("Add New Currency");
        addButton.addActionListener(e -> {
            NewCurrencyDialog dialog = new NewCurrencyDialog((Frame) SwingUtilities.getWindowAncestor(this), diProvider);
            dialog.setVisible(true);
            refreshCurrenciesTable(table, tableModel);
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
        String[] columnNames = {"Name", "Code", "Exchange Rate"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 0 || column == 1 || column == 2;
            }
        };

        for (Currency currency : currencies) {
            Object[] rowData = {
                    currency.getName(),
                    currency.getCode(),
                    currency.getExchangeRate()
            };
            tableModel.addRow(rowData);
        }

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


    private void showPopupMenu(MouseEvent e, JTable table, DefaultTableModel tableModel) {
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

    private void editName(JTable table, DefaultTableModel tableModel) {
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
        diProvider.getCurrencyService().update(currency);
    }

    private void editCode(JTable table, DefaultTableModel tableModel) {
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
        diProvider.getCurrencyService().update(currency);
    }

    private void editExchangeRate(JTable table, DefaultTableModel tableModel) {
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
        diProvider.getCurrencyService().update(currency);
    }

    private void deleteSelectedRows(JTable table, DefaultTableModel tableModel) {
        int[] selectedRows = table.getSelectedRows();
        if (selectedRows.length == 0) {
            return;
        }

        Arrays.sort(selectedRows);
        for (int i = selectedRows.length - 1; i >= 0; i--) {
            int row = selectedRows[i];
            tableModel.removeRow(row);
        }
    }

    private void refreshCurrenciesTable(JTable table, DefaultTableModel tableModel) {
        tableModel.setRowCount(0);

        currencies = diProvider.getCurrencyService().getAll();

        for (Currency currency : currencies) {
            Object[] rowData = {
                    currency.getName(),
                    currency.getCode(),
                    currency.getExchangeRate()
            };
            tableModel.addRow(rowData);
        }
    }

}
