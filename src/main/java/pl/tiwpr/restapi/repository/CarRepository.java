package pl.tiwpr.restapi.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import pl.tiwpr.restapi.model.Car;

public interface CarRepository extends PagingAndSortingRepository<Car, Long> {
}
