package car.hey.lst.repo;


import car.hey.lst.model.ListingDO;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ListingRepository extends CrudRepository<ListingDO, Long> {

    @Query("SELECT l FROM ListingDO l WHERE l.code = :code AND l.dealerId = :dealerId ")
    Optional<ListingDO> findListing(@Param("dealerId") final Long dealerId,
                                    @Param("code") final String code);

    @Query("SELECT l FROM ListingDO l WHERE l.dealerId = :dealerId ")
    List<ListingDO> findByDealerId(@Param("dealerId") final Long dealerId);
}
