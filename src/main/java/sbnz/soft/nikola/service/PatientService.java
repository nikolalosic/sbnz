package sbnz.soft.nikola.service;

import sbnz.soft.nikola.service.dto.PatientDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing Patient.
 */
public interface PatientService {

    /**
     * Save a patient.
     *
     * @param patientDTO the entity to save
     * @return the persisted entity
     */
    PatientDTO save(PatientDTO patientDTO);

    /**
     * Get all the patients.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<PatientDTO> findAll(Pageable pageable);

    /**
     * Get all the Patient with eager load of many-to-many relationships.
     *
     * @return the list of entities
     */
    Page<PatientDTO> findAllWithEagerRelationships(Pageable pageable);
    
    /**
     * Get the "id" patient.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<PatientDTO> findOne(Long id);

    /**
     * Delete the "id" patient.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
