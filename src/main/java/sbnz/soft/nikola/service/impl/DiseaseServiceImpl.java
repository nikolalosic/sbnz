package sbnz.soft.nikola.service.impl;

import sbnz.soft.nikola.service.DiseaseService;
import sbnz.soft.nikola.domain.Disease;
import sbnz.soft.nikola.repository.DiseaseRepository;
import sbnz.soft.nikola.service.dto.DiseaseDTO;
import sbnz.soft.nikola.service.mapper.DiseaseMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing Disease.
 */
@Service
@Transactional
public class DiseaseServiceImpl implements DiseaseService {

    private final Logger log = LoggerFactory.getLogger(DiseaseServiceImpl.class);

    private final DiseaseRepository diseaseRepository;

    private final DiseaseMapper diseaseMapper;

    public DiseaseServiceImpl(DiseaseRepository diseaseRepository, DiseaseMapper diseaseMapper) {
        this.diseaseRepository = diseaseRepository;
        this.diseaseMapper = diseaseMapper;
    }

    /**
     * Save a disease.
     *
     * @param diseaseDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public DiseaseDTO save(DiseaseDTO diseaseDTO) {
        log.debug("Request to save Disease : {}", diseaseDTO);
        Disease disease = diseaseMapper.toEntity(diseaseDTO);
        disease = diseaseRepository.save(disease);
        return diseaseMapper.toDto(disease);
    }

    /**
     * Get all the diseases.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<DiseaseDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Diseases");
        return diseaseRepository.findAll(pageable)
            .map(diseaseMapper::toDto);
    }

    /**
     * Get all the Disease with eager load of many-to-many relationships.
     *
     * @return the list of entities
     */
    public Page<DiseaseDTO> findAllWithEagerRelationships(Pageable pageable) {
        return diseaseRepository.findAllWithEagerRelationships(pageable).map(diseaseMapper::toDto);
    }
    

    /**
     * Get one disease by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<DiseaseDTO> findOne(Long id) {
        log.debug("Request to get Disease : {}", id);
        return diseaseRepository.findOneWithEagerRelationships(id)
            .map(diseaseMapper::toDto);
    }

    /**
     * Delete the disease by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Disease : {}", id);
        diseaseRepository.deleteById(id);
    }
}
