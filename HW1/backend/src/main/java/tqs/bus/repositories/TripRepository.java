package tqs.bus.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import tqs.bus.models.Trip;

public interface TripRepository extends JpaRepository<Trip, Integer>{
    public Trip findById(int id);

    @Query("SELECT t FROM Trip t WHERE (:origin IS NULL OR t.origin = :origin) AND (:destination IS NULL OR t.destination = :destination) AND (:date IS NULL OR t.departureDay = :date)")
    List<Trip> findByOriginAndDestinationAndDate(@Param("origin") String origin, @Param("destination") String destination, @Param("date") String date);

    @Query("SELECT DISTINCT t.departureDay FROM Trip t")
    List<String> findDepartureTimes();

    @Query("SELECT DISTINCT t.origin FROM Trip t")
    List<String> findOrigins();

    @Query("SELECT DISTINCT t.destination FROM Trip t")
    List<String> findDestinations();

    public void deleteById(int id);

    public List<Trip> findByOrigin(String origin);

    public List<Trip> findByDestination(String destination);

    public List<Trip> findByDepartureDay(String day);

    public void deleteByOrigin(String origin);

    public void deleteByDestination(String destination);    
}
