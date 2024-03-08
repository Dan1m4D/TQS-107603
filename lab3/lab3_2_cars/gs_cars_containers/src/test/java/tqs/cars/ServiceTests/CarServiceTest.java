package test.java.tqs.cars.ServiceTests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import tqs.cars.model.Car;
import tqs.cars.repository.CarRepository;
import tqs.cars.services.CarManagerService;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.internal.verification.VerificationModeFactory.times;

@ExtendWith(MockitoExtension.class)
class CarServiceTest {
    @Mock(lenient = true)
    private CarRepository carRepository;

    @InjectMocks
    private CarManagerService carService;

    @BeforeEach
    void setUp() {
        Car audi = new Car("Audi", "A8");
        audi.setCarId(1L);
        Car bmw = new Car("BMW", "X5");
        Car citroen = new Car("Citroen", "C4");

        List<Car> allCars = Arrays.asList(audi, bmw, citroen);

        given(carRepository.findAll()).willReturn(allCars);
        given(carRepository.findByCarId(audi.getCarId())).willReturn(audi);
        given(carRepository.findByCarId(bmw.getCarId())).willReturn(bmw);
        given(carRepository.findByCarId(citroen.getCarId())).willReturn(citroen);

        given(carRepository.findByCarId(-99L)).willReturn(null);
    }

    @Test
    public void whenValidId_thenCarShouldBeFound() {
        Long id = 1L;
        Optional<Car> found = carService.getCarDetails(id);
        assertThat(found).isNotNull();
        assertThat(found.get().getCarId()).isEqualTo(id);
        verifyFindByCarIdIsCalledOnce(id);
    }

    @Test
    public void whenInValidId_thenCarShouldNotBeFound() {
        Optional<Car> fromDb = carService.getCarDetails(-99L);
        assertThat(fromDb).isEmpty();
        verifyFindByCarIdIsCalledOnce(-99L);
    }

    @Test
    public void given3Cars_whengetAll_thenReturn3Records() {
        Car audi = new Car("Audi", "A8");
        Car bmw = new Car("BMW", "X5");
        Car citroen = new Car("Citroen", "C4");

        List<Car> allCars = carService.getAllCars();
        assertThat(allCars).hasSize(3).extracting(Car::getMaker).contains(audi.getMaker(), bmw.getMaker(), citroen.getMaker());
        verifyFindAllCarsIsCalledOnce();
    }

    private void verifyFindByCarIdIsCalledOnce(Long id) {
        Mockito.verify(carRepository, Mockito.times(1)).findByCarId(id);
    }

    private void verifyFindAllCarsIsCalledOnce() {
        Mockito.verify(carRepository, Mockito.times(1)).findAll();
    }
}
