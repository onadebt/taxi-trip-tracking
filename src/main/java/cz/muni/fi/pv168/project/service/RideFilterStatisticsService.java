package cz.muni.fi.pv168.project.service;

import cz.muni.fi.pv168.project.model.Ride;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.ZoneId;
import java.util.List;
import java.util.stream.Collectors;

public class RideFilterStatisticsService {
    public List<Ride> filterRidesByDay(List<Ride> rideHistory) {
        LocalDate today = LocalDate.now();
        return rideHistory.stream()
                .filter(ride -> LocalDate.ofInstant(ride.getCreatedAt(), ZoneId.systemDefault()).isEqual(today))
                .collect(Collectors.toList());
    }

    public List<Ride> filterRidesByWeek(List<Ride> rideHistory) {
        LocalDate today = LocalDate.now();
        LocalDate startOfWeek = today.with(DayOfWeek.MONDAY);
        LocalDate endOfWeek = today.with(DayOfWeek.SUNDAY);

        return rideHistory.stream()
                .filter(ride -> {
                    LocalDate rideDate = LocalDate.ofInstant(ride.getCreatedAt(), ZoneId.systemDefault());
                    return !rideDate.isBefore(startOfWeek) && !rideDate.isAfter(endOfWeek);
                })
                .collect(Collectors.toList());
    }

    public List<Ride> filterRidesByMonth(List<Ride> rideHistory) {
        YearMonth currentMonth = YearMonth.now();
        return rideHistory.stream()
                .filter(ride -> YearMonth.from(LocalDate.ofInstant(ride.getCreatedAt(), ZoneId.systemDefault())).equals(currentMonth))
                .collect(Collectors.toList());
    }
}

