package cz.muni.fi.pv168.project.service.crud;

import cz.muni.fi.pv168.project.model.Currency;
import cz.muni.fi.pv168.project.repository.CurrencyRepository;
import cz.muni.fi.pv168.project.service.validation.ValidationResult;
import cz.muni.fi.pv168.project.service.validation.Validator;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class CurrencyCrudService implements CrudService<Currency> {
    private final CurrencyRepository currencyRepository;
    private final Validator<Currency> currencyValidator;

    public CurrencyCrudService(CurrencyRepository currencyRepository, Validator<Currency> currencyValidator) {
        this.currencyRepository = currencyRepository;
        this.currencyValidator = currencyValidator;
    }

    @Override
    public List<Currency> findAll() {
        return currencyRepository.findAll();
    }

    @Override
    public ValidationResult create(Currency newEntity) {
        // Check if a currency name and currency code is in right format
        var validationResult = currencyValidator.validate(newEntity);
        if (!validationResult.isValid()) {
            return validationResult;
        }

        // Check if a currency with the same name or code already exists
        if (currencyRepository.getByName(newEntity.getName()) != null) {
            return ValidationResult.failed("Currency with this name already exists.");
        }
        if (currencyRepository.getByCode(newEntity.getCode()) != null) {
            return ValidationResult.failed("Currency with this code already exists.");
        }

        // All validation passed
        Currency savedEntity = currencyRepository.create(newEntity);
        newEntity.setId(savedEntity.getId());

        return validationResult;
    }

    @Override
    public ValidationResult update(Currency entity) {
        var validationResult = currencyValidator.validate(entity);
        if (validationResult.isValid()) {
            currencyRepository.update(entity);

//            Logger.info("Updated employee: {}", entity);
        }

        return validationResult;
    }

    public @Nullable Currency getById(Long currencyId) {
        return currencyRepository.getById(currencyId);
    }

    public @Nullable Currency getByTag(String tag) {
        return currencyRepository.getByCode(tag);
    }

    @Override
    public void deleteById(Long id) {
        currencyRepository.deleteById(id);
    }

    @Override
    public void deleteAll() {
        currencyRepository.deleteAll();
    }


}
