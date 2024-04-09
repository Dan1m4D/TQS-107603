package tqs.bus.repositoryTests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import tqs.bus.models.Trip;
import tqs.bus.repositories.TripRepository;

@DataJpaTest
class TripRepositoryTests {

    @Autowired
    private TripRepository tripRepository;
    
    @Test
    @DisplayName("Test when save trip then find it by id")
    void whenSaveTrip_thenFindItById() {
        Trip trip = new Trip();
        trip.setDepartureDay("2023-04-09");
        trip.setOrigin("London");
        trip.setDestination("New York");
        tripRepository.save(trip);

        Trip trip1 = tripRepository.findById(trip.getId());

        assertEquals(trip, trip1);
        assertEquals(trip.getDepartureDay(), trip1.getDepartureDay());
        assertEquals(trip.getOrigin(), trip1.getOrigin());
        assertEquals(trip.getDestination(), trip1.getDestination());
    }

    @Test
    @DisplayName("Test when save trip then find departure days")
    void whenSaveTrip_thenFindDepartureDays() {
        Trip trip = new Trip();
        trip.setDepartureDay("2023-04-09");
        trip.setOrigin("London");
        trip.setDestination("New York");
        tripRepository.save(trip);

        Trip trip1 = new Trip();
        trip1.setOrigin("Las Vegas");
        trip1.setDestination("New York");
        trip1.setDepartureDay("2023-04-08");
        tripRepository.save(trip1);

        Trip trip2 = new Trip();
        trip2.setOrigin("New York");
        trip2.setDestination("Las Vegas");
        trip2.setDepartureDay("2023-04-04");
        tripRepository.save(trip2);

        Trip trip3 = new Trip();
        trip3.setOrigin("Washington DC");
        trip3.setDestination("Texas");
        trip3.setDepartureDay("2024-04-03");
        tripRepository.save(trip3);

        List<String> departureDays = tripRepository.findDepartureTimes();

        assert(departureDays.contains("2023-04-09"));
        assert(departureDays.contains("2023-04-08"));
        assert(departureDays.contains("2023-04-04"));
        assert(departureDays.contains("2024-04-03"));
    }

    @Test
    @DisplayName("Test when save trip then find origins")
    void whenSaveTrip_thenFindOrigins() {
        Trip trip = new Trip();
        trip.setDepartureDay("2023-04-09");
        trip.setOrigin("London");
        trip.setDestination("New York");
        tripRepository.save(trip);

        Trip trip1 = new Trip();
        trip1.setOrigin("Las Vegas");
        trip1.setDestination("New York");
        trip1.setDepartureDay("2023-04-08");
        tripRepository.save(trip1);

        Trip trip2 = new Trip();
        trip2.setOrigin("New York");
        trip2.setDestination("Las Vegas");
        trip2.setDepartureDay("2023-04-04");
        tripRepository.save(trip2);

        Trip trip3 = new Trip();
        trip3.setOrigin("Washington DC");
        trip3.setDestination("Texas");
        trip3.setDepartureDay("2024-04-03");
        tripRepository.save(trip3);

        List<String> origins = tripRepository.findOrigins();

        assert(origins.contains("London"));
        assert(origins.contains("Las Vegas"));
        assert(origins.contains("New York"));
        assert(origins.contains("Washington DC"));
        assertFalse(origins.contains("Texas"));
    }

    @Test
    @DisplayName("Test when save trip then find destinations")
    void whenSaveTrip_thenFindDestinations() {
        Trip trip = new Trip();
        trip.setDepartureDay("2023-04-09");
        trip.setOrigin("London");
        trip.setDestination("New York");
        tripRepository.save(trip);

        Trip trip1 = new Trip();
        trip1.setOrigin("Las Vegas");
        trip1.setDestination("New York");
        trip1.setDepartureDay("2023-04-08");
        tripRepository.save(trip1);

        Trip trip2 = new Trip();
        trip2.setOrigin("New York");
        trip2.setDestination("Las Vegas");
        trip2.setDepartureDay("2023-04-04");
        tripRepository.save(trip2);

        Trip trip3 = new Trip();
        trip3.setOrigin("Washington DC");
        trip3.setDestination("Texas");
        trip3.setDepartureDay("2024-04-03");
        tripRepository.save(trip3);

        List<String> destinations = tripRepository.findDestinations();

        assert(destinations.contains("New York"));
        assert(destinations.contains("Las Vegas"));
        assert(destinations.contains("Texas"));
        assertFalse(destinations.contains("Washington DC"));
    }



