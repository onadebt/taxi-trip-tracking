package cz.muni.fi.pv168.project.providers;

import cz.muni.fi.pv168.project.service.crud.CurrencyCrudService;
import cz.muni.fi.pv168.project.service.validation.CurrencyValidator;

public class ValidatorProvider {
    private CurrencyValidator currencyValidator;

    public ValidatorProvider() {
        this.currencyValidator = new CurrencyValidator();
    }

    public CurrencyValidator getCurrencyValidator() {
        return currencyValidator;
    }
}
