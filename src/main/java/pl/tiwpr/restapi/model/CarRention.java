package pl.tiwpr.restapi.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Data
public class CarRention {
    @Id
    @GeneratedValue
    private Long id;

    private Date fromDate;

    private Date toDate;

    private String fromLocation;

    private String toLocation;

    private Integer price;

    private Integer discount;

    private String rentalPolicy;

    @ManyToOne
    @NotNull
    @JoinColumn(name = "car_id")
    private Car car;

    @ManyToOne
    @NotNull
    @JoinColumn(name = "carRentions")
    private Customer customer;
}
