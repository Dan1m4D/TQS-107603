package test.java.tqs.cars.RepositoryTests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import tqs.cars.repository.CarRepository;
import tqs.cars.model.Car;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class CarRepositoryTest {
    @Autowired
    private TestEntityManager entityManager;
    @Autowired
    private CarRepository carRepository;

    @BeforeEach
    void setUp() {
    }

    @Test
    public void whenFindCarByCarId_thenReturnCar() {
        Car audi = new Car("Audi", "A8");
        entityManager.persistAndFlush(audi);
        Car found = carRepository.findByCarId(audi.getCarId());
        assertThat(found).isNotNull();
        assertThat(found.getMaker()).isEqualTo(audi.getMaker());
        assertThat(found.getModel()).isEqualTo(audi.getModel());
        assertThat(found.getCarId()).isEqualTo(audi.getCarId());
    }

    @Test
    public void whenInvalidCarId_thenReturnNull() {
        Car fromDb = carRepository.findByCarId(-11L);
        assertThat(fromDb).isNull();
    }

    @Test
    public void givenSetOfCars_whenFindAll_thenReturnAllCars() {
        Car audi = new Car("Audi", "A8");
        Car bmw = new Car("BMW", "X5");
        Car citroen = new Car("Citroen", "C4");
        entityManager.persist(audi);
        entityManager.persist(bmw);
        entityManager.persist(citroen);
        entityManager.flush();
        List<Car> allCars = carRepository.findAll();
        assertThat(allCars).hasSize(3).extracting(Car::getMaker).containsOnly(audi.getMaker(), bmw.getMaker(), citroen.getMaker());
    }
    
}
