package cz.muni.fi.pv168.project.ui.action;

import cz.muni.fi.pv168.project.ui.dialog.NewRideDialog;
import cz.muni.fi.pv168.project.ui.model.Category;
import cz.muni.fi.pv168.project.ui.model.Currency;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class NewRideAction extends AbstractAction {
    private JComponent parent;
    private final ListModel<Currency> currencyListModel;
    private final ListModel<Category> categoryListModel;

    public NewRideAction(JComponent parentComponent, ListModel<Currency> currencyListModel, ListModel<Category> categoryListModel) {
        super("Add ride");
        this.categoryListModel = categoryListModel;
        this.currencyListModel = currencyListModel;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        var dialog = new NewRideDialog(currencyListModel, categoryListModel);
        dialog.show(parent, "Add ride"); //TODO - add ifpresent(service.addRide)
    }
}
