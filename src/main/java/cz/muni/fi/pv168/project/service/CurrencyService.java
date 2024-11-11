package cz.muni.fi.pv168.project.service;

import cz.muni.fi.pv168.project.model.CurrencyDbModel;
import cz.muni.fi.pv168.project.repository.ICurrencyRepository;
import cz.muni.fi.pv168.project.model.Currency;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        return null;
    }
//    private class TestCurrencies {
//        private static Map<Integer, CurrencyDbModel> currencies = new HashMap<>() {
//            {put(1, new CurrencyDbModel(1, "Euro", "EUR", 1.0));}
//            {put(2, new CurrencyDbModel(2, "Dollar", "USD", 1.07));}
//            {put(3,  new CurrencyDbModel(3, "Czech crown", "CZK", 25.21));}
//        };
//        public static List<CurrencyDbModel> getTest() {
//            return currencies.values().stream().toList();
//
//        }
//        public static CurrencyDbModel getById(int id) {
//            return currencies.get(id);
//        }
//    }
}
