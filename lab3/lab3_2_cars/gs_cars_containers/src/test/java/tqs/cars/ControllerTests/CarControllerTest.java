package tqs.cars.ControllerTests;

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import tqs.cars.controller.CarController;
import tqs.cars.model.Car;
import tqs.cars.services.CarManagerService;

import java.util.Arrays;

import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CarController.class)
class CarControllerTest {
    @Autowired
    MockMvc mvc;

    @MockBean
    private CarManagerService carService;

    @BeforeEach
    public void setUp() throws Exception {
    }

    @Test
    public void whenPostCar_thenCreateCar() throws Exception {
        Car audi = new Car("Audi", "A8");
        when(carService.save(Mockito.any())).thenReturn(audi);
        mvc.perform(post("/api/cars").contentType(MediaType.APPLICATION_JSON).content(JsonUtil.toJson(audi)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.maker", is("Audi")))
                .andExpect(jsonPath("$.model", is("A8")));
        verify(carService, times(1)).save(Mockito.any());
    }

    @Test
    public void whenGetCar_thenReturnCar() throws Exception {
        Car audi = new Car("Audi", "A8");
        when(carService.getCarDetails(1L)).thenReturn(java.util.Optional.of(audi));
        mvc.perform(get("/api/cars/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.maker", is("Audi")))
                .andExpect(jsonPath("$.model", is("A8")));
    }

    @Test
    public void whenGetCars_thenReturnAllCars() throws Exception {
        Car audi = new Car("Audi", "A8");
        Car bmw = new Car("BMW", "X5");
        when(carService.getAllCars()).thenReturn(Arrays.asList(audi, bmw));
        mvc.perform(get("/api/cars").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(greaterThanOrEqualTo(2))))
                .andExpect(jsonPath("$[0].maker", is(audi.getMaker())))
                .andExpect(jsonPath("$[1].maker", is(bmw.getMaker())));

    }
}