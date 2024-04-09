package tqs.bus.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import tqs.bus.services.TripService;
import tqs.bus.models.Trip;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@CrossOrigin("*")
@RequestMapping(path = "/trips")
public class TripsController {

    private static final Logger logger = LoggerFactory.getLogger(TripsController.class);

    private TripService tripService;

    @Autowired
    public TripsController(TripService tripService) {
        this.tripService = tripService;
    }

    @GetMapping("/list")
    public ResponseEntity<List<Trip>> listTrips(@RequestParam(required = false) String origin,
            @RequestParam(required = false) String destination, @RequestParam(required = false) String date,
            @RequestParam(required = false) String currency) {
        try {
            logger.info("List of trips requested");
            return ResponseEntity.ok(tripService.listTripsFiltered(origin, destination, date, currency));
        } catch (Exception e) {
            logger.error("Error occurred while listing trips: {}", e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error occurred while listing trips");
        }
    }

    @GetMapping("/get")
    public ResponseEntity<Trip> getTrip(@RequestParam int id, @RequestParam(required = false) String currency) {
        try {
            logger.info("Trip with id {} requested", id);
            return ResponseEntity.ok(tripService.getTrip(id, currency));
        } catch (Exception e) {
            logger.error("Error occurred while getting trip with id {}: {}", id, e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error occurred while getting trip");
        }
    }

    @GetMapping("/get_dates")
    public ResponseEntity<List<String>> getDates() {
        try {
            logger.info("List of dates requested");
            return ResponseEntity.ok(tripService.getDepartureTimes());
        } catch (Exception e) {
            logger.error("Error occurred while getting dates: {}", e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error occurred while getting dates");
        }
    }

    @GetMapping("/get_origins")
    public ResponseEntity<List<String>> getOrigins() {
        try {
            logger.info("List of origins requested");
            return ResponseEntity.ok(tripService.getOrigins());
        } catch (Exception e) {
            logger.error("Error occurred while getting origins: {}", e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error occurred while getting origins");
        }
    }

    @GetMapping("/get_destinations")
    public ResponseEntity<List<String>> getDestinations() {
        try {
            logger.info("List of destinations requested");
            return ResponseEntity.ok(tripService.getDestinations());
        } catch (Exception e) {
            logger.error("Error occurred while getting destinations: {}", e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error occurred while getting destinations");
        }
    }
}
