package car.hey.lst.contract;

import lombok.Builder;
import lombok.Data;

/**
 * Listing DTO
 */
@Data
@Builder
public class ListingDTO {

    private String code;
    private String make;
    private String model;
    private Double kW;
    private Integer year;
    private String color;
    private Integer price;

}