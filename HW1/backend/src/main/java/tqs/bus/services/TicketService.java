package tqs.bus.services;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tqs.bus.models.Ticket;
import tqs.bus.models.Trip;
import tqs.bus.repositories.TicketRepository;
import tqs.bus.repositories.TripRepository;

@Service
public class TicketService {
    
    private static final Logger logger = LoggerFactory.getLogger(TicketService.class);

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private TripRepository tripRepository;

    public boolean isSeatAvailable(int tripId, int seatNumber) {
        try {
            return ticketRepository.findBySeatNumberAndTripID(seatNumber, tripId) == null;
        } catch (Exception e) {
            logger.error("Error occurred while checking seat availability: {}", e.getMessage());
            return false;
        }
    }

    public List<Ticket> findTripTickets(int tripId) {
        try {
            return ticketRepository.findByTripID(tripId);
        } catch (Exception e) {
            logger.error("Error occurred while finding tickets for trip ID {}: {}", tripId, e.getMessage());
            return List.of();
        }
    }

    public List<Ticket> getAllTickets() {
        try {
            return ticketRepository.findAll();
        } catch (Exception e) {
            logger.error("Error occurred while getting all tickets: {}", e.getMessage());
            return List.of();   
        }
    }

    public Ticket buyTicket(Ticket ticket) {
        try {
            logger.info("Buying ticket: {}", ticket);   
            ticketRepository.save(ticket);
            Trip trip = tripRepository.findById(ticket.getTripID());
            trip.getSeats().get(ticket.getSeatNumber() - 1).setTaken(true);
            tripRepository.save(trip);

            ticketRepository.save(ticket);
            logger.info("Ticket successfully bought: {}", ticket);
            return ticket;

        } catch (Exception e) {
            logger.error("Error occurred while buying ticket: {}", e.getMessage());
            return null;
        }
    }
}
