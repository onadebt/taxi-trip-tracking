package cz.muni.fi.pv168.project.providers;

import cz.muni.fi.pv168.project.service.crud.CurrencyCrudService;
import cz.muni.fi.pv168.project.service.validation.CategoryValidator;
import cz.muni.fi.pv168.project.service.validation.CurrencyValidator;

public class ValidatorProvider {
    private final CurrencyValidator currencyValidator;
    private final CategoryValidator categoryValidator;

    public ValidatorProvider() {
        this.currencyValidator = new CurrencyValidator();
        this.categoryValidator = new CategoryValidator();
    }

    public CurrencyValidator getCurrencyValidator() {
        return currencyValidator;
    }

    public CategoryValidator getCategoryValidator() {
        return categoryValidator;
    }
}
