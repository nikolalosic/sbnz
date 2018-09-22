package sbnz.soft.nikola.repository;

import sbnz.soft.nikola.domain.Disease;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the Disease entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DiseaseRepository extends JpaRepository<Disease, Long> {

    @Query(value = "select distinct disease from Disease disease left join fetch disease.generalSymptoms left join fetch disease.specificSymptoms",
        countQuery = "select count(distinct disease) from Disease disease")
    Page<Disease> findAllWithEagerRelationships(Pageable pageable);

    @Query(value = "select distinct disease from Disease disease left join fetch disease.generalSymptoms left join fetch disease.specificSymptoms")
    List<Disease> findAllWithEagerRelationships();

    @Query("select disease from Disease disease left join fetch disease.generalSymptoms left join fetch disease.specificSymptoms where disease.id =:id")
    Optional<Disease> findOneWithEagerRelationships(@Param("id") Long id);

}
