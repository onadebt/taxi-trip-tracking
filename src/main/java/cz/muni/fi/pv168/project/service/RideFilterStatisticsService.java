package cz.muni.fi.pv168.project.service;

import cz.muni.fi.pv168.project.model.Ride;

import java.time.*;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

public class RideFilterStatisticsService {
    public List<Ride> filterRidesByDay(List<Ride> rideHistory) {
        Instant today = Instant.now().truncatedTo(ChronoUnit.DAYS);
        return rideHistory.stream()
                .filter(ride -> today.equals(ride.getCreatedAt().truncatedTo(ChronoUnit.DAYS)))
                .collect(Collectors.toList());
    }

    public List<Ride> filterRidesByWeek(List<Ride> rideHistory) {
        Instant today = Instant.now();
        Instant startOfWeek = today.atZone(ZoneId.systemDefault()).with(DayOfWeek.MONDAY).toInstant();
        Instant endOfWeek = today.atZone(ZoneId.systemDefault()).with(DayOfWeek.SUNDAY).toInstant();


        return rideHistory.stream()
                .filter(ride -> !ride.getCreatedAt().isBefore(startOfWeek) && !ride.getCreatedAt().isAfter(endOfWeek))
                .collect(Collectors.toList());
    }

    public List<Ride> filterRidesByMonth(List<Ride> rideHistory) {
        YearMonth currentMonth = YearMonth.now();
        return rideHistory.stream()
                .filter(ride -> YearMonth.from(LocalDate.ofInstant(ride.getCreatedAt(), ZoneId.systemDefault())).equals(currentMonth))
                .collect(Collectors.toList());
    }
}

