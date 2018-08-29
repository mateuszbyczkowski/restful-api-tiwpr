package pl.tiwpr.restapi.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.tiwpr.restapi.model.CarLocation;
import pl.tiwpr.restapi.repository.CarLocationRepository;

import java.util.Optional;

@RestController
@Slf4j
@RequestMapping("location")
public class CarLocationController {
    private CarLocationRepository carLocationRepository;

    public CarLocationController(CarLocationRepository carLocationRepository) {
        this.carLocationRepository = carLocationRepository;
    }

    @GetMapping
    public ResponseEntity get(Pageable pageable) {
        Page<CarLocation> carLocations = carLocationRepository.findAll(pageable);

        if (carLocations.getTotalElements() > 0) {
            return new ResponseEntity<>(carLocations, HttpStatus.OK);
        } else {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping
    public ResponseEntity post(@RequestBody CarLocation carLocation) {
        Optional<CarLocation> existingCar = carLocationRepository.findById(carLocation.getId());
        if (existingCar.isPresent()) {
            return new ResponseEntity("Can't create new location, there is existing actual location of this car.", HttpStatus.CONFLICT);
        } else {
            CarLocation savedLocation = carLocationRepository.save(carLocation);
            return new ResponseEntity<>(savedLocation, HttpStatus.CREATED);
        }
    }

    @PutMapping("{id}")
    public ResponseEntity update(@RequestBody CarLocation carLocation, @PathVariable Long id) {
        Optional<CarLocation> locationOptional = carLocationRepository.findById(id);
        if (!locationOptional.isPresent()) {
            return new ResponseEntity("There is no location to update.", HttpStatus.NOT_FOUND);
        }
        carLocation.setId(id);
        carLocationRepository.save(carLocation);
        return new ResponseEntity("Location updated successfully", HttpStatus.NO_CONTENT);
    }


    @DeleteMapping
    public ResponseEntity delete(Long id) {
        if (carLocationRepository.findById(id).isPresent()) {
            carLocationRepository.deleteById(id);
            return new ResponseEntity("Location deleted", HttpStatus.OK);
        } else {
            return new ResponseEntity("There is no location to remove.", HttpStatus.NOT_FOUND);
        }
    }
}
