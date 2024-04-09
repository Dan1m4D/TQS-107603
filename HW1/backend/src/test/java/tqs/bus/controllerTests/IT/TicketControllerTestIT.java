package tqs.bus.controllerTests.IT;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import tqs.bus.repositories.TicketRepository;
import tqs.bus.models.Bus;
import tqs.bus.models.Seat;
import tqs.bus.models.Ticket;
import tqs.bus.models.Trip;
import tqs.bus.repositories.BusRepository;
import tqs.bus.repositories.TripRepository;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import org.springframework.http.HttpStatus;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;

@TestInstance(Lifecycle.PER_CLASS)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = "spring.profiles.active=test")
class TicketControllerIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private BusRepository busRepository;

    @Autowired
    private TripRepository tripRepository;

    private Ticket ticket = new Ticket();

    private Trip trip = new Trip();
    private Trip trip2 = new Trip();

    @BeforeAll
    void setup() {
        Bus bus1 = new Bus();
        bus1.setName("bus 1");
        bus1.setSeats(20);
        bus1.setId(1);

        busRepository.saveAndFlush(bus1);

        trip.setBusID(1);
        trip.setDepartureDay("2024-04-09");
        trip.setDestination("Destination 1");
        trip.setOrigin("Origin 1");
        trip.setPrice(20);

        List<Seat> seats1 = new ArrayList<>(bus1.getSeats());

        for (int i = 0; i < bus1.getSeats(); i++) {
            seats1.add(new Seat());
        }

        for (int i = 0; i <= 4; i++) {
            seats1.get(i).setSeatType("premium");
        }

        trip.setSeats(seats1);

        trip.setDepartureHour("10:00");

        trip = tripRepository.saveAndFlush(trip);

        ticket.setEmail("john.doe@gmail.com");
        ticket.setName("John Doe");
        ticket.setPrice("20€");
        ticket.setSeatNumber(3);
        ticket.setTripID(trip.getId());
        ticketRepository.saveAndFlush(ticket);

        Bus bus2 = new Bus();
        bus2.setName("bus 2");
        bus2.setSeats(20);
        bus2.setId(2);

        busRepository.saveAndFlush(bus2);

        trip2.setBusID(2);
        trip2.setDepartureDay("2024-04-10");
        trip2.setDestination("Destination 2");
        trip2.setOrigin("Origin 2");
        trip2.setPrice(20);

        List<Seat> seats2 = new ArrayList<>(bus2.getSeats());

        for (int i = 0; i < bus2.getSeats(); i++) {
            seats2.add(new Seat());
        }

        trip2.setSeats(seats2);

        trip2.setDepartureHour("10:00");

        trip2 = tripRepository.saveAndFlush(trip2);
    }

    @Test
    @DisplayName("Test when there are tickets then get tickets")
    void whenHaveTickets_thenGetTickets() {
        ResponseEntity<Ticket[]> response = restTemplate.getForEntity("/tickets/list", Ticket[].class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).hasSize(1);
        assertThat(response.getBody()[0].getEmail()).isEqualTo("john.doe@gmail.com");
    }

    @Test
    @DisplayName("Test when creating a ticket then return 200")
    void whenPostTicket_thenReturn200() {
        ResponseEntity<Ticket> response = restTemplate.postForEntity("/tickets/buy", ticket, Ticket.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getEmail()).isEqualTo("john.doe@gmail.com");
    }

    @Test
    @DisplayName("Test when creating two tickets for the same trip in the same seat then give error")
    void whenPost2TicketsSameSeatForSameTrip_thenGiveError() {
        Ticket ticket2 = new Ticket();
        ticket2.setEmail("jane.doe@gmail.com");
        ticket2.setPrice("15 USD");
        ticket2.setSeatNumber(3);
        ticket2.setTripID(trip.getId());

        ResponseEntity<Ticket> response = restTemplate.postForEntity("/tickets/buy", ticket2, Ticket.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    @DisplayName("When buying a ticket with an invalid email, then give error")
    void whenPostInvalidEmail_thenGiveError() {
        Ticket ticket3 = new Ticket();
        ticket3.setEmail("jane.doe");
        ticket3.setPrice("15 USD");
        ticket3.setSeatNumber(5);
        ticket3.setTripID(trip.getId());

        ResponseEntity<Ticket> response = restTemplate.postForEntity("/tickets/buy", ticket3, Ticket.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }


    @Test
    @DisplayName("Test when buying a ticket for a trip that does not exist then give error")
    void whenPostTicketForInvalidTrip_thenGiveError() {
        Ticket ticket5 = new Ticket();
        ticket5.setEmail("john.doe@example.com");
        ticket5.setPrice("15 USD");
        ticket5.setSeatNumber(4);
        ticket5.setTripID(32);

        ResponseEntity<Ticket> response = restTemplate.postForEntity("/tickets/buy", ticket5, Ticket.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test 
    @DisplayName("Test when buying a ticket for a seat that does not exist then give error")
    void whenPostTicketForInvalidSeat_thenGiveError() {
        Ticket ticket5 = new Ticket();
        ticket5.setEmail("john.doe@example.com");
        ticket5.setPrice("15USD");
        ticket5.setSeatNumber(23874);
        ticket5.setTripID(trip.getId());

        ResponseEntity<Ticket> response = restTemplate.postForEntity("/tickets/buy", ticket5, Ticket.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }
}
