package sbnz.soft.nikola.service.impl;

import sbnz.soft.nikola.service.DiagnoseService;
import sbnz.soft.nikola.domain.Diagnose;
import sbnz.soft.nikola.repository.DiagnoseRepository;
import sbnz.soft.nikola.service.dto.DiagnoseDTO;
import sbnz.soft.nikola.service.mapper.DiagnoseMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing Diagnose.
 */
@Service
@Transactional
public class DiagnoseServiceImpl implements DiagnoseService {

    private final Logger log = LoggerFactory.getLogger(DiagnoseServiceImpl.class);

    private final DiagnoseRepository diagnoseRepository;

    private final DiagnoseMapper diagnoseMapper;

    public DiagnoseServiceImpl(DiagnoseRepository diagnoseRepository, DiagnoseMapper diagnoseMapper) {
        this.diagnoseRepository = diagnoseRepository;
        this.diagnoseMapper = diagnoseMapper;
    }

    /**
     * Save a diagnose.
     *
     * @param diagnoseDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public DiagnoseDTO save(DiagnoseDTO diagnoseDTO) {
        log.debug("Request to save Diagnose : {}", diagnoseDTO);
        Diagnose diagnose = diagnoseMapper.toEntity(diagnoseDTO);
        diagnose = diagnoseRepository.save(diagnose);
        return diagnoseMapper.toDto(diagnose);
    }

    /**
     * Get all the diagnoses.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<DiagnoseDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Diagnoses");
        return diagnoseRepository.findAll(pageable)
            .map(diagnoseMapper::toDto);
    }

    /**
     * Get all the Diagnose with eager load of many-to-many relationships.
     *
     * @return the list of entities
     */
    public Page<DiagnoseDTO> findAllWithEagerRelationships(Pageable pageable) {
        return diagnoseRepository.findAllWithEagerRelationships(pageable).map(diagnoseMapper::toDto);
    }
    

    /**
     * Get one diagnose by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<DiagnoseDTO> findOne(Long id) {
        log.debug("Request to get Diagnose : {}", id);
        return diagnoseRepository.findOneWithEagerRelationships(id)
            .map(diagnoseMapper::toDto);
    }

    /**
     * Delete the diagnose by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Diagnose : {}", id);
        diagnoseRepository.deleteById(id);
    }
}
