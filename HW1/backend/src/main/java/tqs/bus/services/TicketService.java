package tqs.bus.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tqs.bus.models.Ticket;
import tqs.bus.models.Trip;
import tqs.bus.repositories.TicketRepository;
import tqs.bus.repositories.TripRepository;

@Service
public class TicketService {
    
    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private TripRepository tripRepository;

    public boolean isSeatAvailable(int tripId, int seatNumber) {
        return ticketRepository.findBySeatNumberAndTripID(seatNumber, tripId) == null;
    }

    public List<Ticket> findTripTickets(int tripId) {
        return ticketRepository.findByTripID(tripId);
    }

    public List<Ticket> getAllTickets() {
        return ticketRepository.findAll();
    }

    public Ticket buyTicket(Ticket ticket) {
        try {
            ticketRepository.save(ticket);
            Trip trip = tripRepository.findById(ticket.getTripID());
            trip.getSeats().get(ticket.getSeatNumber() - 1).setTaken(true);
            tripRepository.save(trip);

            ticketRepository.save(ticket);
            return ticket;

        } catch (Exception e) {
            return null;
        }
    }

}
