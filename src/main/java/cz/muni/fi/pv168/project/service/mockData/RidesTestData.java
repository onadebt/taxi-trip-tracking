package cz.muni.fi.pv168.project.service.mockData;

import cz.muni.fi.pv168.project.model.Category;
import cz.muni.fi.pv168.project.model.Currency;
import cz.muni.fi.pv168.project.model.Ride;
import cz.muni.fi.pv168.project.model.enums.TripType;
import cz.muni.fi.pv168.project.ui.resources.Icons;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

public class RidesTestData {

    static Currency euro = new Currency(1L, "Euro", "EUR", 1D);
    static Currency dollar = new Currency(2L, "Dollar", "USD", 1.07);
    static Currency czk = new Currency(3L, "Czech crown", "CZK", 25.21);

    static List<Category> categories = CategoryTestData.getMockCategories();

    public static List<Ride> getMockRides() {
        return Stream.of(
                new Ride(1L, 30.0, euro, 100D, categories.get(0), categories.get(0).getIcon(), TripType.Paid, 1, Instant.now(), UUID.randomUUID()),
                new Ride(2L, 50.0, dollar, 200D, categories.get(1), categories.get(1).getIcon(), TripType.Personal, 2, Instant.now(), UUID.randomUUID()),
                new Ride(3L, 70.0, czk, 300D, categories.get(2), categories.get(2).getIcon(), TripType.Paid, 3, Instant.now(), UUID.randomUUID())
        ).toList();
    }
}
