package cz.muni.fi.pv168.project.ui.tabs;

import cz.muni.fi.pv168.project.model.Currency;
import cz.muni.fi.pv168.project.providers.ValidatorProvider;
import cz.muni.fi.pv168.project.service.interfaces.ICurrencyService;
import cz.muni.fi.pv168.project.service.validation.CurrencyValidator;
import cz.muni.fi.pv168.project.ui.action.DeleteCurrencyAction;
import cz.muni.fi.pv168.project.ui.action.EditCurrencyAction;
import cz.muni.fi.pv168.project.ui.action.NewCurrencyAction;
import cz.muni.fi.pv168.project.ui.dialog.NewCurrencyDialog;
import cz.muni.fi.pv168.project.ui.model.ComboBoxModelAdapter;
import cz.muni.fi.pv168.project.ui.model.CurrencyTableModel;
import cz.muni.fi.pv168.project.ui.model.EntityTableModel;
import cz.muni.fi.pv168.project.ui.renderers.CurrencyRenderer;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

public class Currencies extends JPanel {

    private final JTable table;
    private final Consumer<Integer> onSelectionChange;
    private final CurrencyTableModel currencyTableModel;
    private final Action addAction;
    private final Action editAction;
    private final Action deleteAction;

    public Currencies(CurrencyTableModel currencyTableModel, CurrencyValidator currencyValidator) {
        setLayout(new BorderLayout());

        this.table = createCurrenciesTable(currencyTableModel);
        this.onSelectionChange = this::changeActionsState;
        this.currencyTableModel = currencyTableModel;
        this.addAction = new NewCurrencyAction(table, currencyValidator);
        this.editAction = new EditCurrencyAction(table, currencyValidator);
        this.deleteAction = new DeleteCurrencyAction(table);

        JLabel label = new JLabel("Currencies");
        JToolBar toolBar = createToolBar();

        add(label, BorderLayout.NORTH);
        add(toolBar, BorderLayout.NORTH);
        add(new JScrollPane(table), BorderLayout.CENTER);

        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (e.isPopupTrigger()) {
                    showPopupMenu(e);
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (e.isPopupTrigger()) {
                    showPopupMenu(e);
                }
            }
        });
    }


    private JTable createCurrenciesTable(CurrencyTableModel currencyTableModel) {
        var table = new JTable(currencyTableModel);

        var currencyRenderer = new CurrencyRenderer();
        table.setDefaultRenderer(Currency.class, currencyRenderer);

        table.setAutoCreateRowSorter(true);
        table.getSelectionModel().addListSelectionListener(this::rowSelectionChanged);

        return table;
    }


    private JToolBar createToolBar() {
        JToolBar toolBar = new JToolBar();
        toolBar.setFloatable(false);
        toolBar.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));

        JButton addButton = new JButton("Add Currency");
        addButton.addActionListener(addAction);
        toolBar.add(addButton);

        JButton editButton = new JButton("Edit Currency");
        editButton.setEnabled(false);
        editButton.addActionListener(editAction);
        toolBar.add(editButton);

        JButton deleteButton = new JButton("Delete Currency");
        deleteButton.setEnabled(false);
        deleteButton.addActionListener(deleteAction);
        toolBar.add(deleteButton);

        table.setRowHeight(32);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.getSelectionModel().addListSelectionListener(e -> {
            int selectedRow = table.getSelectedRow();
            boolean isSelected = selectedRow >= 0;

            if (isSelected) {
                Object value = table.getValueAt(selectedRow, 0);
                boolean isEuro = "Euro".equals(value);

                editButton.setEnabled(!isEuro);
                deleteButton.setEnabled(!isEuro);
            } else {
                editButton.setEnabled(false);
                deleteButton.setEnabled(false);
            }
        });

        return toolBar;
    }

    private void rowSelectionChanged(ListSelectionEvent listSelectionEvent) {
        var selectionModel = (ListSelectionModel) listSelectionEvent.getSource();
        var count = selectionModel.getSelectedItemsCount();
        if (onSelectionChange != null) {
            onSelectionChange.accept(count);
        }
    }


    private void changeActionsState(int selectedItemsCount) {
        editAction.setEnabled(selectedItemsCount == 1);
        deleteAction.setEnabled(selectedItemsCount >= 1);
    }

    private void showPopupMenu(MouseEvent e) {
        int selectedRow = table.getSelectedRow();
        boolean isSelected = selectedRow >= 0;
        boolean isEuro = isSelected && "Euro".equals(table.getValueAt(selectedRow, 0));

        JPopupMenu popupMenu = new JPopupMenu();

        JMenuItem addCurrency = new JMenuItem("Add Currency");
        addCurrency.addActionListener(addAction);
        popupMenu.add(addCurrency);

        JMenuItem editCurrency = new JMenuItem("Edit Currency");
        editCurrency.addActionListener(editAction);
        editCurrency.setEnabled(!isEuro && isSelected);
        popupMenu.add(editCurrency);

        popupMenu.addSeparator();

        JMenuItem deleteCurrency = new JMenuItem("Delete Currency");
        deleteCurrency.addActionListener(deleteAction);
        deleteCurrency.setEnabled(!isEuro && isSelected);
        popupMenu.add(deleteCurrency);

        popupMenu.show(e.getComponent(), e.getX(), e.getY());
    }
}
