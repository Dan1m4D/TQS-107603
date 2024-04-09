package tqs.bus.controllerTests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import tqs.bus.controllers.BusController;
import tqs.bus.models.Bus;
import tqs.bus.services.BusService;

import java.util.Arrays;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BusController.class)
class BusControllerTest {

    @Autowired
    MockMvc mvc;

    @MockBean
    private BusService busService;

    @Test
    @DisplayName("Test when get a bus then return a bus")
    void whenGetBus_thenReturnBus() throws Exception {
        Bus bus = new Bus();
        bus.setId(1);
        bus.setName("Bus 1");
        bus.setSeats(10);

        when(busService.getBus(1)).thenReturn(bus);

        mvc.perform(get("/bus/get?id=1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("Bus 1")))
                .andExpect(jsonPath("$.seats", is(10)));

        verify(busService, times(1)).getBus(1);
    }

    @Test
    @DisplayName("Test when get a bus by inexistent id then return not found")
    void whenGetBusByInexistentId_thenReturnNotFound() throws Exception {
                
        mvc.perform(get("/bus/get?id=1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        verify(busService, times(1)).getBus(1);
    }

    @Test
    @DisplayName("Test when get all buses then return all buses")
    void whenGetBuses_thenReturnAllBuses() throws Exception {
        Bus bus1 = new Bus();
        bus1.setId(1);
        bus1.setName("Bus 1");
        bus1.setSeats(10);

        Bus bus2 = new Bus();
        bus2.setId(2);
        bus2.setName("Bus 2");
        bus2.setSeats(20);

        when(busService.findAll()).thenReturn(Arrays.asList(bus1, bus2));

        mvc.perform(get("/bus/list").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name", is(bus1.getName())))
                .andExpect(jsonPath("$[1].name", is(bus2.getName())));

        verify(busService, times(1)).findAll();
    }
}
