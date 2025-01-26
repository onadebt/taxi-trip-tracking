package cz.muni.fi.pv168.project.service.validation;

import cz.muni.fi.pv168.project.model.Category;
import cz.muni.fi.pv168.project.model.Currency;
import cz.muni.fi.pv168.project.model.PortData;
import cz.muni.fi.pv168.project.model.RidePortModel;
import cz.muni.fi.pv168.project.model.enums.DistanceUnit;
import cz.muni.fi.pv168.project.model.enums.TripType;
import cz.muni.fi.pv168.project.model.exception.ValidationException;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class PortDataValidator {

    public void validatePortData(PortData data) {
        validateRides(data.getRides(), data.getCurrencies(), data.getCategories());
        validateCurrencies(data.getCurrencies());
        validateCategories(data.getCategories());
    }

    private void validateRides(List<RidePortModel> rides, List<Currency> currencies, List<Category> categories) {
        Set<String> validCurrencyTags = currencies.stream().map(Currency::getCode).collect(Collectors.toSet());
        Set<String> validCategoryNames = categories.stream().map(Category::getName).collect(Collectors.toSet());

        for (RidePortModel ride : rides) {
            if (ride.getAmountCurrency().compareTo(BigDecimal.ZERO) < 0) {
                throw new ValidationException("Amount currency cannot be negative: " + ride.getAmountCurrency());
            }
            if (ride.getDistance() < 0) {
                throw new ValidationException("Distance cannot be negative: " + ride.getDistance());
            }
            if (ride.getPassengers() < 0) {
                throw new ValidationException("Number of passengers cannot be negative: " + ride.getPassengers());
            }
            if (!isValidDistanceUnit(ride.getDistanceUnit())) {
                throw new ValidationException("Invalid distance unit: " + ride.getDistanceUnit());
            }
            if (!isValidTripType(ride.getTripType())) {
                throw new ValidationException("Invalid trip type: " + ride.getTripType());
            }
            if (!validCurrencyTags.contains(ride.getCurrencyTag())) {
                throw new ValidationException("Invalid currency tag: " + ride.getCurrencyTag());
            }
            if (!validCategoryNames.contains(ride.getCategoryName())) {
                throw new ValidationException("Invalid category name: " + ride.getCategoryName());
            }
        }
    }

    private void validateCurrencies(List<Currency> currencies) {
        for (Currency currency : currencies) {
            if (currency.getExchangeRate().compareTo(BigDecimal.ZERO) <= 0) {
                throw new ValidationException("Exchange rate must be positive: " + currency.getExchangeRate());
            }
            if (currency.getCode().length() != 3) {
                throw new ValidationException("Currency code must be 3 characters: " + currency.getCode());
            }
        }
    }

    private void validateCategories(List<Category> categories) {
        for (Category category : categories) {
            if (category.getName() == null || category.getName().isEmpty()) {
                throw new ValidationException("Category name cannot be empty");
            }
        }
    }

    private boolean isValidDistanceUnit(DistanceUnit unit) {
        for (DistanceUnit validUnit : DistanceUnit.values()) {
            if (validUnit == unit) {
                return true;
            }
        }
        return false;
    }

    private boolean isValidTripType(TripType type) {
        for (TripType validType : TripType.values()) {
            if (validType == type) {
                return true;
            }
        }
        return false;
    }
}