package pl.tiwpr.restapi.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import pl.tiwpr.restapi.model.CarRention;

public interface RentedCarRepository extends PagingAndSortingRepository<CarRention, Long> {
}
