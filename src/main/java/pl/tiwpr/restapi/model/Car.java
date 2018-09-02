package pl.tiwpr.restapi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import pl.tiwpr.restapi.model.enums.CarLocation;
import pl.tiwpr.restapi.model.enums.CarState;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "car")
@Getter
@Setter
public class Car {
    @Id
    @GeneratedValue
    private Long id;
    @NotNull
    private String licenseNumber;

    private Date insuranceExpirationTime;

    private Integer kilometersDriven;

    private Boolean damaged;

    private CarState carState = CarState.FREE;
    @NotNull
    private CarLocation carLocation = CarLocation.POZNAN;

    @OneToMany(mappedBy = "car", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<CarRention> carRentions;

    @ManyToOne
    @NotNull
    @JoinColumn(name = "cars")
    private CarSpecification carSpecification;
}
