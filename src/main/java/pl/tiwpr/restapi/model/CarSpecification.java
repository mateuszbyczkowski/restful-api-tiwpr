package pl.tiwpr.restapi.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.persistence.*;
import java.time.Year;
import java.util.List;

@Entity
@Data
public class CarSpecification {
    @Id
    @GeneratedValue
    @JsonProperty
    private Long id;
    @JsonProperty
    private String make;
    @JsonProperty
    private String model;
    @JsonProperty
    private Year yearOfProduction;
    @JsonProperty
    private String color;
    @JsonProperty
    private Integer numberOfSeats;
    @JsonProperty
    @OneToMany
    private List<Car> car;
}
