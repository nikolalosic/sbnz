package sbnz.soft.nikola.web.rest;

import com.codahale.metrics.annotation.Timed;
import sbnz.soft.nikola.service.PatientService;
import sbnz.soft.nikola.web.rest.errors.BadRequestAlertException;
import sbnz.soft.nikola.web.rest.util.HeaderUtil;
import sbnz.soft.nikola.web.rest.util.PaginationUtil;
import sbnz.soft.nikola.service.dto.PatientDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Patient.
 */
@RestController
@RequestMapping("/api")
public class PatientResource {

    private final Logger log = LoggerFactory.getLogger(PatientResource.class);

    private static final String ENTITY_NAME = "patient";

    private final PatientService patientService;

    public PatientResource(PatientService patientService) {
        this.patientService = patientService;
    }

    /**
     * POST  /patients : Create a new patient.
     *
     * @param patientDTO the patientDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new patientDTO, or with status 400 (Bad Request) if the patient has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/patients")
    @Timed
    public ResponseEntity<PatientDTO> createPatient(@RequestBody PatientDTO patientDTO) throws URISyntaxException {
        log.debug("REST request to save Patient : {}", patientDTO);
        if (patientDTO.getId() != null) {
            throw new BadRequestAlertException("A new patient cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PatientDTO result = patientService.save(patientDTO);
        return ResponseEntity.created(new URI("/api/patients/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /patients : Updates an existing patient.
     *
     * @param patientDTO the patientDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated patientDTO,
     * or with status 400 (Bad Request) if the patientDTO is not valid,
     * or with status 500 (Internal Server Error) if the patientDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/patients")
    @Timed
    public ResponseEntity<PatientDTO> updatePatient(@RequestBody PatientDTO patientDTO) throws URISyntaxException {
        log.debug("REST request to update Patient : {}", patientDTO);
        if (patientDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PatientDTO result = patientService.save(patientDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, patientDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /patients : get all the patients.
     *
     * @param pageable the pagination information
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many)
     * @return the ResponseEntity with status 200 (OK) and the list of patients in body
     */
    @GetMapping("/patients")
    @Timed
    public ResponseEntity<List<PatientDTO>> getAllPatients(Pageable pageable, @RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get a page of Patients");
        Page<PatientDTO> page;
        if (eagerload) {
            page = patientService.findAllWithEagerRelationships(pageable);
        } else {
            page = patientService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, String.format("/api/patients?eagerload=%b", eagerload));
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /patients/:id : get the "id" patient.
     *
     * @param id the id of the patientDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the patientDTO, or with status 404 (Not Found)
     */
    @GetMapping("/patients/{id}")
    @Timed
    public ResponseEntity<PatientDTO> getPatient(@PathVariable Long id) {
        log.debug("REST request to get Patient : {}", id);
        Optional<PatientDTO> patientDTO = patientService.findOne(id);
        return ResponseUtil.wrapOrNotFound(patientDTO);
    }

    /**
     * DELETE  /patients/:id : delete the "id" patient.
     *
     * @param id the id of the patientDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/patients/{id}")
    @Timed
    public ResponseEntity<Void> deletePatient(@PathVariable Long id) {
        log.debug("REST request to delete Patient : {}", id);
        patientService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
