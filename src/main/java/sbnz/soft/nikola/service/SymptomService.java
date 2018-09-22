package sbnz.soft.nikola.service;

import sbnz.soft.nikola.service.dto.SymptomDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing Symptom.
 */
public interface SymptomService {

    /**
     * Save a symptom.
     *
     * @param symptomDTO the entity to save
     * @return the persisted entity
     */
    SymptomDTO save(SymptomDTO symptomDTO);

    /**
     * Get all the symptoms.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<SymptomDTO> findAll(Pageable pageable);


    /**
     * Get the "id" symptom.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<SymptomDTO> findOne(Long id);

    /**
     * Delete the "id" symptom.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
