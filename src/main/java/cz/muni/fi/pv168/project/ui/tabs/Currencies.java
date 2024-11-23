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
import cz.muni.fi.pv168.project.ui.model.CurrencyListModel;
import cz.muni.fi.pv168.project.ui.model.CurrencyTableModel;
import cz.muni.fi.pv168.project.ui.model.EntityTableModel;
import cz.muni.fi.pv168.project.ui.renderers.CurrencyRenderer;

import javax.swing.*;
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

    public Currencies(CurrencyTableModel currencyTableModel, /*CurrencyListModel currencyListModel,*/ CurrencyValidator currencyValidator) {
        setLayout(new BorderLayout());

        this.table = createCurrenciesTable(currencyTableModel/*, currencyListModel*/);
        this.onSelectionChange = this::changeActionsState;
        this.currencyTableModel = currencyTableModel;
        this.addAction = new NewCurrencyAction(table, currencyValidator);
        this.editAction = new EditCurrencyAction(table, currencyValidator);
        this.deleteAction = new DeleteCurrencyAction(table);

        JLabel label = new JLabel("Currencies");
        JToolBar toolBar = createToolBar(currencyTableModel, currencyValidator);

        add(label, BorderLayout.NORTH);
        add(toolBar, BorderLayout.NORTH);
        add(new JScrollPane(table), BorderLayout.CENTER);
    }



    private JTable createCurrenciesTable(CurrencyTableModel currencyTableModel/*, CurrencyListModel currencyListModel*/) {
        var table = new JTable(currencyTableModel);

        var currencyRenderer = new CurrencyRenderer();
        table.setDefaultRenderer(Currency.class, currencyRenderer);

        table.setAutoCreateRowSorter(true);
        table.getSelectionModel().addListSelectionListener(this::rowSelectionChanged);

//        var currencyComboBox = new JComboBox<>(new ComboBoxModelAdapter<>(currencyListModel));
//        currencyComboBox.setRenderer(currencyRenderer);
//        table.setDefaultEditor(Currency.class, new DefaultCellEditor(currencyComboBox));

        return table;
    }


    private JToolBar createToolBar(CurrencyTableModel tableModel, CurrencyValidator currencyValidator) {
        JToolBar toolBar = new JToolBar();

        JButton addButton = new JButton("Add");
        addButton.addActionListener(addAction);
        toolBar.add(addButton);


        JButton editButton = new JButton("Edit");
        editButton.addActionListener(editAction);
        toolBar.add(editButton);

        JButton deleteButton = new JButton("Delete");
        deleteButton.addActionListener(deleteAction);
        toolBar.add(deleteButton);

        return toolBar;
    }


//    private JToolBar createToolBar(JTable table, EntityTableModel<Currency> tableModel) {
//        JToolBar toolBar = new JToolBar();
//
//
//
//        JButton addButton = new JButton("Add New Currency");
//        addButton.addActionListener(e -> {
//            NewCurrencyDialog dialog = new NewCurrencyDialog();
//            dialog.setVisible(true);
//            refreshCurrenciesTable(table, tableModel);
//        });
//        toolBar.add(addButton);
//
//        JButton editNameButton = new JButton("Edit Name");
//        editNameButton.addActionListener(e -> editName(table, tableModel));
//        toolBar.add(editNameButton);
//
//        JButton editCodeButton = new JButton("Edit Code");
//        editCodeButton.addActionListener(e -> editCode(table, tableModel));
//        toolBar.add(editCodeButton);
//
//        JButton editExchangeRateButton = new JButton("Edit Exchange Rate");
//        editExchangeRateButton.addActionListener(e -> editExchangeRate(table, tableModel));
//        toolBar.add(editExchangeRateButton);
//
//        JButton deleteRowsButton = new JButton("Delete");
//        deleteRowsButton.addActionListener(e -> deleteSelectedRows(table, tableModel));
//        toolBar.add(deleteRowsButton);
//
//        return toolBar;
//    }


    private void rowSelectionChanged(ListSelectionEvent listSelectionEvent) {
        var selectionModel = (ListSelectionModel) listSelectionEvent.getSource();
        var count = selectionModel.getSelectedItemsCount();
        if (onSelectionChange != null) {
            onSelectionChange.accept(count);
        }
    }

    public void setPopupMenu(JPopupMenu popupMenu) {
        table.setComponentPopupMenu(popupMenu);
    }

    private void changeActionsState(int selectedItemsCount) {
        editAction.setEnabled(selectedItemsCount == 1);
        deleteAction.setEnabled(selectedItemsCount >= 1);
    }

}
