package pl.tiwpr.restapi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.Year;
import java.util.List;

@Entity
@Data
public class CarSpecification {
    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    private String make;

    @NotNull
    private String model;

    @NotNull
    private Year yearOfProduction;

    @NotNull
    private String color;

    @NotNull
    private Integer numberOfSeats;

    @OneToMany(mappedBy = "carSpecification", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Car> cars;
}
