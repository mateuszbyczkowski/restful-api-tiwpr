package pl.tiwpr.restapi.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.tiwpr.restapi.model.Customer;
import pl.tiwpr.restapi.repository.CustomerRepository;

import java.util.Optional;

@RestController
@Slf4j
@RequestMapping("customer")
public class CustomerController {
    private CustomerRepository customerRepository;

    public CustomerController(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @GetMapping
    public ResponseEntity get(Pageable pageable) {
        Page<Customer> customers = customerRepository.findAll(pageable);

        if (customers.getTotalElements() > 0) {
            return new ResponseEntity<>(customers, HttpStatus.OK);
        } else {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping
    public ResponseEntity post(@RequestBody Customer customer) {
        Optional<Customer> existingCustomer = customerRepository.findById(customer.getId());
        if (existingCustomer.isPresent()) {
            return new ResponseEntity("Customer already exists", HttpStatus.CONFLICT);
        } else {
            Customer savedCar = customerRepository.save(customer);
            return new ResponseEntity<>(savedCar, HttpStatus.CREATED);
        }
    }

    @PutMapping("{id}")
    public ResponseEntity update(@RequestBody Customer customer, @PathVariable Long id) {
        Optional<Customer> customerOptional = customerRepository.findById(id);
        if (!customerOptional.isPresent()) {
            return new ResponseEntity("Customer not found", HttpStatus.NOT_FOUND);
        }
        customer.setId(id);
        customerRepository.save(customer);
        return new ResponseEntity("Resource updated", HttpStatus.NO_CONTENT);
    }


    @DeleteMapping
    public ResponseEntity delete(Long id) {
        if (customerRepository.findById(id).isPresent()) {
            customerRepository.deleteById(id);
            return new ResponseEntity("Customer deleted", HttpStatus.OK);
        } else {
            return new ResponseEntity("Customer does not exist", HttpStatus.NOT_FOUND);
        }
    }
}
