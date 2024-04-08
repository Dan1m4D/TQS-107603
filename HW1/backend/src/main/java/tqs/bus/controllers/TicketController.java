package tqs.bus.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import org.springframework.web.bind.annotation.RequestBody;

import org.springframework.http.HttpStatus;

import tqs.bus.services.TicketService;
import tqs.bus.services.BusService;
import tqs.bus.services.TripService;
import tqs.bus.models.Ticket;
import tqs.bus.models.Trip;
import tqs.bus.models.Seat;
import tqs.bus.models.Bus;

import java.util.ArrayList;

@RestController
@CrossOrigin("*")
@RequestMapping(path = "/tickets")
public class TicketController {

    @Autowired
    private TicketService ticketService;

    @Autowired
    private BusService busService;

    @Autowired
    private TripService tripService;

    @PostMapping("/buy")
    public ResponseEntity<Ticket> buyTicket(@RequestBody Ticket ticket) {

        if (!tripService.tripExists(ticket.getTripID())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Trip not found");
        }

        Trip trip = tripService.getTrip(ticket.getTripID(), "EUR");

        if (trip == null) {

            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Trip not found");
        }

        if (validateEmail(ticket.getEmail())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid email");
        }

        int givenSeat_index = ticket.getSeatNumber() - trip.getSeats().get(0).getNumber();

        if (givenSeat_index < 0 || givenSeat_index >= trip.getSeats().size()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid seat number");
        }

        ticket.setSeatNumber(givenSeat_index + 1);

        Bus tripBus = busService.getBus(trip.getBusID());

        int lastTicketOfBus = trip.getSeats().get(0).getNumber() + trip.getSeats().size();
        int givenSeat = ticket.getSeatNumber();

        if (givenSeat > lastTicketOfBus
                || ticket.getSeatNumber() < 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid seat number");
        }

        List<Seat> seats = trip.getSeats();

        Seat seat = seats.get(givenSeat_index);

        if (seat.isTaken()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Seat already occupied");
        }

        // count number of seats taken

        int seatsTaken = 0;
        for (Seat s : seats) {
            if (s.isTaken()) {
                seatsTaken++;
            }
        }

        if (seatsTaken >= tripBus.getSeats()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Bus full");
        }

        Ticket t = ticketService.buyTicket(ticket);

        return ResponseEntity.ok(t);

    }

    @GetMapping("/list")
    public ResponseEntity<List<Ticket>> listTickets() {

        List<Ticket> tickets = ticketService.getAllTickets();

        List<Ticket> ticket = new ArrayList<Ticket>();

        for (Ticket t : tickets) {
            Ticket td = new Ticket();
            td.setId(t.getId());
            td.setTripID(t.getTripID());
            td.setSeatNumber(t.getSeatNumber());
            td.setName(t.getName());
            td.setEmail(t.getEmail());
            td.setPrice(t.getPrice());
            ticket.add(td);
        }

        return ResponseEntity.ok(ticket);
    }

    private boolean validateEmail(String email) {
        return email.matches("/.+@.+\\..+/");
    }

}
