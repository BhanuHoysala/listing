package car.hey.lst.repo;

import car.hey.lst.model.ListingDO;

import java.util.List;
import java.util.Map;

public interface ListingRepositoryCustom {

    List<ListingDO> findListings(Map<String,String> searchParameters);
}
