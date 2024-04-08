package tqs.bus.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tqs.bus.models.Bus;
import tqs.bus.repositories.BusRepository;

@Service
public class BusService {

    @Autowired
    private BusRepository busRepository;

    public Bus getBus(int id) {
        return busRepository.findById(id);
    }

    public List<Bus> findAll() {
        return busRepository.findAll();
    }

    public Bus addBus(Bus bus) {
        return busRepository.save(bus);
    }
    
}
