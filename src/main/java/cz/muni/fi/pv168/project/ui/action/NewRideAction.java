package cz.muni.fi.pv168.project.ui.action;

import cz.muni.fi.pv168.project.model.exception.ValidationException;
import cz.muni.fi.pv168.project.service.crud.CrudService;
import cz.muni.fi.pv168.project.service.interfaces.ICategoryService;
import cz.muni.fi.pv168.project.service.interfaces.ICurrencyService;
import cz.muni.fi.pv168.project.service.interfaces.IRideService;
import cz.muni.fi.pv168.project.ui.dialog.NewRideDialog;
import cz.muni.fi.pv168.project.model.Category;
import cz.muni.fi.pv168.project.ui.model.CategoryListModel;
import cz.muni.fi.pv168.project.model.Currency;
import cz.muni.fi.pv168.project.ui.model.CategoryTableModel;
import cz.muni.fi.pv168.project.ui.model.CurrencyListModel;
import cz.muni.fi.pv168.project.ui.model.CurrencyTableModel;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class NewRideAction extends AbstractAction {
    private final JComponent parent;
    // TODO: use categoryTableModel and currencyTableModel instead of (categoryListModel and currencyListModel)
    private final CategoryTableModel categoryTableModel;
    private final CurrencyTableModel currencyTableModel;
    private final ListModel<Currency> currencyListModel;
    private final ListModel<Category> categoryListModel;

    private final IRideService rideService;

    public NewRideAction(JComponent parentComponent, IRideService rideService, CrudService<Currency> currencyCrudService, ICategoryService categoryService) {
        super("Add ride");
        this.parent = parentComponent;
        this.rideService = rideService;

        this.categoryTableModel = new CategoryTableModel(categoryService);
        this.currencyTableModel = new CurrencyTableModel(currencyCrudService);
        // TODO: use categoryTableModel and currencyTableModel instead of (categoryListModel and currencyListModel)

        this.categoryListModel = new CategoryListModel(categoryService);
        this.currencyListModel = new CurrencyListModel(currencyCrudService);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        var dialog = new NewRideDialog(currencyListModel, categoryListModel);
        var ride = dialog.show(parent, "Add ride");
        if (!dialog.isValidationOk()) {
            do {
                dialog = new NewRideDialog(currencyListModel, categoryListModel);
                ride = dialog.show(parent, "Add ride");
            } while (!dialog.isValidationOk());
        }
        try {
            ride.ifPresent(rideService::create);
        } catch (ValidationException ex) {
            JOptionPane.showMessageDialog(parent, ex.getMessage(), "Invalid entry", JOptionPane.ERROR_MESSAGE);
        }

        // TODO - refresh ridesHistory and HomePage history + stats
    }
}
