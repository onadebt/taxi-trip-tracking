package cz.muni.fi.pv168.project.service;

import cz.muni.fi.pv168.project.model.Ride;
import cz.muni.fi.pv168.project.model.RideFilterCriteria;


import java.util.List;
import java.util.stream.Collectors;

public class RideFilterService {

    public List<Ride> filterRides(List<Ride> rides, RideFilterCriteria criteria) {

        return rides.stream()
                .filter(ride -> criteria.getMinAmount() == null || ride.getAmountCurrency().compareTo(criteria.getMinAmount()) >= 0)
                .filter(ride -> criteria.getMaxAmount() == null || ride.getAmountCurrency().compareTo(criteria.getMaxAmount()) <= 0)
                .filter(ride -> criteria.getCurrency() == null || ride.getCurrency().getCode().equals(criteria.getCurrency().getCode()))
                .filter(ride -> criteria.getMinDistance() == null || ride.getDistance() >= criteria.getMinDistance())
                .filter(ride -> criteria.getMaxDistance() == null || ride.getDistance() <= criteria.getMaxDistance())
                .filter(ride -> criteria.getCategory() == null || (ride.getCategory()!= null && ride.getCategory().getName().equals(criteria.getCategory().getName())))
                .filter(ride -> criteria.getTripType() == null || ride.getTripType().equals(criteria.getTripType()))
                .filter(ride -> criteria.getMinPassengers() == null || ride.getNumberOfPassengers() >= criteria.getMinPassengers())
                .filter(ride -> criteria.getMaxPassengers() == null || ride.getNumberOfPassengers() <= criteria.getMaxPassengers())
                .filter(ride -> criteria.getStartDate() == null || !ride.getCreatedAt().isBefore(criteria.getStartDate()))
                .filter(ride -> criteria.getEndDate() == null || !ride.getCreatedAt().isAfter(criteria.getEndDate()))
                .collect(Collectors.toList());
    }
}
