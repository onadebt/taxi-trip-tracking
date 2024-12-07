package cz.muni.fi.pv168.project.service.interfaces;

import cz.muni.fi.pv168.project.model.CurrencyDbModel;
import cz.muni.fi.pv168.project.model.Ride;
import cz.muni.fi.pv168.project.model.Settings;
import cz.muni.fi.pv168.project.model.enums.DistanceUnit;
import cz.muni.fi.pv168.project.model.Currency;
import cz.muni.fi.pv168.project.service.crud.CrudService;

import java.util.Optional;

public interface ISettingsService extends CrudService<Settings> {


    /**
     * Get default distance unit - kilometers or miles
     * @return distanceUnit
     */
    DistanceUnit getDefaultDistance();


    /**
     * Set default distance unit - all distances inputted will be tied with this distance unit
     * Change of default unit triggers recount of stored distances in DB
     * @param unit
     */
    void setDefaultDistance(DistanceUnit unit);

    Optional<Settings> getSettings();

    void saveSettings(Settings newSettings);
}
