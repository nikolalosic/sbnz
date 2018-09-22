package sbnz.soft.nikola.service;

import sbnz.soft.nikola.service.dto.MedicineDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing Medicine.
 */
public interface MedicineService {

    /**
     * Save a medicine.
     *
     * @param medicineDTO the entity to save
     * @return the persisted entity
     */
    MedicineDTO save(MedicineDTO medicineDTO);

    /**
     * Get all the medicines.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<MedicineDTO> findAll(Pageable pageable);

    /**
     * Get all the Medicine with eager load of many-to-many relationships.
     *
     * @return the list of entities
     */
    Page<MedicineDTO> findAllWithEagerRelationships(Pageable pageable);
    
    /**
     * Get the "id" medicine.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<MedicineDTO> findOne(Long id);

    /**
     * Delete the "id" medicine.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
