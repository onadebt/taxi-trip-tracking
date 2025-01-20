package cz.muni.fi.pv168.project.providers;

import cz.muni.fi.pv168.project.service.validation.CategoryValidator;
import cz.muni.fi.pv168.project.service.validation.CurrencyValidator;
import cz.muni.fi.pv168.project.service.validation.PortDataValidator;
import cz.muni.fi.pv168.project.service.validation.RideValidator;

public class ValidatorProvider {
    private final CurrencyValidator currencyValidator;
    private final CategoryValidator categoryValidator;
    private final RideValidator rideValidator;
    private final PortDataValidator portDataValidator;

    public ValidatorProvider() {
        this.currencyValidator = new CurrencyValidator();
        this.categoryValidator = new CategoryValidator();
        this.rideValidator = new RideValidator();
        this.portDataValidator = new PortDataValidator();
    }

    public CurrencyValidator getCurrencyValidator() {
        return currencyValidator;
    }

    public CategoryValidator getCategoryValidator() {
        return categoryValidator;
    }

    public RideValidator getRideValidator() { return rideValidator; }

    public PortDataValidator getPortDataValidator() {
        return portDataValidator;
    }
}
