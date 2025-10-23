package ie.atu.week5.controller;

import ie.atu.week5.model.passenger;
import ie.atu.week5.service.passengerService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/passengers")
public class passengerController {

    private final passengerService service;

    public passengerController(passengerService service){ this.service = service;}

    @GetMapping
    public ResponseEntity<List<passenger>> getAll(){ return ResponseEntity.ok(service.findAll());}

    @GetMapping("/{id}")
    public ResponseEntity<passenger> getOne(@PathVariable String id){
        Optional<passenger> maybe = service.findById(id);
        if (maybe.isPresent()){
            return  ResponseEntity.ok(maybe.get());
        } else {
            return ResponseEntity.notFound().build();
        }

    }

    @PostMapping
    public ResponseEntity<passenger> create(@Valid @RequestBody passenger p) {
        passenger created = service.create(p);
        return  ResponseEntity
                .created(URI.create("/api/passengers/" + created.getPassengerId()))
                .body(created);
    }
//Homework
    @PutMapping("/{id}")
    public ResponseEntity<passenger> update(@PathVariable String id, @Valid @RequestBody passenger p) {
        return service.update(id, p)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
//Homework
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        boolean removed = service.delete(id);
        return removed ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
