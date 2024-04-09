package tqs.bus.serviceTests;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import tqs.bus.models.Seat;
import tqs.bus.models.Ticket;
import tqs.bus.models.Trip;
import tqs.bus.repositories.TicketRepository;
import tqs.bus.repositories.TripRepository;
import tqs.bus.services.TicketService;

import java.util.List;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class TicketServiceTest {

    @Mock
    private TicketRepository ticketRepository;

    @Mock
    private TripRepository tripRepository;

    @InjectMocks
    private TicketService ticketService;

    @Test
    @DisplayName("Test when seat is available then return true")
    void whenSeatIsAvailable_thenReturnTrue() {
        when(ticketRepository.findBySeatNumberAndTripID(10, 1)).thenReturn(null);

        boolean isAvailable = ticketService.isSeatAvailable(1, 10);
        assertTrue(isAvailable);
        verify(ticketRepository, times(1)).findBySeatNumberAndTripID(10, 1);
    }

    @Test
    @DisplayName("Test when seat is not available then return false")
    void whenSeatIsNotAvailable_thenReturnFalse() {
        when(ticketRepository.findBySeatNumberAndTripID(11, 1)).thenReturn(new Ticket());

        boolean isAvailable = ticketService.isSeatAvailable(1, 11);
        assertFalse(isAvailable);
        verify(ticketRepository, times(1)).findBySeatNumberAndTripID(anyInt(), anyInt());
    }

    @Test
    @DisplayName("Test when find trip tickets then return tickets")
    void whenFindTripTickets_thenReturnTickets() {
        Ticket ticket1 = new Ticket();
        Ticket ticket2 = new Ticket();
        when(ticketRepository.findByTripID(1)).thenReturn(List.of(ticket1, ticket2));

        List<Ticket> tickets = ticketService.findTripTickets(1);
        assertEquals(2, tickets.size());
        verify(ticketRepository, times(1)).findByTripID(1);
    }

    @Test
    @DisplayName("Test when get all tickets then return all tickets")
    void whenGetAllTickets_thenReturnAllTickets() {
        Ticket ticket1 = new Ticket();
        Ticket ticket2 = new Ticket();
        when(ticketRepository.findAll()).thenReturn(List.of(ticket1, ticket2));

        List<Ticket> tickets = ticketService.getAllTickets();
        assertEquals(2, tickets.size());
        verify(ticketRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Test when buy ticket then return ticket")
    void whenBuyTicket_thenReturnTicket() {
        Ticket ticket = new Ticket();
        ticket.setTripID(1);
        ticket.setSeatNumber(1);

        Trip trip = new Trip();
        trip.setDepartureDay("2023-04-09");
        trip.setOrigin("London");
        trip.setDestination("New York");
        trip.setSeats(List.of(new Seat(), new Seat(), new Seat(), new Seat(), new Seat(), new Seat()));

        when(ticketRepository.save(ticket)).thenReturn(ticket);
        when(tripRepository.findById(1)).thenReturn(trip);
        when(tripRepository.save(trip)).thenReturn(trip);

        Ticket boughtTicket = ticketService.buyTicket(ticket);
        assertEquals(ticket, boughtTicket);
        verify(ticketRepository, times(2)).save(any(Ticket.class));
        verify(tripRepository, times(1)).findById(anyInt());
        verify(tripRepository, times(1)).save(any(Trip.class));
    }
}
