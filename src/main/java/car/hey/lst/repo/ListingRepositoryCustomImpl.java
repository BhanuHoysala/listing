package car.hey.lst.repo;

import car.hey.lst.model.ListingDO;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Custom repository for complex queries
 */
@Repository
public class ListingRepositoryCustomImpl implements ListingRepositoryCustom {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public List<ListingDO> findListings(Map<String, String> searchParameters) {

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<ListingDO> criteriaQuery = criteriaBuilder.createQuery(ListingDO.class);

        Root<ListingDO> listing = criteriaQuery.from(ListingDO.class);

        List<Predicate> predicates = new ArrayList<>();

        if (searchParameters.containsKey("make")) {

            predicates.add(criteriaBuilder.equal(
                    listing.get("make"),
                    searchParameters.get("make").toLowerCase()));
        }

        if (searchParameters.containsKey("model")) {

            predicates.add(criteriaBuilder.equal(
                    listing.get("model"),
                    searchParameters.get("model").toLowerCase()));
        }

        if (searchParameters.containsKey("year")) {

            predicates.add(criteriaBuilder.equal(listing.get("year"),
                    Long.parseLong(searchParameters.get("year"))));
        }

        if (searchParameters.containsKey("color")) {

            predicates.add(criteriaBuilder.equal(listing.get("color"),
                    searchParameters.get("color").toLowerCase()));
        }

        criteriaQuery.where(predicates.toArray(new Predicate[0]));

        TypedQuery<ListingDO> query = entityManager.createQuery(criteriaQuery);
        return query.getResultList();
    }
}
