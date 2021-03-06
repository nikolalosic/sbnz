package sbnz.soft.nikola.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.springframework.security.access.prepost.PreAuthorize;
import sbnz.soft.nikola.security.AuthoritiesConstants;
import sbnz.soft.nikola.service.SymptomService;
import sbnz.soft.nikola.web.rest.errors.BadRequestAlertException;
import sbnz.soft.nikola.web.rest.util.HeaderUtil;
import sbnz.soft.nikola.web.rest.util.PaginationUtil;
import sbnz.soft.nikola.service.dto.SymptomDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Symptom.
 */
@RestController
@RequestMapping("/api")
public class SymptomResource {

    private final Logger log = LoggerFactory.getLogger(SymptomResource.class);

    private static final String ENTITY_NAME = "symptom";

    private final SymptomService symptomService;

    public SymptomResource(SymptomService symptomService) {
        this.symptomService = symptomService;
    }

    /**
     * POST  /symptoms : Create a new symptom.
     *
     * @param symptomDTO the symptomDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new symptomDTO, or with status 400 (Bad Request) if the symptom has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/symptoms")
    @Timed
    @PreAuthorize("hasRole(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<SymptomDTO> createSymptom(@Valid @RequestBody SymptomDTO symptomDTO) throws URISyntaxException {
        log.debug("REST request to save Symptom : {}", symptomDTO);
        if (symptomDTO.getId() != null) {
            throw new BadRequestAlertException("A new symptom cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SymptomDTO result = symptomService.save(symptomDTO);
        return ResponseEntity.created(new URI("/api/symptoms/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /symptoms : Updates an existing symptom.
     *
     * @param symptomDTO the symptomDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated symptomDTO,
     * or with status 400 (Bad Request) if the symptomDTO is not valid,
     * or with status 500 (Internal Server Error) if the symptomDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/symptoms")
    @Timed
    @PreAuthorize("hasRole(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<SymptomDTO> updateSymptom(@Valid @RequestBody SymptomDTO symptomDTO) throws URISyntaxException {
        log.debug("REST request to update Symptom : {}", symptomDTO);
        if (symptomDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        SymptomDTO result = symptomService.save(symptomDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, symptomDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /symptoms : get all the symptoms.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of symptoms in body
     */
    @GetMapping("/symptoms")
    @Timed
    public ResponseEntity<List<SymptomDTO>> getAllSymptoms(Pageable pageable) {
        log.debug("REST request to get a page of Symptoms");
        Page<SymptomDTO> page = symptomService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/symptoms");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /symptoms/:id : get the "id" symptom.
     *
     * @param id the id of the symptomDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the symptomDTO, or with status 404 (Not Found)
     */
    @GetMapping("/symptoms/{id}")
    @Timed
    public ResponseEntity<SymptomDTO> getSymptom(@PathVariable Long id) {
        log.debug("REST request to get Symptom : {}", id);
        Optional<SymptomDTO> symptomDTO = symptomService.findOne(id);
        return ResponseUtil.wrapOrNotFound(symptomDTO);
    }

    /**
     * DELETE  /symptoms/:id : delete the "id" symptom.
     *
     * @param id the id of the symptomDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/symptoms/{id}")
    @Timed
    @PreAuthorize("hasRole(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<Void> deleteSymptom(@PathVariable Long id) {
        log.debug("REST request to delete Symptom : {}", id);
        symptomService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
