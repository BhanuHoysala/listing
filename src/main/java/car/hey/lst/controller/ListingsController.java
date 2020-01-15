package car.hey.lst.controller;

import car.hey.lst.contract.ListingDTO;
import car.hey.lst.controller.mapper.ListingMapper;
import car.hey.lst.model.ListingDO;
import car.hey.lst.service.ListingService;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static car.hey.lst.constants.Constants.PSI_TO_KW_CONVERSION_FACTOR;

/**
 * Listing controller
 */
@Slf4j
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RestController
@RequestMapping("/vehicle_listings/v1")
public class ListingsController {

    ListingService listingService;

    public ListingsController(ListingService listingService) {
        this.listingService = listingService;
    }

    /**
     * Get all the listing from all the dealers
     *
     * @return
     */
    @GetMapping
    public ResponseEntity<List<ListingDTO>> getAllListings() {

        List<ListingDO> listingDOS = listingService.getListings();
        return ResponseEntity.ok(ListingMapper.makeListingsDTO(listingDOS));
    }

    /**
     * Get all the listing from a dealer
     *
     * @return
     */
    @GetMapping("/{dealer_id}")
    public ResponseEntity<List<ListingDTO>> getAllListings(@PathVariable("dealer_id") final Long dealerId) {

        List<ListingDO> listingDOS = listingService.getListings(dealerId);
        return ResponseEntity.ok(ListingMapper.makeListingsDTO(listingDOS));
    }

    /**
     * Post listings as JOSOn data
     *
     * @param dealerId
     * @param listingsDto
     * @return
     */
    @PostMapping("/{dealer_id}")
    public ResponseEntity<String> postListings(@PathVariable("dealer_id") final Long dealerId,
                                               @RequestBody final List<ListingDTO> listingsDto) {

        final List<ListingDO> listings = ListingMapper.makeListingsDO(dealerId, listingsDto);
        listingService.saveListings(dealerId, listings);
        return ResponseEntity.ok().body("Success");
    }

    /**
     * Post listings by uploading the CSV file
     *
     * @param dealerId
     * @param multipart
     * @return
     * @throws Exception
     */
    @PostMapping("/upload_csv/{dealer_id}")
    public ResponseEntity<Map<String, String>> postListings(@PathVariable(value = "dealer_id") final Long dealerId,
                                                            @RequestParam("file") final MultipartFile multipart) throws Exception {

        String line;
        BufferedReader br = new BufferedReader(new InputStreamReader(multipart.getInputStream()));

        final List<ListingDO> listingDOS = new ArrayList<>();
        final Map<String, String> listingsStatus = new HashMap<>();

        br.readLine(); // Skipping the header
        while ((line = br.readLine()) != null) {

            ListingDO listingDO;
            try {
                listingDO = parseListing(line);
                listingDO.setDealerId(dealerId);
                listingDOS.add(listingDO);
                listingsStatus.put(line, "Listed Successfully");
            } catch (ArrayIndexOutOfBoundsException e) {
                log.error("Error in in parsing the code the data - {}", line);
                listingsStatus.put(line, "Unsuccessful listing - Invalid data");
            }
        }
        listingService.saveListings(dealerId, listingDOS);

        return ResponseEntity.ok(listingsStatus);
    }

    /**
     * Search Listings
     *
     * @param queryParams
     * @return
     */
    @GetMapping("/search")
    public ResponseEntity<List<ListingDTO>> searchListings(@RequestParam Map<String, String> queryParams) {

        if (queryParams.isEmpty()) {
            return ResponseEntity.ok(new ArrayList<>());
        }

        List<ListingDO> listingDOS = listingService.searchListings(queryParams);
        List<ListingDTO> listingDTOS = ListingMapper.makeListingsDTO(listingDOS);

        return ResponseEntity.ok(listingDTOS);
    }

    /**
     * Parses the CSV listing data
     *
     * @param line
     * @return
     */
    private static ListingDO parseListing(String line) {

        String[] data = line.split(",");
        ListingDO listingDO = ListingDO.builder()
                .code(data[0])
                .make(data[1].split("/")[0])
                .model(data[1].split("/")[1])
                .kW(Double.parseDouble(data[2]))
                .year(Integer.parseInt(data[3]))
                .color(data[4])
                .price(Integer.parseInt(data[5]))
                .build();

        // converting PSI to KW
        listingDO.setKW(Math.round((listingDO.getKW() * PSI_TO_KW_CONVERSION_FACTOR) * 100.0) / 100.0);

        return listingDO;
    }

}


