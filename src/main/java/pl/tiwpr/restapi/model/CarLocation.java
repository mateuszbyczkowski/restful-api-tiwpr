package pl.tiwpr.restapi.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class CarLocation {
    @Id
    @GeneratedValue
    @JsonProperty
    private Long id;
    @JsonProperty
    private Long latitude;
    @JsonProperty
    private Long longitude;
    @JsonProperty
    @OneToOne
    private Car car;
}
