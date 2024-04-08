package tqs.bus.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import tqs.bus.models.Bus;
import tqs.bus.services.BusService;

@RestController
@CrossOrigin("*")
@RequestMapping(path = "/bus")
public class BusController {

    @Autowired
    private BusService busService;


    @GetMapping("/get")
    public ResponseEntity<Bus> getBus(@RequestParam int id) {
        Bus bus = busService.getBus(id);
        if (bus != null) {
            return ResponseEntity.ok(bus);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Bus not found!");
        }
    }

    @GetMapping("/list")
    public ResponseEntity<List<Bus>> listBuses() {
        return ResponseEntity.ok(busService.findAll());
    }


    @PostMapping("/add")
    public ResponseEntity<Bus> addBus(@RequestBody Bus bus) {
        Bus bus2 = busService.addBus(bus);
        return ResponseEntity.ok(bus2);
    }
    



    
}
