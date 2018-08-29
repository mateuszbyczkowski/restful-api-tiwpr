package pl.tiwpr.restapi.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Data
@Table(name = "car")
public class Car {
    @Id
    @JsonProperty
    @GeneratedValue
    private Long id;

    @JsonProperty
    private String licenseNumber;
    @JsonProperty
    private Date insuranceExpirationTime;
    @JsonProperty
    private Integer kilometersDriven;
    @JsonProperty
    private Boolean damaged;

    @OneToMany
    @JsonProperty
    private List<RentedCar> rentedCar;

    @OneToOne
    @JsonProperty
    private CarLocation carLocation;
}
