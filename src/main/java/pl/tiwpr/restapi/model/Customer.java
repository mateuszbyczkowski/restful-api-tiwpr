package pl.tiwpr.restapi.model;

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

    @GeneratedValue
    private Long id;

    private String firstName;

    private String lastName;

    private String street;

    private String city;

    private String state;

    private String postalCode;

    private String phoneNumber;

    private String drivingLicenseNumber;

    @OneToMany(mappedBy = "customer")
    private List<CarRention> rentedCars;
}
