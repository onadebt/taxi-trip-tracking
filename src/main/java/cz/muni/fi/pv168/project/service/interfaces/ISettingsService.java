package cz.muni.fi.pv168.project.service.interfaces;

import cz.muni.fi.pv168.project.model.CurrencyDbModel;
import cz.muni.fi.pv168.project.model.enums.DistanceUnit;
import cz.muni.fi.pv168.project.model.Currency;

public interface ISettingsService {

    /**
     * Get default currency
     * @return currency
     */
    CurrencyDbModel getDefaultCurrency();

    /**
     * Get default distance unit - kilometers or miles
     * @return distanceUnit
     */
    DistanceUnit getDefaultDistance();

    /**
     * Set default currency - has effect on statistics on main page
     * @param currency
     */
    void setDefaultCurrency(Currency currency);

    /**
     * Set default distance unit - all distances inputted will be tied with this distance unit
     * Change of default unit triggers recount of stored distances in DB
     * @param unit
     */
    void setDefaultDistance(DistanceUnit unit);
}
