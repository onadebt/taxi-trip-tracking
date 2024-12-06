package cz.muni.fi.pv168.project.ui.action;

import cz.muni.fi.pv168.project.model.exception.ValidationException;
import cz.muni.fi.pv168.project.service.interfaces.IRideService;
import cz.muni.fi.pv168.project.ui.dialog.NewRideDialog;
import cz.muni.fi.pv168.project.model.Category;
import cz.muni.fi.pv168.project.model.Currency;
import cz.muni.fi.pv168.project.ui.model.RideTableModel;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class NewRideAction extends AbstractAction {
    private final JComponent parent;
    private final ListModel<Currency> currencyListModel;
    private final ListModel<Category> categoryListModel;
    private final RideTableModel rideTableModel;

    private final IRideService rideService;

    public NewRideAction(JComponent parentComponent, RideTableModel tableModel, IRideService rideService, ListModel<Currency> currencyListModel, ListModel<Category> categoryListModel) {
        super("Add ride");
        this.parent = parentComponent;
        this.rideService = rideService;

        this.categoryListModel = categoryListModel;
        this.currencyListModel = currencyListModel;
        this.rideTableModel = tableModel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {


        var dialog = new NewRideDialog(currencyListModel, categoryListModel);
        var optionalRide = dialog.show(parent, "Add ride");

        while (!dialog.isValidationOk()) {
            dialog = new NewRideDialog(currencyListModel, categoryListModel);
            optionalRide = dialog.show(parent, "Add ride");
        }

        optionalRide.ifPresent(ride -> {
            try {
                System.out.println("Creating ride: " + ride);
                rideService.create(ride);
                rideTableModel.addRow(ride);
                rideTableModel.refresh(); // this refresh fixed Adding new ride from HomePage (time was not visible)
            } catch (ValidationException ex) {
                JOptionPane.showMessageDialog(parent, ex.getMessage(), "Invalid entry", JOptionPane.ERROR_MESSAGE);
            }
        });
    }
}
