package car.hey.lst.service;

import car.hey.lst.model.ListingDO;

import java.util.List;
import java.util.Map;

public interface ListingService {

    ListingDO updateListing(Long dealerId, ListingDO listingDO);

    Iterable<ListingDO> saveListings(Long dealerId, List<ListingDO> listingsDO);

    List<ListingDO> searchListings(Map<String, String> queryParams);

    List<ListingDO> getListings();

    List<ListingDO> getListings(Long dealerId);
}
