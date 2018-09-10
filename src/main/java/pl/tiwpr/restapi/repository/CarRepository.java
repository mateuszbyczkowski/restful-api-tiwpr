package pl.tiwpr.restapi.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import pl.tiwpr.restapi.model.Car;

import java.util.Optional;

public interface CarRepository extends PagingAndSortingRepository<Car, Long> {
    Optional<Car> findCarByEntityVersion(int entityVersion);
}
