package sbnz.soft.nikola.service;

import sbnz.soft.nikola.domain.Symptom;
import sbnz.soft.nikola.service.dto.DiseaseDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing Disease.
 */
public interface DiseaseService {

    /**
     * Save a disease.
     *
     * @param diseaseDTO the entity to save
     * @return the persisted entity
     */
    DiseaseDTO save(DiseaseDTO diseaseDTO);

    /**
     * Get all the diseases.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<DiseaseDTO> findAll(Pageable pageable);

    /**
     * Get all the Disease with eager load of many-to-many relationships.
     *
     * @return the list of entities
     */
    Page<DiseaseDTO> findAllWithEagerRelationships(Pageable pageable);
    
    /**
     * Get the "id" disease.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<DiseaseDTO> findOne(Long id);

    /**
     * Delete the "id" disease.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    List<Symptom> getSymptomsByDisease(String search, String doctor);
}
