package ie.atu.week5.service;

import ie.atu.week5.model.passenger;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class passengerService {
    private final List<passenger> store = new ArrayList<>();

    public List<passenger> findAll() {
        return  new ArrayList<>(store);
    }

    public Optional<passenger> findById(String id){
        for (passenger p : store){
            if (p.getPassengerId().equals(id)){
                return  Optional.of(p);

            }
        }
        return Optional.empty();
    }

    public passenger create(passenger p){
        if (findById(p.getPassengerId()).isPresent()){
            throw new IllegalArgumentException("Passenger ID already exists");
        }
        store.add(p);
        return p;
    }

    public Optional<passenger> update(String id, passenger update) {
        return findById(id).map(existing -> {
            existing.setName(update.getName());
            existing.setEmail(update.getEmail());
            return existing;
        });
    }

}
