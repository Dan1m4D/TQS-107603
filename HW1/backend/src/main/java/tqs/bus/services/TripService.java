package tqs.bus.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tqs.bus.models.Trip;
import tqs.bus.repositories.TripRepository;

@Service
public class TripService {

    @Autowired
    private TripRepository tripRepository;

    @Autowired
    private CurrencyService currencyService;
    
    public boolean tripExists(int tripID) {
        return tripRepository.existsById(tripID);
    }

     public Trip getTrip(int tripID, String currency) {

        Trip trip = tripRepository.findById(tripID);

        if (currency == null || currency.equals("EUR")) { // EUR is base currency, no need to exchange
            return trip;
        }

        double exchange_rate = 1.0;

        try {
            System.out.println("Exchanging currency from EUR to " + currency);
            exchange_rate = currencyService.exchange("EUR", currency);
        } catch (Exception e) {

        }
        trip.setPrice(trip.getPrice() * exchange_rate);

        return trip;
    }

    public List<Trip> listTrips() {
        return tripRepository.findAll();
    }

    public List<Trip> listTripsFiltered(String origin, String destination, String date, String currency) {

        List<Trip> trips = new ArrayList<Trip>();
        trips = tripRepository.findByOriginAndDestinationAndDate(origin, destination, date);

        if (currency == null || currency.equals("EUR")) { // EUR is base currency, no need to exchange
            return trips;
        }

        double exchange_rate = 1.0;

        try {
            exchange_rate = currencyService.exchange("EUR", currency);
        } catch (Exception e) {
        }

        for (Trip trip : trips) {
            trip.setPrice(trip.getPrice() * exchange_rate);
        }

        return trips;
    }

    public List<String> getDepartureTimes() {
        return tripRepository.findDepartureTimes();
    }

    public List<String> getOrigins() {
        return tripRepository.findOrigins();
    }

    public List<String> getDestinations() {
        return tripRepository.findDestinations();
    }

}
