package sbnz.soft.nikola.service.impl;

import sbnz.soft.nikola.service.MedicineService;
import sbnz.soft.nikola.domain.Medicine;
import sbnz.soft.nikola.repository.MedicineRepository;
import sbnz.soft.nikola.service.dto.MedicineDTO;
import sbnz.soft.nikola.service.mapper.MedicineMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing Medicine.
 */
@Service
@Transactional
public class MedicineServiceImpl implements MedicineService {

    private final Logger log = LoggerFactory.getLogger(MedicineServiceImpl.class);

    private final MedicineRepository medicineRepository;

    private final MedicineMapper medicineMapper;

    public MedicineServiceImpl(MedicineRepository medicineRepository, MedicineMapper medicineMapper) {
        this.medicineRepository = medicineRepository;
        this.medicineMapper = medicineMapper;
    }

    /**
     * Save a medicine.
     *
     * @param medicineDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public MedicineDTO save(MedicineDTO medicineDTO) {
        log.debug("Request to save Medicine : {}", medicineDTO);
        Medicine medicine = medicineMapper.toEntity(medicineDTO);
        medicine = medicineRepository.save(medicine);
        return medicineMapper.toDto(medicine);
    }

    /**
     * Get all the medicines.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<MedicineDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Medicines");
        return medicineRepository.findAll(pageable)
            .map(medicineMapper::toDto);
    }

    /**
     * Get all the Medicine with eager load of many-to-many relationships.
     *
     * @return the list of entities
     */
    public Page<MedicineDTO> findAllWithEagerRelationships(Pageable pageable) {
        return medicineRepository.findAllWithEagerRelationships(pageable).map(medicineMapper::toDto);
    }
    

    /**
     * Get one medicine by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<MedicineDTO> findOne(Long id) {
        log.debug("Request to get Medicine : {}", id);
        return medicineRepository.findOneWithEagerRelationships(id)
            .map(medicineMapper::toDto);
    }

    /**
     * Delete the medicine by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Medicine : {}", id);
        medicineRepository.deleteById(id);
    }
}
