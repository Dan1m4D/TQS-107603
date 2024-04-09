package tqs.bus.serviceTests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import tqs.bus.models.Bus;
import tqs.bus.repositories.BusRepository;
import tqs.bus.services.BusService;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class BusServiceTest {

    @Mock(lenient = true)
    private BusRepository busRepository;

    @InjectMocks
    private BusService busService;

    @Test
    @DisplayName("Test when find bus by id then return bus")
    void whenFindBusById_thenReturnBus() {
        Bus bus = new Bus();
        bus.setName("Bus 1");
        bus.setSeats(10);
        when(busRepository.findById(1)).thenReturn(bus);


        Bus bus1 = busService.getBus(1);
        assertEquals("Bus 1", bus1.getName());
        verify(busRepository, times(1)).findById(1);
    }

    @Test
    @DisplayName("Test when find bus by invalid id then return null")
    void whenFindBusByInvalidId_thenReturnNull() {
        when(busRepository.findById(-1)).thenReturn(null);

        Bus bus1 = busService.getBus(-1);
        assertNull(bus1);
        verify(busRepository, times(1)).findById(-1);
    }

    @Test
    @DisplayName("Test when find all buss then return buses")
    void whenFindAllBuss_thenReturnBuses() {
        Bus bus = new Bus();
        bus.setName("Bus 1");
        bus.setSeats(10);

        Bus bus1 = new Bus();
        bus1.setName("Bus 2");
        bus1.setSeats(20);

        Bus bus2 = new Bus();
        bus2.setName("Bus 3");
        bus2.setSeats(30);

        when(busRepository.findAll()).thenReturn(List.of(bus, bus1, bus2));


        List<Bus> buses = busService.findAll();
        assertEquals(3, buses.size());
        assert(buses.containsAll(List.of(bus, bus1, bus2)));
        verify(busRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Test when save bus then return bus")
    void whenSaveBus_thenReturnBus() {
        Bus bus = new Bus();
        bus.setName("Bus 1");
        bus.setSeats(10);

        when(busRepository.save(bus)).thenReturn(bus);

        Bus bus1 = busService.addBus(bus);
        assertEquals("Bus 1", bus1.getName());
        verify(busRepository, times(1)).save(bus);
    }


        
    

}
