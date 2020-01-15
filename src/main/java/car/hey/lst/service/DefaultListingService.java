package car.hey.lst.service;

import car.hey.lst.model.ListingDO;
import car.hey.lst.repo.ListingRepository;
import car.hey.lst.repo.ListingRepositoryCustom;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * Default implementation of Listing Service
 */
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Transactional
@Service
public class DefaultListingService implements ListingService {

    ListingRepository listingRepository;

    ListingRepositoryCustom listingRepositoryCustom;

    public DefaultListingService(ListingRepository listingRepository,
                                 ListingRepositoryCustom listingRepositoryCustom) {

        this.listingRepository = listingRepository;
        this.listingRepositoryCustom = listingRepositoryCustom;
    }


    /**
     * Updates existing the Listings
     *
     * @param dealerId
     * @param listingDO
     * @return
     */
    @Override
    public ListingDO updateListing(final Long dealerId, final ListingDO listingDO) {

        Optional<ListingDO> listingToUpdate = listingRepository.findListing(dealerId, listingDO.getCode());
        if (listingToUpdate.isPresent()) {
            listingDO.setId(listingToUpdate.get().getId());
        }
        return listingRepository.save(listingDO);
    }


    /**
     * Saving the listings
     *
     * @param dealerId
     * @param listingsDO
     * @return
     */
    @Override
    public Iterable<ListingDO> saveListings(final Long dealerId, final List<ListingDO> listingsDO) {

        Iterator iterator = listingsDO.iterator();
        ListingDO listingDO = null;
        while (iterator.hasNext()) {
            listingDO = (ListingDO) iterator.next();
            if (listingRepository.findListing(dealerId, listingDO.getCode()).isPresent()) {
                updateListing(dealerId, listingDO);
                iterator.remove();
            }
        }

        // Saving the new listing together
        return listingRepository.saveAll(listingsDO);
    }


    /**
     * searching over listings
     *
     * @param queryParams
     * @return
     */
    @Override
    public List<ListingDO> searchListings(Map<String, String> queryParams) {

        return listingRepositoryCustom.findListings(queryParams);
    }

    @Override
    public List<ListingDO> getListings() {

        Iterable<ListingDO> all = listingRepository.findAll();
        final List<ListingDO> listingDOS = new ArrayList<>();
        all.forEach(listingDOS::add);
        return listingDOS;
    }

    @Override
    public List<ListingDO> getListings(final Long dealerId) {

        List<ListingDO> listingsDO = listingRepository.findByDealerId(dealerId);
        return listingsDO;
    }
}


