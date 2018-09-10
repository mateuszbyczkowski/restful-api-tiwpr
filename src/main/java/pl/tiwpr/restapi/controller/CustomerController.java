package pl.tiwpr.restapi.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.tiwpr.restapi.model.Customer;
import pl.tiwpr.restapi.repository.CustomerRepository;

@RestController
@Slf4j
public class CustomerController {
    private CustomerRepository customerRepository;

    public CustomerController(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @GetMapping("/customers")
    public ResponseEntity get(Pageable pageable) {
        Page<Customer> customers = customerRepository.findAll(pageable);
        return customers.getTotalElements() > 0 ? new ResponseEntity<>(customers, HttpStatus.OK)
                : new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/customers/{id}")
    public ResponseEntity get(@PathVariable(value = "id") Long carId) {
        Customer customer = customerRepository.findById(carId).orElse(null);
        return customer == null ? new ResponseEntity(HttpStatus.BAD_REQUEST) : new ResponseEntity<>(customer, HttpStatus.OK);
    }

    @PostMapping("/customers")
    public ResponseEntity post(@RequestBody Customer customer) {
        Customer existingCustomer = customerRepository.findById(customer.getId()).orElse(null);
        if (existingCustomer != null) {
            return new ResponseEntity("Customer already exists", HttpStatus.CONFLICT);
        } else {
            Customer savedCar = customerRepository.save(customer);
            return new ResponseEntity<>(savedCar, HttpStatus.CREATED);
        }
    }


    @PostMapping("/customers/excatly-once")
    public ResponseEntity postExcatlyOnce(@RequestBody Customer car) {
        Customer savedCustomer = customerRepository.save(new Customer());
        return new ResponseEntity<>(savedCustomer.getId(), HttpStatus.CREATED);
    }


    @PutMapping("/customers/{id}")
    public ResponseEntity update(@RequestBody Customer customer, @PathVariable(name = "id") Long id) {
        Customer existingCustomer = customerRepository.findById(id).orElse(null);
        if (existingCustomer == null) {
            return new ResponseEntity("Customer not found", HttpStatus.NOT_FOUND);
        }
        customer.setId(id);
        customerRepository.save(customer);
        return new ResponseEntity("Resource updated", HttpStatus.NO_CONTENT);
    }


    @DeleteMapping("/customers/{id}")
    public ResponseEntity delete(@PathVariable(name = "id") Long id) {
        if (customerRepository.findById(id).isPresent()) {
            customerRepository.deleteById(id);
            return new ResponseEntity("Customer deleted", HttpStatus.OK);
        } else {
            return new ResponseEntity("Customer does not exist", HttpStatus.NOT_FOUND);
        }
    }
}
