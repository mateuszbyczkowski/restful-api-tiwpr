package pl.tiwpr.restapi.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import pl.tiwpr.restapi.model.CarLocation;

public interface CarLocationRepository extends PagingAndSortingRepository<CarLocation, Long> {
}
