package sbnz.soft.nikola.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import sbnz.soft.nikola.security.AuthoritiesConstants;
import sbnz.soft.nikola.service.DiseaseService;
import sbnz.soft.nikola.service.dto.DiseaseDTO;
import sbnz.soft.nikola.web.rest.errors.BadRequestAlertException;
import sbnz.soft.nikola.web.rest.util.HeaderUtil;
import sbnz.soft.nikola.web.rest.util.PaginationUtil;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Disease.
 */
@RestController
@RequestMapping("/api")
public class DiseaseResource {

    private final Logger log = LoggerFactory.getLogger(DiseaseResource.class);

    private static final String ENTITY_NAME = "disease";

    private final DiseaseService diseaseService;

    public DiseaseResource(DiseaseService diseaseService
    ) {
        this.diseaseService = diseaseService;
    }

    /**
     * POST  /diseases : Create a new disease.
     *
     * @param diseaseDTO the diseaseDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new diseaseDTO, or with status 400 (Bad Request) if the disease has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/diseases")
    @Timed
    @PreAuthorize("hasRole(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<DiseaseDTO> createDisease(@Valid @RequestBody DiseaseDTO diseaseDTO) throws URISyntaxException {
        log.debug("REST request to save Disease : {}", diseaseDTO);
        if (diseaseDTO.getId() != null) {
            throw new BadRequestAlertException("A new disease cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DiseaseDTO result = diseaseService.save(diseaseDTO);
        return ResponseEntity.created(new URI("/api/diseases/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /diseases : Updates an existing disease.
     *
     * @param diseaseDTO the diseaseDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated diseaseDTO,
     * or with status 400 (Bad Request) if the diseaseDTO is not valid,
     * or with status 500 (Internal Server Error) if the diseaseDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/diseases")
    @Timed
    @PreAuthorize("hasRole(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<DiseaseDTO> updateDisease(@Valid @RequestBody DiseaseDTO diseaseDTO) throws URISyntaxException {
        log.debug("REST request to update Disease : {}", diseaseDTO);
        if (diseaseDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        DiseaseDTO result = diseaseService.save(diseaseDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, diseaseDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /diseases : get all the diseases.
     *
     * @param pageable  the pagination information
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many)
     * @return the ResponseEntity with status 200 (OK) and the list of diseases in body
     */
    @GetMapping("/diseases")
    @Timed
    public ResponseEntity<List<DiseaseDTO>> getAllDiseases(Pageable pageable, @RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get a page of Diseases");
        Page<DiseaseDTO> page;
        if (eagerload) {
            page = diseaseService.findAllWithEagerRelationships(pageable);
        } else {
            page = diseaseService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, String.format("/api/diseases?eagerload=%b", eagerload));
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /diseases/:id : get the "id" disease.
     *
     * @param id the id of the diseaseDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the diseaseDTO, or with status 404 (Not Found)
     */
    @GetMapping("/diseases/{id}")
    @Timed
    public ResponseEntity<DiseaseDTO> getDisease(@PathVariable Long id) {
        log.debug("REST request to get Disease : {}", id);
        Optional<DiseaseDTO> diseaseDTO = diseaseService.findOne(id);
        return ResponseUtil.wrapOrNotFound(diseaseDTO);
    }


    /**
     * DELETE  /diseases/:id : delete the "id" disease.
     *
     * @param id the id of the diseaseDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/diseases/{id}")
    @Timed
    @PreAuthorize("hasRole(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<Void> deleteDisease(@PathVariable Long id) {
        log.debug("REST request to delete Disease : {}", id);
        diseaseService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
