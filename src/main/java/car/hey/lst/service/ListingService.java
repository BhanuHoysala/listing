package car.hey.lst.service;

import car.hey.lst.model.ListingDO;

import java.util.List;
import java.util.Map;

public interface ListingService {

    ListingDO updateListing(Long dealerId, ListingDO listingDO);

    Iterable<ListingDO> saveListings(Long dealerId, List<ListingDO> listingsDO);

    List<ListingDO> findListings(Map<String, String> queryParams);
}
