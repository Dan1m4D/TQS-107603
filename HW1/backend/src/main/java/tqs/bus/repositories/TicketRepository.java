package tqs.bus.repositories;

import org.springframework.stereotype.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import tqs.bus.models.Ticket;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Integer>{

    public Ticket findById(String id);

    public List<Ticket> findByTripID(int tripID);

    public Ticket findBySeatNumberAndTripID(int seatNumber,int tripID);

    public void deleteByTripID(int tripID);
}
