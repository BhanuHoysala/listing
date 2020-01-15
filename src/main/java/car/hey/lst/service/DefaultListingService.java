package car.hey.lst.service;

import car.hey.lst.model.ListingDO;
import car.hey.lst.repo.ListingRepository;
import car.hey.lst.repo.ListingRepositoryCustom;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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

    @Override
    public ListingDO updateListing(final Long dealerId, final ListingDO listingDO) {

        Optional<ListingDO> listingToUpdate = listingRepository.findLisitng(dealerId, listingDO.getCode());
        if (listingToUpdate.isPresent()) {
            listingDO.setId(listingToUpdate.get().getId());
        }
        return listingRepository.save(listingDO);
    }

    @Override
    public Iterable<ListingDO> saveListings(final Long dealerId, final List<ListingDO> listingsDO) {

        Iterator iterator = listingsDO.iterator();
        ListingDO listingDO = null;
        while (iterator.hasNext()) {
            listingDO = (ListingDO) iterator.next();
            if (listingRepository.findLisitng(dealerId, listingDO.getCode()).isPresent()) {
                updateListing(dealerId, listingDO);
                iterator.remove();
            }
        }

        // Saving the new listing together
        return listingRepository.saveAll(listingsDO);
    }

    @Override
    public List<ListingDO> findListings(Map<String, String> queryParams) {

        return listingRepositoryCustom.findListings(queryParams);
    }
}


