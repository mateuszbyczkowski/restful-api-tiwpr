package pl.tiwpr.restapi.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.tiwpr.restapi.model.CarSpecification;
import pl.tiwpr.restapi.repository.CarSpecificationRepository;

@RestController
@Slf4j
public class CarSpecificationController {
    private CarSpecificationRepository carSpecificationRepository;

    public CarSpecificationController(CarSpecificationRepository carSpecificationRepository) {
        this.carSpecificationRepository = carSpecificationRepository;
    }

    @GetMapping("/specifications")
    public ResponseEntity get(Pageable pageable) {
        Page<CarSpecification> carSpecifications = carSpecificationRepository.findAll(pageable);
        return carSpecifications.getTotalElements() > 0 ? new ResponseEntity<>(carSpecifications, HttpStatus.OK)
                : new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/specifications/{id}")
    public ResponseEntity get(@PathVariable(value = "id") Long specId) {
        CarSpecification specification = carSpecificationRepository.findById(specId).orElse(null);
        return specification == null ? new ResponseEntity(HttpStatus.BAD_REQUEST) : new ResponseEntity<>(specification, HttpStatus.OK);
    }

    @PostMapping("/specifications")
    public ResponseEntity post(@RequestBody CarSpecification carSpecification) {
        CarSpecification existingSpecification = carSpecificationRepository.findById(carSpecification.getId()).orElse(null);
        if (existingSpecification != null) {
            return new ResponseEntity("Specification for this car already exists", HttpStatus.CONFLICT);
        } else {
            CarSpecification savedSpecification = carSpecificationRepository.save(carSpecification);
            return new ResponseEntity<>(savedSpecification, HttpStatus.CREATED);
        }
    }

    @PutMapping("/specifications/{id}")
    public ResponseEntity update(@RequestBody CarSpecification carSpecification, @PathVariable(name = "id") Long id) {
        CarSpecification existingSpecification = carSpecificationRepository.findById(id).orElse(null);
        if (existingSpecification == null) {
            return new ResponseEntity("Car specification not found", HttpStatus.NOT_FOUND);
        }
        carSpecification.setId(id);
        carSpecificationRepository.save(carSpecification);
        return new ResponseEntity("Resource updated", HttpStatus.NO_CONTENT);
    }


    @DeleteMapping("/specifications/{id}")
    public ResponseEntity delete(@PathVariable(name = "id") Long id) {
        if (carSpecificationRepository.findById(id).isPresent()) {
            carSpecificationRepository.deleteById(id);
            return new ResponseEntity("Specification deleted", HttpStatus.OK);
        } else {
            return new ResponseEntity("Specification does not exist", HttpStatus.NOT_FOUND);
        }
    }
}
