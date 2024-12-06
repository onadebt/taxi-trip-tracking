package cz.muni.fi.pv168.project.service.validation;

import cz.muni.fi.pv168.project.model.Ride;
import cz.muni.fi.pv168.project.service.validation.common.StringLengthValidator;

import java.util.List;

public class RideValidator implements Validator<Ride> {

    @Override
    public ValidationResult validate(Ride model) {
        var validators = List.of (

        );

        return Validator.compose(null).validate(model);
    }
}
