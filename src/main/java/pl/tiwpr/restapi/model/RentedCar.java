package pl.tiwpr.restapi.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
public class RentedCar {
    @Id
    @GeneratedValue
    @JsonProperty
    private Long id;
    @JsonProperty
    private Date fromDate;
    @JsonProperty
    private Date toDate;
    @JsonProperty
    private String fromLocation;
    @JsonProperty
    private String toLocation;
    @JsonProperty
    private Integer price;
    @JsonProperty
    private Integer discount;
    @JsonProperty
    private String rentalPolicy;
    @JsonProperty
    @ManyToOne
    @JoinColumn(name="car_id")
    private Car cars;
    @JsonProperty
    @ManyToOne
    private Customer customers;
}
