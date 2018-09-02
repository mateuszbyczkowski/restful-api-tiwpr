package pl.tiwpr.restapi.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.tiwpr.restapi.model.Car;
import pl.tiwpr.restapi.repository.CarRepository;

import java.util.Optional;

@RestController
@Slf4j
@RequestMapping("car")
public class CarController {
    private CarRepository carRepository;

    public CarController(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    @GetMapping
    public ResponseEntity get(Pageable pageable) {
        Page<Car> cars = carRepository.findAll(pageable);

        if (cars.getTotalElements() > 0) {
            return new ResponseEntity<>(cars, HttpStatus.OK);
        } else {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping
    public ResponseEntity post(@RequestBody Car car) {
        Optional<Car> existingCar = carRepository.findById(car.getId());
        if (existingCar.isPresent()) {
            return new ResponseEntity("Car already exists", HttpStatus.CONFLICT);
        } else {
            Car savedCar = carRepository.save(car);
            return new ResponseEntity<>(savedCar, HttpStatus.CREATED);
        }
    }

    @PutMapping("{id}")
    public ResponseEntity update(@RequestBody Car car, @PathVariable Long id) {
        Optional<Car> carOptional = carRepository.findById(id);
        if (!carOptional.isPresent()) {
            return new ResponseEntity("Car not found", HttpStatus.NOT_FOUND);
        }
        car.setId(id);
        carRepository.save(car);
        return new ResponseEntity("Resource updated", HttpStatus.NO_CONTENT);
    }


    @DeleteMapping
    public ResponseEntity delete(Long id) {
        if (carRepository.findById(id).isPresent()) {
            carRepository.deleteById(id);
            return new ResponseEntity("Car deleted", HttpStatus.OK);
        } else {
            return new ResponseEntity("Car does not exist", HttpStatus.NOT_FOUND);
        }
    }
}
