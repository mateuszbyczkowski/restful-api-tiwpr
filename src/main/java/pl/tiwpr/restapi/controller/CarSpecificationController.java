package pl.tiwpr.restapi.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.tiwpr.restapi.model.CarSpecification;
import pl.tiwpr.restapi.repository.CarSpecificationRepository;

import java.util.Optional;

@RestController
@Slf4j
@RequestMapping("specification")
public class CarSpecificationController {
    private CarSpecificationRepository carSpecificationRepository;

    public CarSpecificationController(CarSpecificationRepository carSpecificationRepository) {
        this.carSpecificationRepository = carSpecificationRepository;
    }

    @GetMapping
    public ResponseEntity get(Pageable pageable) {
        Page<CarSpecification> carSpecifications = carSpecificationRepository.findAll(pageable);
        if (carSpecifications.getTotalElements() > 0) {
            return new ResponseEntity<>(carSpecifications, HttpStatus.OK);
        } else {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping
    public ResponseEntity post(@RequestBody CarSpecification carSpecification) {
        Optional<CarSpecification> existingSpecification = carSpecificationRepository.findById(carSpecification.getId());
        if (existingSpecification.isPresent()) {
            return new ResponseEntity("Specification for this car already exists", HttpStatus.CONFLICT);
        } else {
            CarSpecification savedSpecification = carSpecificationRepository.save(carSpecification);
            return new ResponseEntity<>(savedSpecification, HttpStatus.CREATED);
        }
    }

    @PutMapping("{id}")
    public ResponseEntity update(@RequestBody CarSpecification carSpecification, @PathVariable Long id) {
        Optional<CarSpecification> carSpecificationOptional = carSpecificationRepository.findById(id);
        if (!carSpecificationOptional.isPresent()) {
            return new ResponseEntity("Car specification not found", HttpStatus.NOT_FOUND);
        }
        carSpecification.setId(id);
        carSpecificationRepository.save(carSpecification);
        return new ResponseEntity("Resource updated", HttpStatus.NO_CONTENT);
    }


    @DeleteMapping
    public ResponseEntity delete(Long id) {
        if (carSpecificationRepository.findById(id).isPresent()) {
            carSpecificationRepository.deleteById(id);
            return new ResponseEntity("Specification deleted", HttpStatus.OK);
        } else {
            return new ResponseEntity("Specification does not exist", HttpStatus.NOT_FOUND);
        }
    }
}
