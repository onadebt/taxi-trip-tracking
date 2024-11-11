//package cz.muni.fi.pv168.project.ui.action;
//
//import cz.muni.fi.pv168.project.model.exception.ValidationException;
//import cz.muni.fi.pv168.project.service.ICategoryService;
//import cz.muni.fi.pv168.project.service.ICurrencyService;
//import cz.muni.fi.pv168.project.service.IRideService;
//import cz.muni.fi.pv168.project.ui.dialog.NewRideDialog;
//import cz.muni.fi.pv168.project.model.Category;
//import cz.muni.fi.pv168.project.ui.model.CategoryListModel;
//import cz.muni.fi.pv168.project.model.Currency;
//import cz.muni.fi.pv168.project.ui.model.CurrencyListModel;
//
//import javax.swing.*;
//import java.awt.event.ActionEvent;
//import java.util.stream.Collectors;
//
//public class NewRideAction extends AbstractAction {
//    private final JComponent parent;
//    private final ListModel<Currency> currencyListModel;
//    private final ListModel<Category> categoryListModel;
//
//    private final IRideService rideService;
//
//    public NewRideAction(JComponent parentComponent, IRideService rideService, ICurrencyService currencyService, ICategoryService categoryService) {
//        super("Add ride");
//        this.parent = parentComponent;
//        this.rideService = rideService;
//        this.categoryListModel = new CategoryListModel(categoryService.get().stream().map(Category::new).collect(Collectors.toList()));
//        this.currencyListModel = new CurrencyListModel(currencyService.getAll().stream().map(Currency::new).collect(Collectors.toList()));
//    }
//    @Override
//    public void actionPerformed(ActionEvent e) {
//        var dialog = new NewRideDialog(currencyListModel, categoryListModel);
//        var ride = dialog.show(parent, "Add ride");
//        if (!dialog.isValidationOk()) {
//            do {
//                dialog = new NewRideDialog(currencyListModel, categoryListModel);
//                ride = dialog.show(parent, "Add ride");
//            } while (!dialog.isValidationOk());
//        }
//        try {
//            ride.ifPresent(rideService::create);
//        } catch (ValidationException ex) {
//            JOptionPane.showMessageDialog(parent, ex.getMessage(), "Invalid entry", JOptionPane.ERROR_MESSAGE);
//        }
//
//        // TODO - refresh ridesHistory and HomePage history + stats
//    }
//}
