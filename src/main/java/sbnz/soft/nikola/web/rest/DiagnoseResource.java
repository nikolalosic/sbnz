package sbnz.soft.nikola.web.rest;

import com.codahale.metrics.annotation.Timed;
import sbnz.soft.nikola.service.DiagnoseService;
import sbnz.soft.nikola.web.rest.errors.BadRequestAlertException;
import sbnz.soft.nikola.web.rest.util.HeaderUtil;
import sbnz.soft.nikola.web.rest.util.PaginationUtil;
import sbnz.soft.nikola.service.dto.DiagnoseDTO;
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
 * REST controller for managing Diagnose.
 */
@RestController
@RequestMapping("/api")
public class DiagnoseResource {

    private final Logger log = LoggerFactory.getLogger(DiagnoseResource.class);

    private static final String ENTITY_NAME = "diagnose";

    private final DiagnoseService diagnoseService;

    public DiagnoseResource(DiagnoseService diagnoseService) {
        this.diagnoseService = diagnoseService;
    }

    /**
     * POST  /diagnoses : Create a new diagnose.
     *
     * @param diagnoseDTO the diagnoseDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new diagnoseDTO, or with status 400 (Bad Request) if the diagnose has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/diagnoses")
    @Timed
    public ResponseEntity<DiagnoseDTO> createDiagnose(@Valid @RequestBody DiagnoseDTO diagnoseDTO) throws URISyntaxException {
        log.debug("REST request to save Diagnose : {}", diagnoseDTO);
        if (diagnoseDTO.getId() != null) {
            throw new BadRequestAlertException("A new diagnose cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DiagnoseDTO result = diagnoseService.save(diagnoseDTO);
        return ResponseEntity.created(new URI("/api/diagnoses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /diagnoses : Updates an existing diagnose.
     *
     * @param diagnoseDTO the diagnoseDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated diagnoseDTO,
     * or with status 400 (Bad Request) if the diagnoseDTO is not valid,
     * or with status 500 (Internal Server Error) if the diagnoseDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/diagnoses")
    @Timed
    public ResponseEntity<DiagnoseDTO> updateDiagnose(@Valid @RequestBody DiagnoseDTO diagnoseDTO) throws URISyntaxException {
        log.debug("REST request to update Diagnose : {}", diagnoseDTO);
        if (diagnoseDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        DiagnoseDTO result = diagnoseService.save(diagnoseDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, diagnoseDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /diagnoses : get all the diagnoses.
     *
     * @param pageable the pagination information
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many)
     * @return the ResponseEntity with status 200 (OK) and the list of diagnoses in body
     */
    @GetMapping("/diagnoses")
    @Timed
    public ResponseEntity<List<DiagnoseDTO>> getAllDiagnoses(Pageable pageable, @RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get a page of Diagnoses");
        Page<DiagnoseDTO> page;
        if (eagerload) {
            page = diagnoseService.findAllWithEagerRelationships(pageable);
        } else {
            page = diagnoseService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, String.format("/api/diagnoses?eagerload=%b", eagerload));
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /diagnoses/:id : get the "id" diagnose.
     *
     * @param id the id of the diagnoseDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the diagnoseDTO, or with status 404 (Not Found)
     */
    @GetMapping("/diagnoses/{id}")
    @Timed
    public ResponseEntity<DiagnoseDTO> getDiagnose(@PathVariable Long id) {
        log.debug("REST request to get Diagnose : {}", id);
        Optional<DiagnoseDTO> diagnoseDTO = diagnoseService.findOne(id);
        return ResponseUtil.wrapOrNotFound(diagnoseDTO);
    }

    /**
     * DELETE  /diagnoses/:id : delete the "id" diagnose.
     *
     * @param id the id of the diagnoseDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/diagnoses/{id}")
    @Timed
    public ResponseEntity<Void> deleteDiagnose(@PathVariable Long id) {
        log.debug("REST request to delete Diagnose : {}", id);
        diagnoseService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
