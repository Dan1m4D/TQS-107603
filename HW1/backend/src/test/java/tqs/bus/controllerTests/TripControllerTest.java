package tqs.bus.controllerTests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import tqs.bus.controllers.TripsController;
import tqs.bus.models.Trip;
import tqs.bus.services.TripService;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.hasSize;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TripsController.class)
class TripsControllerTest {

    @Autowired
    MockMvc mvc;

    @MockBean
    private TripService tripService;

    @BeforeEach
    public void setUp() throws Exception {
        when(tripService.getDepartureTimes()).thenReturn(List.of("", "2024-04-09", "2024-04-10"));
        when(tripService.getOrigins()).thenReturn(List.of("Lisboa", "Porto", "Mirandela"));
        when(tripService.getDestinations()).thenReturn(List.of("Porto", "Lisboa", "Aveiro"));
        when(tripService.listTripsFiltered("Mirandela", "Aveiro", "2024-04-09", "EUR"))
                .thenReturn(List.of(new Trip(), new Trip(), new Trip()));
        when(tripService.getTrip(1, "EUR")).thenReturn(null);
    }

    @Test
    @DisplayName("Test when list trips then return all trips")
    void whenListTrips_thenReturnAllTrips() throws Exception {
        mvc.perform(get("/trips/list?origin=Mirandela&destination=Aveiro&date=2024-04-09&currency=EUR")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)));

        verify(tripService, times(1)).listTripsFiltered(anyString(), anyString(), anyString(), anyString());
    }

    @Test
    @DisplayName("Test when get trip then return the trip")
    void whenGetTrip_thenReturnTrip() throws Exception {

        mvc.perform(get("/trips/get?id=1&currency=EUR"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").doesNotExist());

        verify(tripService, times(1)).getTrip(1, "EUR");
    }

    @Test
    @DisplayName("Test when get trip with inexistent id then return empty")
    void whenGetTripWithInexistentId_thenReturnEmpty() throws Exception {
        mvc.perform(get("/trips/get?id=0&currency=EUR"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").doesNotExist());

        verify(tripService, times(0)).getTrip(100, "EUR");
    }

    @Test
    @DisplayName("Test when get trip with invalid id then return error")
    void whenGetTripWithInvalidId_thenReturnError() throws Exception {
        mvc.perform(get("/trips/get?id=tqs&currency=EUR"))
                .andExpect(status().isBadRequest());

    }

    @Test
    @DisplayName("Test when get dates then return the available dates")
    void whenGetDates_thenReturnDates() throws Exception {

        mvc.perform(get("/trips/get_dates"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)));

        verify(tripService, times(1)).getDepartureTimes();
    }

    @Test
    @DisplayName("Test when get origins then return the available origins")
    void whenGetOrigins_thenReturnOrigins() throws Exception {

        mvc.perform(get("/trips/get_origins"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0]", is("Lisboa")));

        verify(tripService, times(1)).getOrigins();
    }

    @Test
    @DisplayName("Test when get destinations then return the available destinations")
    void whenGetDestinations_thenReturnDestinations() throws Exception {
        

        mvc.perform(get("/trips/get_destinations"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0]", is("Porto")));

        verify(tripService, times(1)).getDestinations();
    }

}
