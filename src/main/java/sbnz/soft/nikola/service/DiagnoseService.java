package sbnz.soft.nikola.service;

import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.rule.QueryResults;
import org.kie.api.runtime.rule.QueryResultsRow;
import sbnz.soft.nikola.domain.*;
import sbnz.soft.nikola.service.dto.DiagnoseDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import sbnz.soft.nikola.service.dto.DiseaseDTO;
import sbnz.soft.nikola.service.dto.PatientDTO;
import sbnz.soft.nikola.service.util.SortingUtil;

import java.util.*;

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

    DiseaseDTO checkForDisease(Set<Symptom> symptoms, String email, Patient patient);

    void addDiagnoseAndCheckAlergies(Diagnose diagnose, Long patientId, String doctor);

    List<PatientDTO> chronicsReport(String doctor);

    List<PatientDTO> addictsReport(String doctor);

    List<PatientDTO> immunityReport(String doctor);

    List<DiseaseDTO> filterBySymptoms(Set<Symptom> symptoms, String doctor);
}
