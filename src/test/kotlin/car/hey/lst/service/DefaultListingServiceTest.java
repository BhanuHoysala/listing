package car.hey.lst.service;

import car.hey.lst.model.ListingDO;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Description;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootTest
class DefaultListingServiceTest {

    @Autowired
    ListingService listingService;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    @Description("Testing save operation")
    void saveListings() {

        long dealerId = 2l;

        // creating data
        ListingDO newListingDo = new ListingDO();
        newListingDo.setDealerId(dealerId);
        newListingDo.setCode("b");
        newListingDo.setMake("mb");

        List<ListingDO> listingDOList = new ArrayList<>();
        listingDOList.add(newListingDo);
        listingService.saveListings(dealerId, listingDOList);

        // asserting the persistence
        ListingDO listingDOPersisted = listingService.getListings(dealerId).get(0);
        Assertions.assertEquals(newListingDo.getDealerId(), listingDOPersisted.getDealerId());
        Assertions.assertEquals("b", listingDOPersisted.getCode());
        Assertions.assertEquals("mb", listingDOPersisted.getMake());
    }

    @Test
    @Description("Testing find all listings by a Dealer")
    void findListings() {

        long dealerId = 3l;
        List<ListingDO> listingDOList = new ArrayList<>();

        // creating data
        ListingDO newListingOne = new ListingDO();
        newListingOne.setDealerId(dealerId);
        newListingOne.setCode("b");
        newListingOne.setMake("mb");
        listingDOList.add(newListingOne);

        // creating data
        ListingDO newListingTwo = new ListingDO();
        newListingTwo.setDealerId(dealerId);
        newListingTwo.setCode("c");
        newListingTwo.setMake("vw");
        listingDOList.add(newListingTwo);

        // creating data
        ListingDO newListingThree = new ListingDO();
        newListingThree.setDealerId(dealerId);
        newListingThree.setCode("d");
        newListingThree.setMake("audi");
        listingDOList.add(newListingThree);

        listingService.saveListings(dealerId, listingDOList);

        Assertions.assertEquals(3, listingService.getListings(dealerId).size());
    }

    @Test
    @Description("Testing search operation")
    void searchListings() {

        long dealerId = 4l;
        List<ListingDO> listingDOList = new ArrayList<>();

        // creating data
        ListingDO newListingOne = new ListingDO();
        newListingOne.setDealerId(dealerId);
        newListingOne.setCode("b");
        newListingOne.setMake("mb");
        listingDOList.add(newListingOne);

        // creating data
        ListingDO newListingTwo = new ListingDO();
        newListingTwo.setDealerId(dealerId);
        newListingTwo.setCode("c");
        newListingTwo.setMake("renault");
        listingDOList.add(newListingTwo);

        // creating data
        ListingDO newListingThree = new ListingDO();
        newListingThree.setDealerId(dealerId);
        newListingThree.setCode("d");
        newListingThree.setMake("audi");
        listingDOList.add(newListingThree);

        listingService.saveListings(dealerId, listingDOList);

        Map<String, String> searchParmas = new HashMap<>();
        searchParmas.put("make", "renault");
        List<ListingDO> listingDOS = listingService.searchListings(searchParmas);

        Assertions.assertEquals(1, listingDOS.size());
    }

    @Test
    @Description("Testing the Listing update operation")
    void updateListing() {

        long dealerId = 1l;

        // creating data
        ListingDO newListingDo = new ListingDO();
        newListingDo.setDealerId(dealerId);
        newListingDo.setCode("a");
        newListingDo.setMake("audi");

        List<ListingDO> listingDOList = new ArrayList<>();
        listingDOList.add(newListingDo);
        listingService.saveListings(dealerId, listingDOList);

        // asserting the persistence
        ListingDO listingDOPersisted = listingService.getListings(dealerId).get(0);
        Assertions.assertEquals(newListingDo.getDealerId(), listingDOPersisted.getDealerId());
        Assertions.assertEquals("a", listingDOPersisted.getCode());
        Assertions.assertEquals("audi", listingDOPersisted.getMake());

        ListingDO listingToUpdate = new ListingDO();
        listingToUpdate.setDealerId(dealerId);
        listingToUpdate.setCode("a");
        listingToUpdate.setMake("vw");

        listingService.updateListing(1l, listingToUpdate);

        // Asserting the Listing update
        ListingDO listingDOPersistedAndUpdated = listingService.getListings(dealerId).get(0);
        Assertions.assertEquals(newListingDo.getDealerId(), listingDOPersistedAndUpdated.getDealerId());
        Assertions.assertEquals("a", listingDOPersistedAndUpdated.getCode());
        Assertions.assertEquals("vw", listingDOPersistedAndUpdated.getMake());

    }
}