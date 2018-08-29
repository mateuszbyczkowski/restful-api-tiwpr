package pl.tiwpr.restapi.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import pl.tiwpr.restapi.model.RentedCar;

public interface RentedCarRepository extends PagingAndSortingRepository<RentedCar, Long> {
}
