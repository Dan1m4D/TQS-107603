package tqs.bus.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tqs.bus.models.Trip;
import tqs.bus.repositories.TripRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class TripService {

    private static final Logger logger = LoggerFactory.getLogger(TripService.class);

    @Autowired
    private TripRepository tripRepository;

    @Autowired
    private CurrencyService currencyService;

    public boolean tripExists(int tripID) {
        return tripRepository.existsById(tripID);
    }

    public Trip getTrip(int tripID, String currency) {
        Trip trip = tripRepository.findById(tripID);
        if (trip == null) {
            logger.error("Trip with ID {} not found", tripID);
            return null;
        }

        try {
            if (!"EUR".equals(currency)) { // Exchange currency if not EUR
                double exchangeRate = currencyService.exchange("EUR", currency);
                trip.setPrice(trip.getPrice() * exchangeRate);
                logger.info("Currency exchanged successfully from EUR to {}: {}", currency, exchangeRate);
            } else {
                logger.info("No currency exchange needed as target currency is EUR");
            }
        } catch (Exception e) {
            logger.error("Error occurred while exchanging currency: {}", e.getMessage());
        }

        return trip;
    }

    public List<Trip> listTrips() {
        return tripRepository.findAll();
    }

    public List<Trip> listTripsFiltered(String origin, String destination, String date, String currency) {
        List<Trip> trips = tripRepository.findByOriginAndDestinationAndDate(origin, destination, date);

        if (!"EUR".equals(currency)) { // Exchange currency if not EUR
            try {
                double exchangeRate = currencyService.exchange("EUR", currency);
                for (Trip trip : trips) {
                    trip.setPrice(trip.getPrice() * exchangeRate);
                }
                logger.info("Currency exchanged successfully from EUR to {}: {}", currency, exchangeRate);
            } catch (Exception e) {
                logger.error("Error occurred while exchanging currency: {}", e.getMessage());
            }
        } else {
            logger.info("No currency exchange needed as target currency is EUR");
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
