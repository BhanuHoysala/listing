package car.hey.lst.controller.mapper;

import car.hey.lst.contract.ListingDTO;
import car.hey.lst.model.ListingDO;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Utility methods to convert Listing DTO to DO and vice versa
 */
public class ListingMapper {

    public static ListingDO makeListingsDO(Long dealerId, ListingDTO listingDTO) {

        return ListingDO.builder()
                .code(listingDTO.getCode().toLowerCase())
                .dealerId(dealerId)
                .make(listingDTO.getMake().toLowerCase())
                .model(listingDTO.getModel().toLowerCase())
                .kW(listingDTO.getKW())
                .year(listingDTO.getYear())
                .color(listingDTO.getColor())
                .price(listingDTO.getPrice())
                .build();
    }

    public static List<ListingDO> makeListingsDO(Long dealerId, List<ListingDTO> listingDTO) {

        final List<ListingDO> listingDOS = new ArrayList<>();
        listingDTO.forEach(l ->
                listingDOS.add(makeListingsDO(dealerId, l))
        );
        return listingDOS;
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