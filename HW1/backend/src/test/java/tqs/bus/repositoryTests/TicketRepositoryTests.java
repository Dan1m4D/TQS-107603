package tqs.bus.repositoryTests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import tqs.bus.models.Ticket;
import tqs.bus.repositories.TicketRepository;

@DataJpaTest
class TicketRepositoryTests {

    @Autowired
    private TicketRepository ticketRepository;

    @Test
    @DisplayName("Test when saving ticket then find it by id")
    void whenSavingTicket_thenFindItById() {
        Ticket ticket = new Ticket();
        ticket.setSeatNumber(1);
        ticket.setPrice("8.00 USD");
        ticket.setEmail("daniel.madureira@ua.pt");
        ticket.setName("Daniel Madureira");
        ticket.setTripID(1);
        ticketRepository.save(ticket);

        Ticket ticket1 = ticketRepository.findById(ticket.getId());

        assertEquals(ticket, ticket1);
        assertEquals(ticket.getSeatNumber(), ticket1.getSeatNumber());
        assertEquals(ticket.getPrice(), ticket1.getPrice());
        assertEquals(ticket.getEmail(), ticket1.getEmail());
        assertEquals(ticket.getName(), ticket1.getName());
    }

    @Test
    @DisplayName("Test when saving ticket then find it by trip ID")
    void whenSavingTicket_thenFindItByTripID() {
        Ticket ticket = new Ticket();
        ticket.setSeatNumber(1);
        ticket.setPrice("8.00 USD");
        ticket.setEmail("daniel.madureira@ua.pt");
        ticket.setName("Daniel Madureira");
        ticket.setTripID(1);
        ticketRepository.save(ticket);

        Ticket ticket1 = new Ticket();
        ticket1.setSeatNumber(2);
        ticket1.setPrice("8.00 USD");
        ticket1.setEmail("daniel.madureira1@ua.pt");
        ticket1.setName("Daniel Madureira 1");
        ticket1.setTripID(2);
        ticketRepository.save(ticket1);

        Ticket ticket2 = new Ticket();
        ticket2.setSeatNumber(3);
        ticket2.setPrice("10.00 USD");
        ticket2.setEmail("daniel.madureira2@ua.pt");
        ticket2.setName("Daniel Madureira 2");
        ticket2.setTripID(2);
        ticketRepository.save(ticket2);

        List<Ticket> tickets = ticketRepository.findByTripID(1);

        assert (tickets.contains(ticket));
        assertFalse(tickets.contains(ticket1));
        assertFalse(tickets.contains(ticket2));

        List<Ticket> tickets1 = ticketRepository.findByTripID(2);
        assertFalse(tickets1.contains(ticket));
        assert (tickets1.contains(ticket1));
        assert (tickets1.contains(ticket2));
    }

    @Test
    @DisplayName("Test when saving ticket then find it by seat number and trip ID")
    void whenSavingTicket_thenFindItBySeatNumberAndTripID() {
        Ticket ticket = new Ticket();
        ticket.setSeatNumber(1);
        ticket.setPrice("8.00 USD");
        ticket.setEmail("daniel.madureira@ua.pt");
        ticket.setName("Daniel Madureira");
        ticket.setTripID(1);
        ticketRepository.save(ticket);

        Ticket ticket1 = new Ticket();
        ticket1.setSeatNumber(2);
        ticket1.setPrice("9.00 USD");
        ticket1.setEmail("daniel.madureira1@ua.pt");
        ticket1.setName("Daniel Madureira 1");
        ticket1.setTripID(2);
        ticketRepository.save(ticket1);

        Ticket foundTicket = ticketRepository.findBySeatNumberAndTripID(1, 1);
        Ticket foundTicket1 = ticketRepository.findBySeatNumberAndTripID(2, 2);

        assertEquals(ticket, foundTicket);
        assertEquals(ticket1, foundTicket1);

        assertNotEquals(ticket, foundTicket1);
        assertNotEquals(ticket1, foundTicket);
    }

