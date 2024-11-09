package cz.muni.fi.pv168.project.repository;

import cz.muni.fi.pv168.project.model.CurrencyDbModel;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public interface ICurrencyRepository {
    void create(CurrencyDbModel currency);
    void update(CurrencyDbModel currencyDbModel);
    void delete(int currencyId);
    @Nullable CurrencyDbModel getById();
    @Nullable CurrencyDbModel getByTag(String tag);
    List<CurrencyDbModel> get();
}
