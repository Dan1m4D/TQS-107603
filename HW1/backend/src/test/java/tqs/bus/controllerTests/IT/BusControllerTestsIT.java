package tqs.bus.controllerTests.IT;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import tqs.bus.models.Bus;
import tqs.bus.repositories.BusRepository;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class BusControllerIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private BusRepository busRepository;

    @BeforeEach
    void setUp() {
        busRepository.deleteAll(); // Clean up the database before each test
    }

    @Test
    @DisplayName("Test when get a bus then return a bus")
    void whenGetBus_thenReturnBus() {
        Bus bus = new Bus();
        bus.setName("Bus 1");
        bus.setSeats(10);

        bus = busRepository.save(bus);

        ResponseEntity<Bus> response = restTemplate.getForEntity("/bus/get?id=" + bus.getId(), Bus.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getName()).isEqualTo("Bus 1");
        assertThat(response.getBody().getSeats()).isEqualTo(10);
    }

    @Test
    @DisplayName("Test when add a bus then return a bus")
    void whenAddBus_thenReturnBus() {
        Bus bus = new Bus();
        bus.setName("Bus 1");
        bus.setSeats(10);

        ResponseEntity<Bus> response = restTemplate.postForEntity("/bus/add", bus, Bus.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getName()).isEqualTo("Bus 1");
        assertThat(response.getBody().getSeats()).isEqualTo(10);
    }

    @Test
    @DisplayName("Test when get a bus by inexistent id then return not found")
    void whenGetBusByInexistentId_thenReturnNotFound() {
        ResponseEntity<Bus> response = restTemplate.getForEntity("/bus/get?id=1", Bus.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    @DisplayName("Test when get all buses then return all buses")
    void whenGetBuses_thenReturnAllBuses() {
        Bus bus1 = new Bus();
        bus1.setName("Bus 1");
        bus1.setSeats(10);

        Bus bus2 = new Bus();
        bus2.setName("Bus 2");
        bus2.setSeats(20);

        busRepository.save(bus1);
        busRepository.save(bus2);

        ResponseEntity<Bus[]> response = restTemplate.getForEntity("/bus/list", Bus[].class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull().hasSize(2);
        assertThat(response.getBody()[0].getName()).isEqualTo(bus1.getName());
        assertThat(response.getBody()[1].getName()).isEqualTo(bus2.getName());
    }
}
