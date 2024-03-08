package tqs.cars.ControllerTests;


import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import tqs.cars.model.Car;
import tqs.cars.repository.CarRepository;
import static org.assertj.core.api.Assertions.assertThat;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;

import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
// switch AutoConfigureTestDatabase with TestPropertySource to use a real database
//@TestPropertySource( locations = "application-integrationtest.properties")
@AutoConfigureTestDatabase
public class CarControllerRestIT {

    @LocalServerPort
    int randomServerPort;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private CarRepository carRepository;

    @AfterEach
    public void resetDb() {
        carRepository.deleteAll();
    }

    @Test
    public void whenValidInput_thenCreateCar(){
        Car bmw = new Car("BMW","I8");
        ResponseEntity<Car> entity = restTemplate
                .postForEntity("/api/cars",
                        bmw, Car.class
                );
        List<Car> cars_db = carRepository.findAll();
        assertThat(cars_db)
                .extracting(Car::getModel)
                .containsOnly("I8");
    }

    @Test
    public void givenCars_when_GetCars_then200() {
        createTestCar("I8", "BMW");
        createTestCar("A4", "Audi");
        ResponseEntity<List<Car>> response = restTemplate
                .exchange("/api/v1/cars",
                        HttpMethod.GET, null,
                        new ParameterizedTypeReference<List<Car>>() {
                        });
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).extracting(Car::getModel).containsExactly("I8", "A4");
    }
    @Test
    void getCar_returnsCarDetails() {
        createTestCar("I8","BMW");
        ResponseEntity<Car> entity = restTemplate.getForEntity("/api/cars/1", Car.class);

        assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(entity.getBody()).extracting(Car::getModel).isEqualTo("BMW");
    }


    private void createTestCar(String model, String maker) {
        Car car = new Car(model, maker);
        carRepository.saveAndFlush(car);
    }
}