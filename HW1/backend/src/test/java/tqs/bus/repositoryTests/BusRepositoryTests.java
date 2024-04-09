package tqs.bus.repositoryTests;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import tqs.bus.models.Bus;
import tqs.bus.repositories.BusRepository;

@DataJpaTest
class BusRepositoryTests {

    @Autowired
    private BusRepository busRepository;

    @Test
    @DisplayName("Test save a bus then find it by id")
    void whenSaveABus_thenFindItById() throws Exception {
        Bus bus = new Bus();
        bus.setName("Bus 1");
        bus.setSeats(10);
        busRepository.save(bus);

        Bus bus1 = busRepository.findById(bus.getId());

        assert(bus1.getName().equals(bus.getName()));
        assert(bus1.getSeats() == bus.getSeats());
        assert(bus1.getId() == bus.getId());
        assert(bus.equals(bus1));
    }

    @Test
    @DisplayName("Test save a bus then find it by name")
    void whenSaveABus_thenFindItByName() throws Exception {
        Bus bus = new Bus();
        bus.setName("Bus 1");
        bus.setSeats(10);
        busRepository.save(bus);

        Bus bus1 = busRepository.findByName(bus.getName());

        assert(bus1.getName().equals(bus.getName()));
        assert(bus1.getSeats() == bus.getSeats());
        assert(bus1.getId() == bus.getId());
        assert(bus.equals(bus1));
    }

    @Test
    @DisplayName("Test save multiple buses then find all")
    void whenSaveMultipleBuses_thenFindAll() throws Exception {
        Bus bus = new Bus();
        bus.setName("Bus 1");
        bus.setSeats(10);
        busRepository.save(bus);

        Bus bus1 = new Bus();
        bus1.setName("Bus 2");
        bus1.setSeats(20);
        busRepository.save(bus1);

        Bus bus2 = new Bus();
        bus2.setName("Bus 3");
        bus2.setSeats(30);
        busRepository.save(bus2);

        assert(busRepository.findAll().size() == 3);
    }


    @Test
    @DisplayName("Test delete a bus by id")
    void whenDeleteABusById_thenFindNone() throws Exception {
        Bus bus = new Bus();
        bus.setName("Bus 1");
        bus.setSeats(10);
        busRepository.save(bus);

        busRepository.deleteById(bus.getId());

        assert(busRepository.findByName(bus.getName()) == null);
    }

    @Test
    @DisplayName("Test delete a bus by name")
    void whenDeleteABusByName_thenFindNone() throws Exception {
        Bus bus = new Bus();
        bus.setName("Bus 1");
        bus.setSeats(10);
        busRepository.save(bus);

        busRepository.deleteByName(bus.getName());

        assert(busRepository.findByName(bus.getName()) == null);
    }

    @Test
    @DisplayName("Test delete all buss")
    void whenDeleteAllBus_thenFindEmpty() throws Exception {
        Bus bus = new Bus();
        bus.setName("Bus 1");
        bus.setSeats(10);
        busRepository.save(bus);

        Bus bus1 = new Bus();
        bus1.setName("Bus 2");
        bus1.setSeats(20);
        busRepository.save(bus1);

        Bus bus2 = new Bus();
        bus2.setName("Bus 3");
        bus2.setSeats(30);
        busRepository.save(bus2);

        busRepository.deleteAll();


        busRepository.deleteAll();

        assert(busRepository.findAll().isEmpty());
    
    }

    
    
}
