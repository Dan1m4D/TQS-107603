package tqs.cars.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tqs.cars.model.Car;
import tqs.cars.services.CarManagerService;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CarController {

    private final CarManagerService carService;

    public CarController(CarManagerService carService) {
        this.carService = carService;
    }

    @PostMapping("/cars")
    public ResponseEntity<Car> createCar(@RequestBody Car oneCar) {
        HttpStatus status = HttpStatus.CREATED;
        Car saved = carService.save(oneCar);
        return new ResponseEntity<>(saved, status);
    }

    @GetMapping(path = "/cars", produces = "application/json")
    public List<Car> getAllCars() {
        return carService.getAllCars();
    }

    @GetMapping("/cars/{id}")
    public ResponseEntity<Car> getCarById(@PathVariable(value = "id") Long id) throws Exception{
        Car car = carService.getCarDetails(id)
                .orElseThrow(() -> new Exception("Car not found for id: " + id));
        return ResponseEntity.ok().body(car);
    }
}
