package sbnz.soft.nikola.service;

import sbnz.soft.nikola.service.dto.DiagnoseDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing Diagnose.
 */
public interface DiagnoseService {

    /**
     * Save a diagnose.
     *
     * @param diagnoseDTO the entity to save
     * @return the persisted entity
     */
    DiagnoseDTO save(DiagnoseDTO diagnoseDTO);

    /**
     * Get all the diagnoses.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<DiagnoseDTO> findAll(Pageable pageable);

    /**
     * Get all the Diagnose with eager load of many-to-many relationships.
     *
     * @return the list of entities
     */
    Page<DiagnoseDTO> findAllWithEagerRelationships(Pageable pageable);
    
    /**
     * Get the "id" diagnose.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<DiagnoseDTO> findOne(Long id);

    /**
     * Delete the "id" diagnose.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