    @Test
    @DisplayName("Test when save trip then find it by origin, destination and date")
    void whenSaveTrip_thenFindItByOriginAndDestinationAndDate() {
        Trip trip = new Trip();
        trip.setDepartureDay("2023-04-09");
        trip.setOrigin("London");
        trip.setDestination("New York");
        tripRepository.save(trip);

        Trip trip1 = new Trip();
        trip1.setOrigin("London");
        trip1.setDestination("New York");
        trip1.setDepartureDay("2023-04-09");
        tripRepository.save(trip1);

        Trip trip2 = new Trip();
        trip2.setOrigin("New York");
        trip2.setDestination("London");
        trip2.setDepartureDay("2023-04-09");
        tripRepository.save(trip2);

        Trip trip3 = new Trip();
        trip3.setOrigin("New York");
        trip3.setDestination("London");
        trip3.setDepartureDay("2024-04-03");
        tripRepository.save(trip3);

        List<Trip> trips = tripRepository.findByOriginAndDestinationAndDate("London", "New York", "2023-04-09");

        assert(trips.contains(trip));
        assert(trips.contains(trip1));
        assertFalse(trips.contains(trip2));
        assertFalse(trips.contains(trip3));
    }

    @Test
    @DisplayName("Test when save trip then find it by departure day")
    void whenSaveTrip_thenFindItByDepartureDay() {
        Trip trip = new Trip();
        trip.setDepartureDay("2023-04-09");
        trip.setOrigin("London");
        trip.setDestination("New York");
        tripRepository.save(trip);

        Trip trip1 = new Trip();
        trip1.setDepartureDay("2023-04-09");
        trip1.setOrigin("London");
        trip1.setDestination("New York");
        tripRepository.save(trip1);

        Trip trip2 = new Trip();
        trip2.setDepartureDay("2024-04-03");
        trip2.setOrigin("London");
        trip2.setDestination("New York");
        tripRepository.save(trip2);

        List<Trip> trips = tripRepository.findByDepartureDay("2023-04-09");

        assert(trips.contains(trip));
        assert(trips.contains(trip1));
        assertFalse(trips.contains(trip2));
    }

    @Test
    @DisplayName("Test when save trip then find it by origin")
    void whenSaveTrip_thenFindItByOrigin() {
        Trip trip = new Trip();
        trip.setDepartureDay("2023-04-09");
        trip.setOrigin("London");
        trip.setDestination("New York");
        tripRepository.save(trip);

        Trip trip1 = new Trip();
        trip1.setOrigin("London");
        trip1.setDestination("New York");
        trip1.setDepartureDay("2023-04-09");
        tripRepository.save(trip1);

        Trip trip2 = new Trip();
        trip2.setOrigin("New York");
        trip2.setDestination("London");
        trip2.setDepartureDay("2023-04-09");
        tripRepository.save(trip2);

        List<Trip> trips = tripRepository.findByOrigin("London");

        assert(trips.contains(trip));
        assert(trips.contains(trip1));
        assertFalse(trips.contains(trip2));
    }

    @Test
    @DisplayName("Test when save trip then find it by destination")
    void whenSaveTrip_thenFindItByDestination() {
        Trip trip = new Trip();
        trip.setDepartureDay("2023-04-09");
        trip.setOrigin("London");
        trip.setDestination("New York");
        tripRepository.save(trip);

        Trip trip1 = new Trip();
        trip1.setOrigin("London");
        trip1.setDestination("New York");
        trip1.setDepartureDay("2023-04-08");
        tripRepository.save(trip1);

        Trip trip2 = new Trip();
        trip2.setOrigin("New York");
        trip2.setDestination("London");
        trip2.setDepartureDay("2023-04-07");
        tripRepository.save(trip2);

        List<Trip> trips = tripRepository.findByDestination("New York");
        
        assert(trips.contains(trip));
        assert(trips.contains(trip1));
        assertFalse(trips.contains(trip2));
    }

