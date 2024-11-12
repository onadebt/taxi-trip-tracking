package cz.muni.fi.pv168.project.ui.model;

import cz.muni.fi.pv168.project.model.Category;
import cz.muni.fi.pv168.project.model.Currency;
import cz.muni.fi.pv168.project.model.Ride;
import cz.muni.fi.pv168.project.model.enums.DistanceUnit;
import cz.muni.fi.pv168.project.model.enums.TripType;
import cz.muni.fi.pv168.project.service.RideService;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

public class RideTableModel extends AbstractTableModel {
    private List<Ride> rides;
    private final RideService rideService;

    private final List<Column<Ride, ?>> columns = List.of(
            Column.editable("Amount currency", Double.class, Ride::getAmountCurrency, Ride::setAmountCurrency),
            Column.editable("Currency Type", Currency.class, Ride::getCurrency, Ride::setCurrency),
            Column.editable("Distance", Double.class, Ride::getDistance, Ride::setDistance),
            Column.editable("Distance Unit", DistanceUnit.class, Ride::getDistanceUnit, Ride::setDistanceUnit),
            Column.editable("Category", Category.class, Ride::getCategory, Ride::setCategory),
            Column.editable("Trip Type", TripType.class, Ride::getTripType, Ride::setTripType),
            Column.editable("Passengers", Integer.class, Ride::getNumberOfPassengers, Ride::setNumberOfPassengers)
    );


    public RideTableModel(RideService rideService) {
        this.rideService = rideService;
        this.rides = new ArrayList<>(rideService.getAll());
    }

    public void refresh() {
        this.rides = new ArrayList<>(rideService.getAll());
        fireTableDataChanged();
    }

    public void addRow(Ride ride) {
        int newRowIndex = getRowCount();
        rideService.create(ride);
        rides.add(ride);
        fireTableRowsInserted(newRowIndex, newRowIndex);
    }

    public void removeRow(int rowIndex) {
        var rideToBeDeleted = getEntity(rowIndex);
        rideService.deleteById(rideToBeDeleted.getId());
        rides.remove(rowIndex);
        fireTableRowsDeleted(rowIndex, rowIndex);
    }

    public void updateRow(Ride ride) {
        rideService.update(ride);
        int rowIndex = rides.indexOf(ride);
        fireTableRowsUpdated(rowIndex, rowIndex);
    }

    public Ride getRow(int rowIndex) {
        return rides.get(rowIndex);
    }

    public int indexOf(Ride ride) {
        return rides.indexOf(ride);
    }



    @Override
    public int getRowCount() {
        return rides.size();
    }

    @Override
    public int getColumnCount() {
        return rides.size();
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return null;
    }

    @Override
    public void setValueAt(Object value, int rowIndex, int columnIndex) {
        if (value != null) {
            var ride = getEntity(rowIndex);
            columns.get(columnIndex).setValue(value, ride);
            updateRow(ride);
        }
    }

    public Ride getEntity(int rowIndex){
        return rides.get(rowIndex);
    }

}
