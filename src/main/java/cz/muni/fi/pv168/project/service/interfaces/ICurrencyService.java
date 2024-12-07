package cz.muni.fi.pv168.project.service.interfaces;

import cz.muni.fi.pv168.project.model.Currency;
import cz.muni.fi.pv168.project.service.crud.CrudService;

import java.util.Optional;

public interface ICurrencyService extends CrudService<Currency> {

    /**
     * Find currency by given {@code tag}.
     */
    Optional<Currency> findByCode(String code);

    /**
     * Find currency by given {@code name}.
     */
    Optional<Currency> findByName(String name);
}