    @Test
    @DisplayName("Test when save trip then find all")
    void whenSaveTrip_thenFindAll() {
        Trip trip = new Trip();
        trip.setDepartureDay("2023-04-09");
        trip.setOrigin("London");
        trip.setDestination("New York");
        tripRepository.save(trip);

        Trip trip1 = new Trip();
        trip1.setOrigin("London");
        trip1.setDestination("New York");
        trip1.setDepartureDay("2023-04-09");
        tripRepository.save(trip1);

        Trip trip2 = new Trip();
        trip2.setOrigin("New York");
        trip2.setDestination("London");
        trip2.setDepartureDay("2023-04-09");
        tripRepository.save(trip2);

        Trip trip3 = new Trip();
        trip3.setOrigin("New York");
        trip3.setDestination("London");
        trip3.setDepartureDay("2024-04-03");
        tripRepository.save(trip3);

        List<Trip> trips = tripRepository.findAll();

        assert(trips.contains(trip));
        assert(trips.contains(trip1));
        assert(trips.contains(trip2));
        assert(trips.contains(trip3));
    }

    @Test
    @DisplayName("Test when save trip then delete by id")
    void whenSaveTrip_thenDeleteById() {
        Trip trip = new Trip();
        trip.setDepartureDay("2023-04-09");
        trip.setOrigin("London");
        trip.setDestination("New York");
        tripRepository.save(trip);

        tripRepository.deleteById(trip.getId());

        Trip foundTrip = tripRepository.findById(trip.getId());

        assertEquals(null , foundTrip);
    }

    @Test
    @DisplayName("Test when save trip then delete by origin")
    void whenSaveTrip_thenDeleteByOrigin() {
        Trip trip = new Trip();
        trip.setDepartureDay("2023-04-09");
        trip.setOrigin("London");
        trip.setDestination("New York");
        tripRepository.save(trip);

        Trip trip1 = new Trip();
        trip1.setOrigin("London");
        trip1.setDestination("Las Vegas");
        trip1.setDepartureDay("2023-04-09");
        tripRepository.save(trip1);

        tripRepository.deleteByOrigin("London");

        List<Trip> trips = tripRepository.findByOrigin("London");

        assert(trips.isEmpty());
    }

    @Test
    @DisplayName("Test when save trip then delete by destination")
    void whenSaveTrip_thenDeleteByDestination() {
        Trip trip = new Trip();
        trip.setDepartureDay("2023-04-09");
        trip.setOrigin("London");
        trip.setDestination("New York");
        tripRepository.save(trip);

        Trip trip1 = new Trip();
        trip1.setOrigin("Las Vegas");
        trip1.setDestination("New York");
        trip1.setDepartureDay("2023-04-09");
        tripRepository.save(trip1);

        tripRepository.deleteByDestination("New York");

        List<Trip> trips = tripRepository.findByDestination("New York");

        assert(trips.isEmpty());
    }

    @Test
    @DisplayName("Test when save trip then delete all")
    void whenSaveTrip_thenDeleteAll() {
        Trip trip = new Trip();
        trip.setDepartureDay("2023-04-09");
        trip.setOrigin("London");
        trip.setDestination("New York");
        tripRepository.save(trip);

        Trip trip1 = new Trip();
        trip1.setOrigin("London");
        trip1.setDestination("New York");
        trip1.setDepartureDay("2023-04-09");
        tripRepository.save(trip1);

        Trip trip2 = new Trip();
        trip2.setOrigin("New York");
        trip2.setDestination("London");
        trip2.setDepartureDay("2023-04-09");
        tripRepository.save(trip2);

        tripRepository.deleteAll();

        List<Trip> trips = tripRepository.findAll();

        assert(trips.isEmpty());
    }
    



    
}
