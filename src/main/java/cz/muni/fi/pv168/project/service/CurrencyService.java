package cz.muni.fi.pv168.project.service;

import cz.muni.fi.pv168.project.model.CurrencyDbModel;
import cz.muni.fi.pv168.project.model.enums.CurrencyCode;
import cz.muni.fi.pv168.project.repository.ICurrencyRepository;
import cz.muni.fi.pv168.project.model.Currency;
import cz.muni.fi.pv168.project.service.interfaces.ICurrencyService;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static javax.swing.UIManager.put;

public class CurrencyService implements ICurrencyService {
    ICurrencyRepository currencyRepository;
    public CurrencyService(ICurrencyRepository currencyRepository) {
        this.currencyRepository = currencyRepository;
    }
    @Override
    public void create(Currency currency) {

    }

    @Override
    public void update(Currency currency) {

    }

    @Override
    public void delete(Long currencyId) {

    }

    @Override
    public @Nullable Currency getById(Long currencyId) {
        return null;
    }

    @Override
    public @Nullable Currency getByTag(String tag) {
        return null;
    }

    @Override
    public List<Currency> getAll() {
        return TestCurrencies.getTest();
    }

    private class TestCurrencies {
        private static Map<Integer, Currency> currencies = new HashMap<>() {
            {put(1, new Currency(1L, "Euro", "EUR", 1.0));}
            {put(2, new Currency(2L, "Dollar", "USD", 1.07));}
            {put(3,  new Currency(3L, "Czech crown", "CZK", 25.21));}
        };
        public static List<Currency> getTest() {
            return currencies.values().stream().toList();

        }
        public static Currency getById(int id) {
            return currencies.get(id);
        }
    }

//    private final List<Currency>  testCurrencies = List.of(
//        new Currency("US Dollar", CurrencyCode.USD, 22D),
//        new Currency("Euro", CurrencyCode.EUR, 25D),
//        new Currency("British Pound", CurrencyCode.GBP, 29D),
//        new Currency("Japanese Yen", CurrencyCode.JPY, 0.2D),
//        new Currency("Czech Crown", CurrencyCode.CZK, 1D)
//    );
}
