package tqs.bus.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tqs.bus.services.TripService;
import tqs.bus.models.Trip;
import java.util.List;
import org.springframework.web.bind.annotation.RequestParam;
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
            @RequestParam(required = false) String destination, @RequestParam(required = false) String date , @RequestParam(required = false) String currency) throws Exception {

        logger.info("List of trips requested");
    return ResponseEntity.ok(tripService.listTripsFiltered(origin, destination, date, currency));

    }

    @GetMapping("/get")
    public ResponseEntity<Trip> getTrip(@RequestParam int id, @RequestParam(required=false) String currency) {
        logger.info("Trip with id %s requested", id);
        return ResponseEntity.ok(tripService.getTrip(id, currency));
    }

    @GetMapping("/get_dates")
    public ResponseEntity<List<String>> getDates() {
        logger.info("List of dates requested");
        return ResponseEntity.ok(tripService.getDepartureTimes());
    }

    @GetMapping("/get_origins")
    public ResponseEntity<List<String>> getOrigins() {
        logger.info("List of origins requested");
        return ResponseEntity.ok(tripService.getOrigins());
    }

    @GetMapping("/get_destinations")
    public ResponseEntity<List<String>> getDestinations() {
        logger.info("List of destinations requested");
        return ResponseEntity.ok(tripService.getDestinations());
    }

}