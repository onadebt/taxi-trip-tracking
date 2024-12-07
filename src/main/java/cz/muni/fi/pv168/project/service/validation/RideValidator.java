package cz.muni.fi.pv168.project.service.validation;

import cz.muni.fi.pv168.project.model.Ride;
import cz.muni.fi.pv168.project.service.validation.common.StringLengthValidator;

import java.util.List;

public class RideValidator implements Validator<Ride> {

    @Override
    public ValidationResult validate(Ride model) {
        ValidationResult result = new ValidationResult();

        //TODO: Implement more advanced validation logic

        if (model == null) {
            result.add("Ride cannot be null.");
            return result;
        }

        if (model.getAmountCurrency() == null || model.getAmountCurrency() < 0) {
            result.add("Amount currency must be a non-negative value.");
        }

        if (model.getCurrency() == null || model.getCurrency().getCode() == null || model.getCurrency().getCode().trim().isEmpty()) {
            result.add("Currency must be specified and cannot be empty.");
        }

        if (model.getDistance() == null || model.getDistance() < 0) {
            result.add("Distance must be a non-negative value.");
        }

        if (model.getDistanceUnit() == null) {
            result.add("Distance unit must be specified.");
        }

        if (model.getTripType() == null) {
            result.add("Trip type must be specified.");
        }

        if (model.getNumberOfPassengers() == null || model.getNumberOfPassengers() < 0) {
            result.add("Number of passengers must be a non-negative value.");
        }

        if (model.getCreatedAt() == null) {
            result.add("Creation date must be specified.");
        }

        return result;
    }
}
