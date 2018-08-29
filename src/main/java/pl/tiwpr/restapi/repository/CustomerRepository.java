package pl.tiwpr.restapi.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import pl.tiwpr.restapi.model.Customer;

public interface CustomerRepository extends PagingAndSortingRepository<Customer, Long> {
}
