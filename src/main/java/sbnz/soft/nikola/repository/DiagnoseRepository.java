package sbnz.soft.nikola.repository;

import sbnz.soft.nikola.domain.Diagnose;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the Diagnose entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DiagnoseRepository extends JpaRepository<Diagnose, Long> {

    @Query("select diagnose from Diagnose diagnose where diagnose.doctor.login = ?#{principal.username}")
    List<Diagnose> findByDoctorIsCurrentUser();

    @Query(value = "select distinct diagnose from Diagnose diagnose left join fetch diagnose.symptoms left join fetch diagnose.medicines",
        countQuery = "select count(distinct diagnose) from Diagnose diagnose")
    Page<Diagnose> findAllWithEagerRelationships(Pageable pageable);

    @Query(value = "select distinct diagnose from Diagnose diagnose left join fetch diagnose.symptoms left join fetch diagnose.medicines")
    List<Diagnose> findAllWithEagerRelationships();

    @Query("select diagnose from Diagnose diagnose left join fetch diagnose.symptoms left join fetch diagnose.medicines where diagnose.id =:id")
    Optional<Diagnose> findOneWithEagerRelationships(@Param("id") Long id);

}
