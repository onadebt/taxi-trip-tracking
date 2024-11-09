package cz.muni.fi.pv168.project.repository;

import cz.muni.fi.pv168.project.model.CurrencyDbModel;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class CurrencyRepository implements ICurrencyRepository {
    @Override
    public void create(CurrencyDbModel currency) {

    }

    @Override
    public void update(CurrencyDbModel currencyDbModel) {

    }

    @Override
    public void delete(int currencyId) {

    }

    @Override
    public @Nullable CurrencyDbModel getById() {
        return null;
    }

    @Override
    public @Nullable CurrencyDbModel getByTag(String tag) {
        return null;
    }

    @Override
    public List<CurrencyDbModel> get() {
        return List.of();
    }
}
