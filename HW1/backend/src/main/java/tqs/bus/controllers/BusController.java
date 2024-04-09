package tqs.bus.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import tqs.bus.models.Bus;
import tqs.bus.services.BusService;

@RestController
@CrossOrigin("*")
@RequestMapping(path = "/bus")
public class BusController {

    private static final Logger logger = LoggerFactory.getLogger(BusController.class);

    @Autowired
    private BusService busService;

    @GetMapping("/get")
    public ResponseEntity<Bus> getBus(@RequestParam int id) {

        logger.info("Fetching bus with ID {}", id);
        Bus bus = busService.getBus(id);
        if (bus != null) {
            logger.info("Bus with ID {} found", id);
            return ResponseEntity.ok(bus);
        } else {
            logger.warn("Bus with ID {} not found", id);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Bus not found!");
        }

    }

    @GetMapping("/list")
    public ResponseEntity<List<Bus>> listBuses() {
        try {
            logger.info("Listing all buses");
            return ResponseEntity.ok(busService.findAll());
        } catch (Exception e) {
            logger.error("Error occurred while listing buses: {}", e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Internal server error");
        }
    }

    @PostMapping("/add")
    public ResponseEntity<Bus> addBus(@RequestBody Bus bus) {
        try {
            logger.info("Adding new bus: {}", bus);
            Bus addedBus = busService.addBus(bus);
            logger.info("Bus added successfully with ID {}", addedBus.getId());
            return ResponseEntity.ok(addedBus);
        } catch (Exception e) {
            logger.error("Error occurred while adding bus: {}", e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Internal server error");
        }
    }
}
