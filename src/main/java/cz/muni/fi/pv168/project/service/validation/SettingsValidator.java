package cz.muni.fi.pv168.project.service.validation;

import cz.muni.fi.pv168.project.model.Settings;

public class SettingsValidator implements Validator<Settings> {

    @Override
    public ValidationResult validate(Settings model) {
        ValidationResult result = new ValidationResult();

        if (model == null) {
            result.add("Settings cannot be null.");
            return result;
        }

        if (model.getDefaultDistanceUnit() == null) {
            result.add("Default distance unit must be specified.");
        }

        return result;
    }
}
