package sbnz.soft.nikola.service.impl;

import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.rule.QueryResults;
import org.kie.api.runtime.rule.QueryResultsRow;
import sbnz.soft.nikola.domain.Symptom;
import sbnz.soft.nikola.security.SecurityUtils;
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
import sbnz.soft.nikola.service.mapper.SymptomMapper;
import sbnz.soft.nikola.service.util.KieSessionUtil;
import sbnz.soft.nikola.service.util.SortingUtil;
import sbnz.soft.nikola.web.rest.errors.BadRequestAlertException;

import java.util.*;

/**
 * Service Implementation for managing Disease.
 */
@Service
@Transactional
public class DiseaseServiceImpl implements DiseaseService {

    private final Logger log = LoggerFactory.getLogger(DiseaseServiceImpl.class);

    private final DiseaseRepository diseaseRepository;

    private final DiseaseMapper diseaseMapper;
    private final SymptomMapper symptomMapper;
    private final KieSessionUtil kieSessionUtil;

    public DiseaseServiceImpl(DiseaseRepository diseaseRepository, DiseaseMapper diseaseMapper,
                              KieSessionUtil kieSessionUtil,
                              SymptomMapper symptomMapper) {
        this.diseaseRepository = diseaseRepository;
        this.diseaseMapper = diseaseMapper;
        this.kieSessionUtil = kieSessionUtil;
        this.symptomMapper = symptomMapper;
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
        Optional<Disease> disease = diseaseRepository.findOneWithEagerRelationships(id);
        if (!disease.isPresent()) {
            return Optional.empty();
        }

        DiseaseDTO dto = diseaseMapper.toDto(disease.get());
        dto.setSortedSymptoms(symptomMapper.toDto(getSymptomsByDisease(dto.getName(), SecurityUtils.getCurrentUserLogin().get())));
        return Optional.of(dto);
        //  return diseaseRepository.findOneWithEagerRelationships(id)
        //      .map(diseaseMapper::toDto);
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

    @Override
    public List<Symptom> getSymptomsByDisease(String search, String doctor) {

        if (search == null) {
            throw new BadRequestAlertException("Search is empty!", "String", "String");
        }

        Disease disease = this.diseaseRepository.findByName(search);
        if (disease == null) {
            throw new BadRequestAlertException("Disease does not exist!", "Disease", "Disease");
        }

        KieSession session = this.kieSessionUtil.getUserSession(doctor);

        QueryResults results = session.getQueryResults("get Symptoms", disease);

        Map<Symptom, Integer> filter = new HashMap<Symptom, Integer>();


        for (Symptom s : disease.getGeneralSymptoms()) {
            filter.put(s, 200);
        }

        for (Symptom s : disease.getSpecificSymptoms()) {
            filter.put(s, 300);
        }
        Map<Symptom, Integer> sorted = SortingUtil.sortByValue(filter);

        List<Symptom> retval = new ArrayList<>();
        for (Symptom s : sorted.keySet()) {
            retval.add(s);
        }

        return retval;
    }
}
