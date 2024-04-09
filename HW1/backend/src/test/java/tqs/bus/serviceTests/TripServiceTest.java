package tqs.bus.serviceTests;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import tqs.bus.models.Trip;
import tqs.bus.repositories.TripRepository;
import tqs.bus.services.CurrencyService;
import tqs.bus.services.TripService;

import java.util.List;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class TripServiceTest {

    @Mock
    private TripRepository tripRepository;

    @Mock
    private CurrencyService currencyService;

    @InjectMocks
    private TripService tripService;

    @Test
    @DisplayName("Test when trip exists then return true")
    void whenTripExists_thenReturnTrue() {
        when(tripRepository.existsById(1)).thenReturn(true);

        boolean exists = tripService.tripExists(1);
        assertTrue(exists);
        verify(tripRepository, times(1)).existsById(1);
    }

    @Test
    @DisplayName("Test when trip does not exist then return false")
    void whenTripDoesNotExist_thenReturnFalse() {
        when(tripRepository.existsById(2)).thenReturn(false);

        boolean exists = tripService.tripExists(2);
        assertFalse(exists);
        verify(tripRepository, times(1)).existsById(2);
    }

    @Test
    @DisplayName("Test when get trip then return trip")
    void whenGetTrip_thenReturnTrip() throws Exception {
        Trip trip = new Trip();
        when(tripRepository.findById(1)).thenReturn(trip);

        Trip result = tripService.getTrip(1, "EUR");
        assertNotNull(result);
        verify(tripRepository, times(1)).findById(1);
    }

    @Test
    @DisplayName("Test when list trips then return trips")
    void whenListTrips_thenReturnTrips() {
        Trip trip1 = new Trip();
        Trip trip2 = new Trip();
        when(tripRepository.findAll()).thenReturn(List.of(trip1, trip2));

        List<Trip> trips = tripService.listTrips();
        assertEquals(2, trips.size());
        assert(trips.contains(trip1));
        assert(trips.contains(trip2));
        verify(tripRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Test when list trips filtered then return filtered trips")
    void whenListTripsFiltered_thenReturnFilteredTrips() throws Exception {
        Trip trip1 = new Trip();
        Trip trip2 = new Trip();

        when(tripRepository.findByOriginAndDestinationAndDate("Aveiro", "Mirandela", "2024-04-09"))
                .thenReturn(List.of(trip1, trip2));

        List<Trip> trips = tripService.listTripsFiltered("Aveiro", "Mirandela", "2024-04-09", "EUR");
        assertEquals(2, trips.size());
        verify(tripRepository, times(1)).findByOriginAndDestinationAndDate("Aveiro", "Mirandela", "2024-04-09");
    }

    @Test
    @DisplayName("Test when list trips filtered then return empty list")
    void whenListTripsFiltered_thenReturnEmptyList() throws Exception {
        when(tripRepository.findByOriginAndDestinationAndDate("Aveiro", "Mirandela", "2024-04-09"))
                .thenReturn(List.of());

        List<Trip> trips = tripService.listTripsFiltered("Aveiro", "Mirandela", "2024-04-09", "EUR");
        assert(trips.isEmpty());
        verify(tripRepository, times(1)).findByOriginAndDestinationAndDate("Aveiro", "Mirandela", "2024-04-09");
    }

    @Test
    @DisplayName("Test when list trips filtered with different currencies then return filtered trips")
    void whenListTripsFilteredWithDifferentCurrencies_thenReturnFilteredTrips() throws Exception {
        Trip trip1 = new Trip();
        trip1.setPrice(10);

        when(tripRepository.findByOriginAndDestinationAndDate("Aveiro", "Mirandela", "2024-04-09"))
                .thenReturn(List.of(trip1));
        when(currencyService.exchange("EUR", "USD")).thenReturn(1.25);
        List<Trip> trips = tripService.listTripsFiltered("Aveiro", "Mirandela", "2024-04-09", "USD");
        assertEquals(1, trips.size());
        assertEquals(10*1.25, trips.get(0).getPrice());
        verify(tripRepository, times(1)).findByOriginAndDestinationAndDate("Aveiro", "Mirandela", "2024-04-09");
        verify(currencyService, times(1)).exchange("EUR", "USD");
    }

    @Test
    @DisplayName("Test when get departure times then return departure times")
    void whenGetDepartureTimes_thenReturnDepartureTimes() {
        when(tripRepository.findDepartureTimes()).thenReturn(List.of("09:00", "17:00"));

        List<String> times = tripService.getDepartureTimes();
        assertEquals(2, times.size());
        assert(times.contains("09:00"));
        assert(times.contains("17:00"));
        verify(tripRepository, times(1)).findDepartureTimes();
    }

    @Test
    @DisplayName("Test when get origins then return origins")
    void whenGetOrigins_thenReturnOrigins() {
        when(tripRepository.findOrigins()).thenReturn(List.of("Porto", "Aveiro"));

        List<String> origins = tripService.getOrigins();
        assertEquals(2, origins.size());
        assert(origins.contains("Porto"));
        assert(origins.contains("Aveiro"));
        verify(tripRepository, times(1)).findOrigins();
    }

    @Test
    @DisplayName("Test when get destinations then return destinations")
    void whenGetDestinations_thenReturnDestinations() {
        when(tripRepository.findDestinations()).thenReturn(List.of("Lisboa", "Mirandela"));

        List<String> destinations = tripService.getDestinations();
        assertEquals(2, destinations.size());
        assert(destinations.contains("Lisboa"));
        assert(destinations.contains("Mirandela"));
        verify(tripRepository, times(1)).findDestinations();
    }
}