    @Test
    @DisplayName("Test when saving multiple tickets then find all")
    void whenSavingMultipleTickets_thenFindAll() {
        Ticket ticket = new Ticket();
        ticket.setSeatNumber(1);
        ticket.setPrice("9.00 USD");
        ticket.setEmail("daniel.madureira@ua.pt");
        ticket.setName("Daniel Madureira");
        ticket.setTripID(1);
        ticketRepository.save(ticket);

        Ticket ticket1 = new Ticket();
        ticket1.setSeatNumber(2);
        ticket1.setPrice("8.00 USD");
        ticket1.setEmail("daniel.madureira1@ua.pt");
        ticket1.setName("Daniel Madureira 1");
        ticket1.setTripID(2);
        ticketRepository.save(ticket1);

        Ticket ticket2 = new Ticket();
        ticket2.setSeatNumber(3);
        ticket2.setPrice("10.00 USD");
        ticket2.setEmail("daniel.madureira2@ua.pt");
        ticket2.setName("Daniel Madureira 2");
        ticket2.setTripID(2);
        ticketRepository.save(ticket2);

        List<Ticket> tickets = ticketRepository.findAll();

        assertEquals(3, tickets.size());
        assert (tickets.contains(ticket));
        assert (tickets.contains(ticket1));
        assert (tickets.contains(ticket2));

    }

    @Test
    @DisplayName("Test when deleting ticket then not find it")
    void whenDeletingTicket_thenNotFindIt() {
        Ticket ticket = new Ticket();
        ticket.setSeatNumber(1);
        ticket.setPrice("9.00 USD");
        ticket.setEmail("daniel.madureira@ua.pt");
        ticket.setName("Daniel Madureira");
        ticket.setTripID(1);
        ticketRepository.save(ticket);

        ticketRepository.delete(ticket);

        Ticket ticket1 = ticketRepository.findBySeatNumberAndTripID(1, 1);

        assertEquals(null, ticket1);
    }

    @Test
    @DisplayName("Test when deleting ticket by trip ID then not find it")
    void whenDeletingTicketByTripID_thenNotFindIt() {
        Ticket ticket = new Ticket();
        ticket.setSeatNumber(1);
        ticket.setPrice("9.00 USD");
        ticket.setEmail("daniel.madureira@ua.pt");
        ticket.setName("Daniel Madureira");
        ticket.setTripID(1);
        ticketRepository.save(ticket);

        Ticket ticket1 = new Ticket();
        ticket1.setSeatNumber(2);
        ticket1.setPrice("8.00 USD");
        ticket1.setEmail("daniel.madureira1@ua.pt");
        ticket1.setName("Daniel Madureira 1");
        ticket1.setTripID(1);
        ticketRepository.save(ticket1);

        ticketRepository.deleteByTripID(1);
        List<Ticket> tickets = ticketRepository.findByTripID(1);

        assert(tickets.isEmpty());
    }

    @Test
    @DisplayName("Test when deleting all tickets then not find none")
    void whenDeletingAllTickets_thenNotFindNone() {
        Ticket ticket = new Ticket();
        ticket.setSeatNumber(1);
        ticket.setPrice("9.00 USD");
        ticket.setEmail("daniel.madureira@ua.pt");
        ticket.setName("Daniel Madureira");
        ticket.setTripID(1);
        ticketRepository.save(ticket);

        Ticket ticket1 = new Ticket();
        ticket1.setSeatNumber(2);
        ticket1.setPrice("8.00 USD");
        ticket1.setEmail("daniel.madureira1@ua.pt");
        ticket1.setName("Daniel Madureira 1");
        ticket1.setTripID(1);
        ticketRepository.save(ticket1);

        ticketRepository.deleteAll();

        List<Ticket> tickets = ticketRepository.findAll();

        assert (tickets.isEmpty());
    }

}
