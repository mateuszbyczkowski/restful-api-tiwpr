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
public class CarController {
    private CarRepository carRepository;

    public CarController(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    @GetMapping("/cars")
    public ResponseEntity get(Pageable pageable) {
        Page<Car> cars = carRepository.findAll(pageable);
        return cars.getTotalElements() > 0 ? new ResponseEntity<>(cars, HttpStatus.OK)
                : new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/cars/{id}")
    public ResponseEntity get(@PathVariable(value = "id") Long carId) {
        Car car = carRepository.findById(carId).orElse(null);
        return car == null ? new ResponseEntity(HttpStatus.BAD_REQUEST) : new ResponseEntity<>(car, HttpStatus.OK);
    }

    @GetMapping("/cars/{id}/specification")
    public ResponseEntity getCarSpec(@PathVariable(value = "id") Long carId) {
        Car car = carRepository.findById(carId).orElse(null);
        return car == null || car.getCarSpecification() == null
                ? new ResponseEntity(HttpStatus.BAD_REQUEST)
                : new ResponseEntity<>(car.getCarSpecification(), HttpStatus.OK);
    }

    @PostMapping("/cars")
    public ResponseEntity post(@RequestBody Car car) {
        Car existingCar = carRepository.findById(car.getId()).orElse(null);
        if (existingCar != null) {
            return new ResponseEntity("Car already exists", HttpStatus.CONFLICT);
        } else {

            int newEntityVersion = countNowEntityVersion(car);
            car.setEntityVersion(newEntityVersion);

            Car savedCar = carRepository.save(car);
            return new ResponseEntity<>(savedCar, HttpStatus.CREATED);
        }
    }

    private int countNowEntityVersion(Car car) {
        int newEntityVersion = car.getEntityVersion() + 1;
        Optional<Car> carByEntityVersion = carRepository.findCarByEntityVersion(newEntityVersion);
        if (carByEntityVersion.isPresent()) {
            newEntityVersion = carByEntityVersion.get().getEntityVersion();
            while (carRepository.findCarByEntityVersion(newEntityVersion + 1).isPresent()) {
                newEntityVersion++;
            }
            newEntityVersion++;
        }
        return newEntityVersion;
    }

    @PutMapping("/cars/{id}")
    public ResponseEntity update(@RequestBody Car car, @PathVariable(value = "id") Long id) {
        Car existingCar = carRepository.findById(id).orElse(null);
        if (existingCar == null) {
            return new ResponseEntity("Car not found", HttpStatus.NOT_FOUND);
        }

        int newEntityVersion = countNowEntityVersion(car);
        car.setEntityVersion(newEntityVersion);

        car.setId(id);
        carRepository.save(car);
        return new ResponseEntity("Resource updated", HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/cars/{id}")
    public ResponseEntity delete(@PathVariable(value = "id") Long id) {
        if (carRepository.findById(id).isPresent()) {
            carRepository.deleteById(id);
            return new ResponseEntity("Car " + id + " deleted", HttpStatus.OK);
        } else {
            return new ResponseEntity("Car does not exist", HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/cars/")
    public ResponseEntity delete() {
        carRepository.deleteAll();
        return new ResponseEntity("All cars deleted", HttpStatus.OK);
    }

    @DeleteMapping("/cars/{make}")
    public ResponseEntity delete(@PathVariable(value = "make") String make) {
        Iterable<Car> all = carRepository.findAll();
        all.forEach(car -> {
            if (car.getCarSpecification() != null) {
                if (car.getCarSpecification().getMake().equals(make)) {
                    carRepository.deleteById(car.getId());
                }
            }
        });
        return new ResponseEntity("All " + make + " cars deleted.", HttpStatus.OK);
    }
}
