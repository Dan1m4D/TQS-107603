package tqs.bus.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.ArrayList;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;

import tqs.bus.services.TicketService;
import tqs.bus.services.BusService;
import tqs.bus.services.TripService;
import tqs.bus.models.Ticket;
import tqs.bus.models.Trip;
import tqs.bus.models.Seat;
import tqs.bus.models.Bus;

@RestController
@CrossOrigin("*")
@RequestMapping(path = "/tickets")
public class TicketController {

    private static final Logger logger = LoggerFactory.getLogger(TicketController.class);

    @Autowired
    private TicketService ticketService;

    @Autowired
    private BusService busService;

    @Autowired
    private TripService tripService;

    @PostMapping("/buy")
    public ResponseEntity<Ticket> buyTicket(@RequestBody Ticket ticket) {

        logger.info("Buying ticket: {}", ticket);
        // Check if trip exists
        if (!tripService.tripExists(ticket.getTripID())) {
            logger.error("Trip with ID {} not found", ticket.getTripID());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Trip not found");
        }

        // Get trip details
        Trip trip = tripService.getTrip(ticket.getTripID(), "EUR");

        // Validate trip
        if (trip == null) {
            logger.error("Trip with ID {} not found", ticket.getTripID());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Trip not found");
        }

        // Validate email
        if (!validateEmail(ticket.getEmail())) {
            logger.error("Invalid email");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid email");
        }

        // Validate seat number
        int givenSeatIndex = ticket.getSeatNumber() - trip.getSeats().get(0).getNumber();
        if (givenSeatIndex < 0 || givenSeatIndex >= trip.getSeats().size()) {
            logger.error("Invalid seat number");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid seat number");
        }

        // Set adjusted seat number
        ticket.setSeatNumber(givenSeatIndex + 1);

        // Validate seat availability
        Seat seat = trip.getSeats().get(givenSeatIndex);
        if (seat.isTaken()) {
            logger.error("Seat already occupied");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Seat already occupied");
        }

        // Validate bus capacity
        Bus tripBus = busService.getBus(trip.getBusID());
        if (ticket.getSeatNumber() > tripBus.getSeats() || ticket.getSeatNumber() < 0) {
            logger.error("Invalid seat number");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid seat number");
        }

        // Validate bus full
        int seatsTaken = (int) trip.getSeats().stream().filter(Seat::isTaken).count();
        if (seatsTaken >= tripBus.getSeats()) {
            logger.error("Bus full ");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Bus full");
        }

        // Buy ticket
        Ticket boughtTicket = ticketService.buyTicket(ticket);
        logger.info("Ticket bought successfully: {}", boughtTicket);
        return ResponseEntity.ok(boughtTicket);

    }

    @GetMapping("/list")
    public ResponseEntity<List<Ticket>> listTickets() {
        try {
            logger.info("Listing all tickets");
            List<Ticket> tickets = ticketService.getAllTickets();
            List<Ticket> formattedTickets = new ArrayList<>();
            for (Ticket t : tickets) {
                Ticket formattedTicket = new Ticket();
                formattedTicket.setId(t.getId());
                formattedTicket.setTripID(t.getTripID());
                formattedTicket.setSeatNumber(t.getSeatNumber());
                formattedTicket.setName(t.getName());
                formattedTicket.setEmail(t.getEmail());
                formattedTicket.setPrice(t.getPrice());
                formattedTickets.add(formattedTicket);
            }
            return ResponseEntity.ok(formattedTickets);
        } catch (Exception e) {
            logger.error("Error occurred while listing tickets: {}", e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error occurred while listing tickets");
        }
    }

    public static boolean validateEmail(String email) {
        return email.matches(".+@.+\\..+");
    }
}
