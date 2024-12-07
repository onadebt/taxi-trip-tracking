package cz.muni.fi.pv168.project.service;

import cz.muni.fi.pv168.project.model.Currency;
import cz.muni.fi.pv168.project.repository.ICurrencyRepository;
import cz.muni.fi.pv168.project.service.interfaces.ICurrencyService;
import cz.muni.fi.pv168.project.service.validation.ValidationResult;
import cz.muni.fi.pv168.project.service.validation.Validator;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class CurrencyService implements ICurrencyService {
    private final ICurrencyRepository currencyRepository;
    private final Validator<Currency> currencyValidator;

    public CurrencyService(ICurrencyRepository currencyRepository, Validator<Currency> currencyValidator) {
        this.currencyRepository = currencyRepository;
        this.currencyValidator = currencyValidator;
    }


    @Override
    public @Nullable Optional<Currency> findByCode(String code) {
        return currencyRepository.findByCode(code);
    }

    @Override
    public @Nullable Optional<Currency> findByName(String name) {
        return currencyRepository.findByName(name);
    }

    @Override
    public Optional<Currency> findById(Long id) {
        return currencyRepository.findById(id);
    }

    @Override
    public List<Currency> findAll() {
        return currencyRepository.findAll();
    }

    @Override
    public ValidationResult create(Currency newEntity) {
        var validationResult = currencyValidator.validate(newEntity);
        if (!validationResult.isValid()) {
            return validationResult;
        }

        if (currencyRepository.findByCode(newEntity.getCode()).isPresent()) {
            return ValidationResult.failed("Currency with this code already exists.");
        }

        if (currencyRepository.findByName(newEntity.getName()).isPresent()) {
            return ValidationResult.failed("Currency with this name already exists.");
        }

        var savedEntity = currencyRepository.create(newEntity);
        newEntity.setId(savedEntity.getId());

        return validationResult;
    }

    @Override
    public ValidationResult update(Currency entity) {
        var validationResult = currencyValidator.validate(entity);
        if (!validationResult.isValid()) {
            return validationResult;
        }

        var existingByCode = currencyRepository.findByCode(entity.getCode());
        if (existingByCode.isPresent() && !Objects.equals(existingByCode.get().getId(), entity.getId())) {
            return ValidationResult.failed("Currency with this code already exists.");
        }

        var existingByName = currencyRepository.findByName(entity.getName());
        if (existingByName.isPresent() && !Objects.equals(existingByName.get().getId(), entity.getId())) {
            return ValidationResult.failed("Currency with this name already exists.");
        }

        currencyRepository.update(entity);
        return validationResult;
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
