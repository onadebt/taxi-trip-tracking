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

        if (model.getName() == null) {
            result.add("Settings name cannot be null.");
        }

        if (model.getValue() == null) {
            result.add("Settings value cannot be null.");
        }

        return result;
    }
}
