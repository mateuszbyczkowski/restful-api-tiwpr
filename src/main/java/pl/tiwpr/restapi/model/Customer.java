package pl.tiwpr.restapi.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
@Data
public class Customer {
    @Id
    @JsonProperty
    @GeneratedValue
    private Long id;
    @JsonProperty
    private String firstName;
    @JsonProperty
    private String lastName;
    @JsonProperty
    private String street;
    @JsonProperty
    private String city;
    @JsonProperty
    private String state;
    @JsonProperty
    private String postalCode;
    @JsonProperty
    private String phoneNumber;
    @JsonProperty
    private String drivingLicenseNumber;
    @JsonProperty
    @OneToMany
    private List<RentedCar> rentedCar;
}
