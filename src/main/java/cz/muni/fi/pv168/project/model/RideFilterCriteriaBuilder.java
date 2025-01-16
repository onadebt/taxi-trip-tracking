package cz.muni.fi.pv168.project.model;

import com.toedter.calendar.JDateChooser;
import cz.muni.fi.pv168.project.model.enums.TripType;

import javax.swing.*;
import java.math.BigDecimal;

public class RideFilterCriteriaBuilder {
    private final JTextField minAmountField;
    private final JTextField maxAmountField;
    private final JTextField minDistanceField;
    private final JTextField maxDistanceField;
    private final JTextField minPeopleField;
    private final JTextField maxPeopleField;
    private final JComboBox<Currency> currencyField;
    private final JComboBox<Category> categoryField;
    private final JComboBox<TripType> tripTypeField;
    private final JDateChooser startDateChooser;
    private final JDateChooser endDateChooser;

    public RideFilterCriteriaBuilder(JTextField minAmountField, JTextField maxAmountField, JTextField minDistanceField,
                                     JTextField maxDistanceField, JTextField minPeopleField, JTextField maxPeopleField,
                                     JComboBox<Currency> currencyField, JComboBox<Category> categoryField,
                                     JComboBox<TripType> tripTypeField, JDateChooser startDateChooser, JDateChooser endDateChooser) {
        this.minAmountField = minAmountField;
        this.maxAmountField = maxAmountField;
        this.minDistanceField = minDistanceField;
        this.maxDistanceField = maxDistanceField;
        this.minPeopleField = minPeopleField;
        this.maxPeopleField = maxPeopleField;
        this.currencyField = currencyField;
        this.categoryField = categoryField;
        this.tripTypeField = tripTypeField;
        this.startDateChooser = startDateChooser;
        this.endDateChooser = endDateChooser;
    }

    public RideFilterCriteria build() {
        RideFilterCriteria criteria = new RideFilterCriteria();
        criteria.setMinAmount(parseBigDecimal(minAmountField.getText()));
        criteria.setMaxAmount(parseBigDecimal(maxAmountField.getText()));
        criteria.setMinDistance(parseDouble(minDistanceField.getText()));
        criteria.setMaxDistance(parseDouble(maxDistanceField.getText()));
        criteria.setMinPassengers(parseInt(minPeopleField.getText()));
        criteria.setMaxPassengers(parseInt(maxPeopleField.getText()));
        criteria.setCurrency((Currency) currencyField.getSelectedItem());
        criteria.setCategory((Category) categoryField.getSelectedItem());
        criteria.setTripType((TripType) tripTypeField.getSelectedItem());
        criteria.setStartDate(startDateChooser.getDate() != null ? startDateChooser.getDate().toInstant() : null);
        criteria.setEndDate(endDateChooser.getDate() != null ? endDateChooser.getDate().toInstant() : null);
        return criteria;
    }

    private BigDecimal parseBigDecimal(String text) {
        return text.isEmpty() ? null : new BigDecimal(text);
    }

    private Double parseDouble(String text) {
        try {
            return text.isEmpty() ? null : Double.parseDouble(text);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private Integer parseInt(String text) {
        try {
            return text.isEmpty() ? null : Integer.parseInt(text);
        } catch (NumberFormatException e) {
            return null;
        }
    }


    public JTextField getMinAmountField() {
        return minAmountField;
    }

    public JTextField getMaxAmountField() {
        return maxAmountField;
    }

    public JTextField getMinDistanceField() {
        return minDistanceField;
    }

    public JTextField getMaxDistanceField() {
        return maxDistanceField;
    }

    public JTextField getMinPeopleField() {
        return minPeopleField;
    }

    public JTextField getMaxPeopleField() {
        return maxPeopleField;
    }

    public JComboBox<Currency> getCurrencyField() {
        return currencyField;
    }

    public JComboBox<Category> getCategoryField() {
        return categoryField;
    }

    public JComboBox<TripType> getTripTypeField() {
        return tripTypeField;
    }

    public JDateChooser getStartDateChooser() {
        return startDateChooser;
    }

    public JDateChooser getEndDateChooser() {
        return endDateChooser;
    }
}

