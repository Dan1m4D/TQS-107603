package tqs.bus.controllerTests;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import tqs.bus.controllers.TicketController;
import tqs.bus.models.Ticket;
import tqs.bus.services.TicketService;
import tqs.bus.services.BusService;
import tqs.bus.services.TripService;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.hasSize;


import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TicketController.class)
class TicketControllerTest {

    @Autowired
    MockMvc mvc;

    @MockBean
    private TicketService ticketService;

    @MockBean
    private BusService busService;

    @MockBean
    private TripService tripService;

    @Test
    @DisplayName("Test when list tickets then return all tickets")
    void whenListTickets_thenReturnAllTickets() throws Exception {
        Ticket ticket1 = new Ticket();
        ticket1.setTripID(1);
        ticket1.setSeatNumber(1);
        ticket1.setName("John Doe");
        ticket1.setEmail("john.doe@example.com");
        ticket1.setPrice("100.0 EUR");

        Ticket ticket2 = new Ticket();
        ticket2.setTripID(2);
        ticket2.setSeatNumber(2);
        ticket2.setName("Jane Doe");
        ticket2.setEmail("jane.doe@example.com");
        ticket2.setPrice("150.0 EUR");

        when(ticketService.getAllTickets()).thenReturn(List.of(ticket1, ticket2));

        mvc.perform(get("/tickets/list").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name", is(ticket1.getName())))
                .andExpect(jsonPath("$[1].name", is(ticket2.getName())));

        verify(ticketService, times(1)).getAllTickets();
    }
}
