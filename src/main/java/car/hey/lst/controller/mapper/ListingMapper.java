package car.hey.lst.controller.mapper;

import car.hey.lst.contract.ListingDTO;
import car.hey.lst.model.ListingDO;

import java.util.List;
import java.util.stream.Collectors;

public class ListingMapper {

    public static ListingDO makeListingsDO(ListingDTO listingDTO) {

        return ListingDO.builder()
                .code(listingDTO.getCode().toLowerCase())
                .make(listingDTO.getMake().toLowerCase())
                .model(listingDTO.getModel().toLowerCase())
                .kW(listingDTO.getKW())
                .year(listingDTO.getYear())
                .color(listingDTO.getColor())
                .price(listingDTO.getPrice())
                .build();
    }

    public static List<ListingDO> makeListingsDO(List<ListingDTO> listingDTO) {

        return listingDTO.stream().map(ListingMapper::makeListingsDO).collect(Collectors.toList());
    }

    public static ListingDTO makeListingsDTO(ListingDO listingDO) {

        return ListingDTO.builder()
                .code(listingDO.getCode())
                .make(listingDO.getMake())
                .model(listingDO.getModel())
                .kW(listingDO.getKW())
                .year(listingDO.getYear())
                .color(listingDO.getColor())
                .price(listingDO.getPrice())
                .build();
    }

    public static List<ListingDTO> makeListingsDTO(List<ListingDO> listingDO) {

        return listingDO.stream().map(ListingMapper::makeListingsDTO).collect(Collectors.toList());
    }
}