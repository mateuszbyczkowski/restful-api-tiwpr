package pl.tiwpr.restapi.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.tiwpr.restapi.model.RentedCar;
import pl.tiwpr.restapi.repository.RentedCarRepository;

import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("rent")
public class RentController {

    private RentedCarRepository rentedCarRepository;

    public RentController(RentedCarRepository rentedCarRepository) {
        this.rentedCarRepository = rentedCarRepository;
    }

    @GetMapping
    public ResponseEntity get(Pageable pageable) {
        Page<RentedCar> rentedCars = rentedCarRepository.findAll(pageable);

        if (rentedCars.getTotalElements() > 0) {
            return new ResponseEntity<>(rentedCars, HttpStatus.OK);
        } else {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping
    public ResponseEntity post(@RequestBody RentedCar rentedCar) {
        Optional<RentedCar> rentedCarOptional = rentedCarRepository.findById(rentedCar.getId());
        if (rentedCarOptional.isPresent()) {
            return new ResponseEntity("Rent of this car already exists", HttpStatus.CONFLICT);
        } else {
            RentedCar savedRentedCar = rentedCarRepository.save(rentedCar);
            return new ResponseEntity<>(savedRentedCar, HttpStatus.CREATED);
        }
    }

    @PutMapping("{id}")
    public ResponseEntity update(@RequestBody RentedCar rentedCar, @PathVariable Long id) {
        Optional<RentedCar> carOptional = rentedCarRepository.findById(id);
        if (!carOptional.isPresent()) {
            return new ResponseEntity("Rent for this car not found", HttpStatus.NOT_FOUND);
        }
        rentedCar.setId(id);
        rentedCarRepository.save(rentedCar);
        return new ResponseEntity("Resource updated", HttpStatus.NO_CONTENT);
    }

    @DeleteMapping
    public ResponseEntity delete(Long id) {
        if (rentedCarRepository.findById(id).isPresent()) {
            rentedCarRepository.deleteById(id);
            return new ResponseEntity("Rention deleted", HttpStatus.OK);
        } else {
            return new ResponseEntity("Rent of this car does not exist", HttpStatus.NOT_FOUND);
        }
    }
}