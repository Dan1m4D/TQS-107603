package tqs.bus.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import java.util.ArrayList;

import tqs.bus.models.Bus;
import tqs.bus.models.Seat;
import tqs.bus.models.Trip;
import tqs.bus.repositories.BusRepository;
import tqs.bus.repositories.TripRepository;

@Component
@ConditionalOnProperty(name = "spring.profiles.active", havingValue = "dev")
public class DefaultValues implements ApplicationRunner {

    @Autowired
    private BusRepository busRepository;

    @Autowired
    private TripRepository tripRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {

        String premium = "premium";

        String mirandela = "Mirandela";
        String aveiro = "Aveiro";
        String porto = "Porto";
        String vilaReal = "Vila Real";
        String lisboa = "Lisboa";
        String faro = "Faro";
        String peanfiel = "Penanfiel";

        Bus bus1 = new Bus();
        bus1.setSeats(50);
        bus1.setName("Flex Buss");

        int bus1ID = busRepository.save(bus1).getId();

        Bus bus2 = new Bus();
        bus2.setSeats(40);
        bus2.setName("Rede Espertos");

        int bus2ID = busRepository.save(bus2).getId();

        Bus bus3 = new Bus();
        bus3.setSeats(30);
        bus3.setName("Sanctis");
        int bus3ID = busRepository.save(bus3).getId();

        Bus bus4 = new Bus();
        bus4.setSeats(20);
        bus4.setName("RodaNorte");
        int bus4ID = busRepository.save(bus4).getId();

        Trip trip1 = new Trip();
        trip1.setOrigin(aveiro);
        trip1.setDestination(porto);
        trip1.setBusID(bus1ID);
        trip1.setPrice(10.0);
        trip1.setDepartureDay("2024-04-01");
        trip1.setDepartureHour("10:00");

        ArrayList<Seat> seatsBus1 = new ArrayList<>(bus1.getSeats());

        for (int i = 0; i < bus1.getSeats(); i++) {
            seatsBus1.add(new Seat()); // Create and add a new Seat object
        }

        // make seats 0 to 5 premium

        for (int i = 0; i <= 4; i++) {
            seatsBus1.get(i).setSeatType(premium);
        }

        trip1.setSeats(seatsBus1);

        // Create additional trips with the available buses and cities
        Trip trip2 = new Trip();
        trip2.setOrigin(aveiro);
        trip2.setDestination(mirandela);
        trip2.setBusID(bus2ID);
        trip2.setPrice(15.0);
        trip2.setDepartureDay("2024-04-01");
        trip2.setDepartureHour("12:00");

        ArrayList<Seat> seatsBus2 = new ArrayList<>(bus2.getSeats());
        for (int i = 0; i < bus2.getSeats(); i++) {
            seatsBus2.add(new Seat());
        }

        // Make seats premium
        for (int i = 0; i <= 4; i++) {
            seatsBus2.get(i).setSeatType(premium);
        }

        trip2.setSeats(seatsBus2);

        // Continue creating more trips as needed
        // Example:

        Trip trip3 = new Trip();
        trip3.setOrigin(porto);
        trip3.setDestination(vilaReal);
        trip3.setBusID(bus3ID);
        trip3.setPrice(20.0);
        trip3.setDepartureDay("2024-04-02");
        trip3.setDepartureHour("08:00");

        ArrayList<Seat> seatsBus3 = new ArrayList<>(bus3.getSeats());
        for (int i = 0; i < bus3.getSeats(); i++) {
            seatsBus3.add(new Seat());
        }

        // Make seats premium
        for (int i = 0; i <= 4; i++) {
            seatsBus3.get(i).setSeatType(premium);
        }

        trip3.setSeats(seatsBus3);

        // Save trips to the repository
        tripRepository.save(trip2);
        tripRepository.save(trip3);

        // Create more trips with available buses and cities
        Trip trip4 = new Trip();
        trip4.setOrigin(porto);
        trip4.setDestination(lisboa);
        trip4.setBusID(bus4ID);
        trip4.setPrice(25.0);
        trip4.setDepartureDay("2024-04-03");
        trip4.setDepartureHour("09:00");

        ArrayList<Seat> seatsBus4 = new ArrayList<>(bus4.getSeats());
        for (int i = 0; i < bus4.getSeats(); i++) {
            seatsBus4.add(new Seat());
        }

        // Make seats premium
        for (int i = 0; i <= 4; i++) {
            seatsBus4.get(i).setSeatType(premium);
        }

        trip4.setSeats(seatsBus4);

        // Save trip to the repository
        tripRepository.save(trip4);

        // Create more trips as needed

        Trip trip5 = new Trip();
        trip5.setOrigin(mirandela);
        trip5.setDestination(vilaReal);
        trip5.setBusID(bus1ID);
        trip5.setPrice(18.0);
        trip5.setDepartureDay("2024-04-04");
        trip5.setDepartureHour("10:30");

        ArrayList<Seat> seatsBus5 = new ArrayList<>(bus1.getSeats());
        for (int i = 0; i < bus1.getSeats(); i++) {
            seatsBus5.add(new Seat());
        }

        // Make seats premium
        for (int i = 0; i <= 4; i++) {
            seatsBus5.get(i).setSeatType(premium);
        }

        trip5.setSeats(seatsBus5);

        // Save trip to the repository
        tripRepository.save(trip5);

        // Create more trips as needed

        Trip trip6 = new Trip();
        trip6.setOrigin(aveiro);
        trip6.setDestination(faro);
        trip6.setBusID(bus2ID);
        trip6.setPrice(30.0);
        trip6.setDepartureDay("2024-04-05");
        trip6.setDepartureHour("11:00");

        ArrayList<Seat> seatsBus6 = new ArrayList<>(bus2.getSeats());
        for (int i = 0; i < bus2.getSeats(); i++) {
            seatsBus6.add(new Seat());
        }

        // Make seats premium
        for (int i = 0; i <= 4; i++) {
            seatsBus6.get(i).setSeatType(premium);
        }

        trip6.setSeats(seatsBus6);

        // Save trip to the repository
        tripRepository.save(trip6);

        // Create more trips with available buses and cities
        Trip trip7 = new Trip();
        trip7.setOrigin(porto);
        trip7.setDestination(aveiro);
        trip7.setBusID(bus3ID);
        trip7.setPrice(12.0);
        trip7.setDepartureDay("2024-04-06");
        trip7.setDepartureHour("12:00");

        ArrayList<Seat> seatsBus7 = new ArrayList<>(bus3.getSeats());
        for (int i = 0; i < bus3.getSeats(); i++) {
            seatsBus7.add(new Seat());
        }

        // Make seats premium
        for (int i = 0; i <= 4; i++) {
            seatsBus7.get(i).setSeatType(premium);
        }

        trip7.setSeats(seatsBus7);

        // Save trip to the repository
        tripRepository.save(trip7);

        // Create more trips as needed

        Trip trip8 = new Trip();
        trip8.setOrigin(vilaReal);
        trip8.setDestination(porto);
        trip8.setBusID(bus4ID);
        trip8.setPrice(22.0);
        trip8.setDepartureDay("2024-04-07");
        trip8.setDepartureHour("13:00");

        ArrayList<Seat> seatsBus8 = new ArrayList<>(bus4.getSeats());
        for (int i = 0; i < bus4.getSeats(); i++) {
            seatsBus8.add(new Seat());
        }

        // Make seats premium
        for (int i = 0; i <= 4; i++) {
            seatsBus8.get(i).setSeatType(premium);
        }

        trip8.setSeats(seatsBus8);

        // Save trip to the repository
        tripRepository.save(trip8);

        // Create more trips as needed

        Trip trip9 = new Trip();
        trip9.setOrigin(faro);
        trip9.setDestination(mirandela);
        trip9.setBusID(bus1ID);
        trip9.setPrice(28.0);
        trip9.setDepartureDay("2024-04-08");
        trip9.setDepartureHour("14:00");

        ArrayList<Seat> seatsBus9 = new ArrayList<>(bus1.getSeats());
        for (int i = 0; i < bus1.getSeats(); i++) {
            seatsBus9.add(new Seat());
        }

        // Make seats premium
        for (int i = 0; i <= 4; i++) {
            seatsBus9.get(i).setSeatType(premium);
        }

        trip9.setSeats(seatsBus9);

        // Save trip to the repository
        tripRepository.save(trip9);

        // Create more trips with each city as the origin and destination at least once
        // Aveiro to Mirandela
        Trip trip10 = new Trip();
        trip10.setOrigin(aveiro);
        trip10.setDestination(mirandela);
        trip10.setBusID(bus2ID);
        trip10.setPrice(15.0);
        trip10.setDepartureDay("2024-04-09");
        trip10.setDepartureHour("15:00");

        ArrayList<Seat> seatsBus10 = new ArrayList<>(bus2.getSeats());
        for (int i = 0; i < bus2.getSeats(); i++) {
            seatsBus10.add(new Seat());
        }

        // Make seats premium
        for (int i = 0; i <= 4; i++) {
            seatsBus10.get(i).setSeatType(premium);
        }

        trip10.setSeats(seatsBus10);

        // Save trip to the repository
        tripRepository.save(trip10);

        // Create more trips as needed

        // Mirandela to Aveiro
        Trip trip11 = new Trip();
        trip11.setOrigin(mirandela);
        trip11.setDestination(aveiro);
        trip11.setBusID(bus3ID);
        trip11.setPrice(20.0);
        trip11.setDepartureDay("2024-04-10");
        trip11.setDepartureHour("16:00");

        ArrayList<Seat> seatsBus11 = new ArrayList<>(bus3.getSeats());
        for (int i = 0; i < bus3.getSeats(); i++) {
            seatsBus11.add(new Seat());
        }

        // Make seats premium
        for (int i = 0; i <= 4; i++) {
            seatsBus11.get(i).setSeatType(premium);
        }

        trip11.setSeats(seatsBus11);

        // Save trip to the repository
        tripRepository.save(trip11);

        // Create more trips as needed

        // Porto to Vila Real
        Trip trip12 = new Trip();
        trip12.setOrigin(porto);
        trip12.setDestination(vilaReal);
        trip12.setBusID(bus4ID);
        trip12.setPrice(25.0);
        trip12.setDepartureDay("2024-04-11");
        trip12.setDepartureHour("17:00");

        ArrayList<Seat> seatsBus12 = new ArrayList<>(bus4.getSeats());
        for (int i = 0; i < bus4.getSeats(); i++) {
            seatsBus12.add(new Seat());
        }

        // Make seats premium
        for (int i = 0; i <= 4; i++) {
            seatsBus12.get(i).setSeatType(premium);
        }

        trip12.setSeats(seatsBus12);

        // Save trip to the repository
        tripRepository.save(trip12);

        // Create more trips as needed

        // Vila Real to Porto
        Trip trip13 = new Trip();
        trip13.setOrigin(vilaReal);
        trip13.setDestination(porto);
        trip13.setBusID(bus1ID);
        trip13.setPrice(30.0);
        trip13.setDepartureDay("2024-04-12");
        trip13.setDepartureHour("18:00");

        ArrayList<Seat> seatsBus13 = new ArrayList<>(bus1.getSeats());
        for (int i = 0; i < bus1.getSeats(); i++) {
            seatsBus13.add(new Seat());
        }

        // Make seats premium
        for (int i = 0; i <= 4; i++) {
            seatsBus13.get(i).setSeatType(premium);
        }

        trip13.setSeats(seatsBus13);

        // Save trip to the repository
        tripRepository.save(trip13);

        // Create more trips to cover all cities as both origin and destination

        // Vila Real to Lisboa
        Trip trip14 = new Trip();
        trip14.setOrigin(vilaReal);
        trip14.setDestination(lisboa);
        trip14.setBusID(bus2ID);
        trip14.setPrice(35.0);
        trip14.setDepartureDay("2024-04-13");
        trip14.setDepartureHour("19:00");

        ArrayList<Seat> seatsBus14 = new ArrayList<>(bus2.getSeats());
        for (int i = 0; i < bus2.getSeats(); i++) {
            seatsBus14.add(new Seat());
        }

        // Make seats premium
        for (int i = 0; i <= 4; i++) {
            seatsBus14.get(i).setSeatType(premium);
        }

        trip14.setSeats(seatsBus14);

        // Save trip to the repository
        tripRepository.save(trip14);

        // Create more trips as needed

        // Lisboa to Vila Real
        Trip trip15 = new Trip();
        trip15.setOrigin(lisboa);
        trip15.setDestination(vilaReal);
        trip15.setBusID(bus3ID);
        trip15.setPrice(40.0);
        trip15.setDepartureDay("2024-04-14");
        trip15.setDepartureHour("20:00");

        ArrayList<Seat> seatsBus15 = new ArrayList<>(bus3.getSeats());
        for (int i = 0; i < bus3.getSeats(); i++) {
            seatsBus15.add(new Seat());
        }

        // Make seats premium
        for (int i = 0; i <= 4; i++) {
            seatsBus15.get(i).setSeatType(premium);
        }

        trip15.setSeats(seatsBus15);

        // Save trip to the repository
        tripRepository.save(trip15);

        // Create more trips as needed

        // Lisboa to Porto
        Trip trip16 = new Trip();
        trip16.setOrigin(lisboa);
        trip16.setDestination(porto);
        trip16.setBusID(bus4ID);
        trip16.setPrice(45.0);
        trip16.setDepartureDay("2024-04-15");
        trip16.setDepartureHour("21:00");

        ArrayList<Seat> seatsBus16 = new ArrayList<>(bus4.getSeats());
        for (int i = 0; i < bus4.getSeats(); i++) {
            seatsBus16.add(new Seat());
        }

        // Make seats premium
        for (int i = 0; i <= 4; i++) {
            seatsBus16.get(i).setSeatType(premium);
        }

        trip16.setSeats(seatsBus16);

        // Save trip to the repository
        tripRepository.save(trip16);

        // Create more trips as needed

        // Porto to Lisboa
        Trip trip17 = new Trip();
        trip17.setOrigin(porto);
        trip17.setDestination(lisboa);
        trip17.setBusID(bus1ID);
        trip17.setPrice(50.0);
        trip17.setDepartureDay("2024-04-16");
        trip17.setDepartureHour("22:00");

        ArrayList<Seat> seatsBus17 = new ArrayList<>(bus1.getSeats());
        for (int i = 0; i < bus1.getSeats(); i++) {
            seatsBus17.add(new Seat());
        }

        // Make seats premium
        for (int i = 0; i <= 4; i++) {
            seatsBus17.get(i).setSeatType(premium);
        }

        trip17.setSeats(seatsBus17);

        // Save trip to the repository
        tripRepository.save(trip17);

        // Create more trips to cover all cities as both origin and destination

        // Lisboa to Mirandela
        Trip trip18 = new Trip();
        trip18.setOrigin(lisboa);
        trip18.setDestination(mirandela);
        trip18.setBusID(bus2ID);
        trip18.setPrice(55.0);
        trip18.setDepartureDay("2024-04-17");
        trip18.setDepartureHour("23:00");

        ArrayList<Seat> seatsBus18 = new ArrayList<>(bus2.getSeats());
        for (int i = 0; i < bus2.getSeats(); i++) {
            seatsBus18.add(new Seat());
        }

        // Make seats premium
        for (int i = 0; i <= 4; i++) {
            seatsBus18.get(i).setSeatType(premium);
        }

        trip18.setSeats(seatsBus18);

        // Save trip to the repository
        tripRepository.save(trip18);

        // Create more trips as needed

        // Mirandela to Lisboa
        Trip trip19 = new Trip();
        trip19.setOrigin(mirandela);
        trip19.setDestination(lisboa);
        trip19.setBusID(bus3ID);
        trip19.setPrice(60.0);
        trip19.setDepartureDay("2024-04-18");
        trip19.setDepartureHour("09:00");

        ArrayList<Seat> seatsBus19 = new ArrayList<>(bus3.getSeats());
        for (int i = 0; i < bus3.getSeats(); i++) {
            seatsBus19.add(new Seat());
        }

        // Make seats premium
        for (int i = 0; i <= 4; i++) {
            seatsBus19.get(i).setSeatType(premium);
        }

        trip19.setSeats(seatsBus19);

        // Save trip to the repository
        tripRepository.save(trip19);

        // Create more trips as needed

        // Faro to Penanfiel
        Trip trip20 = new Trip();
        trip20.setOrigin(faro);
        trip20.setDestination(peanfiel);
        trip20.setBusID(bus4ID);
        trip20.setPrice(65.0);
        trip20.setDepartureDay("2024-04-19");
        trip20.setDepartureHour("10:00");

        ArrayList<Seat> seatsBus20 = new ArrayList<>(bus4.getSeats());
        for (int i = 0; i < bus4.getSeats(); i++) {
            seatsBus20.add(new Seat());
        }

        // Make seats premium
        for (int i = 0; i <= 4; i++) {
            seatsBus20.get(i).setSeatType(premium);
        }

        trip20.setSeats(seatsBus20);

        // Save trip to the repository
        tripRepository.save(trip20);

        // Create more trips as needed

        // Penanfiel to Faro
        Trip trip21 = new Trip();
        trip21.setOrigin(peanfiel);
        trip21.setDestination(faro);
        trip21.setBusID(bus1ID);
        trip21.setPrice(70.0);
        trip21.setDepartureDay("2024-04-20");
        trip21.setDepartureHour("11:00");

        ArrayList<Seat> seatsBus21 = new ArrayList<>(bus1.getSeats());
        for (int i = 0; i < bus1.getSeats(); i++) {
            seatsBus21.add(new Seat());
        }

        // Make seats premium
        for (int i = 0; i <= 4; i++) {
            seatsBus21.get(i).setSeatType(premium);
        }

        trip21.setSeats(seatsBus21);

        // Save trip to the repository
        tripRepository.save(trip21);

        // Create more trips as needed

    }
}
