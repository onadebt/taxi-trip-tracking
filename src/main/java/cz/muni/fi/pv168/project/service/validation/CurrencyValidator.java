package cz.muni.fi.pv168.project.service.validation;

import cz.muni.fi.pv168.project.model.Currency;
import cz.muni.fi.pv168.project.service.validation.common.StringLengthValidator;

import java.util.List;

/**
 * Currency entity {@link Validator}.
 */
public class CurrencyValidator implements Validator<Currency> {

    @Override
    public ValidationResult validate(Currency model) {
        var validators = List.of (
                Validator.extracting(Currency::getName, new StringLengthValidator(1, 20, "Name")),
                Validator.extracting(Currency::getCode, new StringLengthValidator(3, 3, "Code"))
        );

        return Validator.compose(validators).validate(model);
    }
}
