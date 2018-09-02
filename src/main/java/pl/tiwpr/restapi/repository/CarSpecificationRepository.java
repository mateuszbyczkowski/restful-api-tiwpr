package pl.tiwpr.restapi.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import pl.tiwpr.restapi.model.CarSpecification;

public interface CarSpecificationRepository extends PagingAndSortingRepository<CarSpecification, Long> {
}
