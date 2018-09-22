package sbnz.soft.nikola.service.impl;

import sbnz.soft.nikola.service.SymptomService;
import sbnz.soft.nikola.domain.Symptom;
import sbnz.soft.nikola.repository.SymptomRepository;
import sbnz.soft.nikola.service.dto.SymptomDTO;
import sbnz.soft.nikola.service.mapper.SymptomMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing Symptom.
 */
@Service
@Transactional
public class SymptomServiceImpl implements SymptomService {

    private final Logger log = LoggerFactory.getLogger(SymptomServiceImpl.class);

    private final SymptomRepository symptomRepository;

    private final SymptomMapper symptomMapper;

    public SymptomServiceImpl(SymptomRepository symptomRepository, SymptomMapper symptomMapper) {
        this.symptomRepository = symptomRepository;
        this.symptomMapper = symptomMapper;
    }

    /**
     * Save a symptom.
     *
     * @param symptomDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public SymptomDTO save(SymptomDTO symptomDTO) {
        log.debug("Request to save Symptom : {}", symptomDTO);
        Symptom symptom = symptomMapper.toEntity(symptomDTO);
        symptom = symptomRepository.save(symptom);
        return symptomMapper.toDto(symptom);
    }

    /**
     * Get all the symptoms.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<SymptomDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Symptoms");
        return symptomRepository.findAll(pageable)
            .map(symptomMapper::toDto);
    }


    /**
     * Get one symptom by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<SymptomDTO> findOne(Long id) {
        log.debug("Request to get Symptom : {}", id);
        return symptomRepository.findById(id)
            .map(symptomMapper::toDto);
    }

    /**
     * Delete the symptom by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Symptom : {}", id);
        symptomRepository.deleteById(id);
    }
}
