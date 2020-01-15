package car.hey.lst.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Entity
public class ListingDO {

    @Id
    @GeneratedValue
    private Long id;

    private Long dealerId;

    private String code;
    private String make;
    private String model;
    private Double kW;
    private Integer year;
    private String color;
    private Integer price;

}

