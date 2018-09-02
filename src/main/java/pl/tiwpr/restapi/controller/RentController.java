package pl.tiwpr.restapi.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.tiwpr.restapi.model.Car;
import pl.tiwpr.restapi.model.CarRention;
import pl.tiwpr.restapi.model.enums.CarState;
import pl.tiwpr.restapi.repository.CarRepository;
import pl.tiwpr.restapi.repository.RentedCarRepository;

import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("rent")
public class RentController {

    private RentedCarRepository rentedCarRepository;
    private CarRepository carRepository;

    public RentController(RentedCarRepository rentedCarRepository, CarRepository carRepository) {
        this.rentedCarRepository = rentedCarRepository;
        this.carRepository = carRepository;
    }

    @GetMapping
    public ResponseEntity get(Pageable pageable) {
        Page<CarRention> rentedCars = rentedCarRepository.findAll(pageable);

        if (rentedCars.getTotalElements() > 0) {
            return new ResponseEntity<>(rentedCars, HttpStatus.OK);
        } else {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping
    public ResponseEntity post(@RequestBody CarRention carRention) {
        Car car = this.carRepository
                .findById(carRention.getCar().getId())
                .orElseThrow(RuntimeException::new);
        if (car.getCarState() == CarState.RENTED) {
            return new ResponseEntity("Car already rented", HttpStatus.BAD_REQUEST);
        }
        car.setCarState(CarState.RENTED);
        this.carRepository.save(car);
        CarRention save = this.rentedCarRepository.save(carRention);
        return new ResponseEntity(save, HttpStatus.CREATED);
    }

    @PutMapping("{id}")
    public ResponseEntity update(@RequestBody CarRention carRention, @PathVariable Long id) {
        Optional<CarRention> carOptional = rentedCarRepository.findById(id);
        if (!carOptional.isPresent()) {
            return new ResponseEntity("Rent for this car not found", HttpStatus.NOT_FOUND);
        }
        carRention.setId(id);
        rentedCarRepository.save(carRention);
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