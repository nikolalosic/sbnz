package sbnz.soft.nikola.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.springframework.security.access.prepost.PreAuthorize;
import sbnz.soft.nikola.security.AuthoritiesConstants;
import sbnz.soft.nikola.service.MedicineService;
import sbnz.soft.nikola.web.rest.errors.BadRequestAlertException;
import sbnz.soft.nikola.web.rest.util.HeaderUtil;
import sbnz.soft.nikola.web.rest.util.PaginationUtil;
import sbnz.soft.nikola.service.dto.MedicineDTO;
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
 * REST controller for managing Medicine.
 */
@RestController
@RequestMapping("/api")
public class MedicineResource {

    private final Logger log = LoggerFactory.getLogger(MedicineResource.class);

    private static final String ENTITY_NAME = "medicine";

    private final MedicineService medicineService;

    public MedicineResource(MedicineService medicineService) {
        this.medicineService = medicineService;
    }

    /**
     * POST  /medicines : Create a new medicine.
     *
     * @param medicineDTO the medicineDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new medicineDTO, or with status 400 (Bad Request) if the medicine has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/medicines")
    @Timed
    @PreAuthorize("hasRole(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<MedicineDTO> createMedicine(@Valid @RequestBody MedicineDTO medicineDTO) throws URISyntaxException {
        log.debug("REST request to save Medicine : {}", medicineDTO);
        if (medicineDTO.getId() != null) {
            throw new BadRequestAlertException("A new medicine cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MedicineDTO result = medicineService.save(medicineDTO);
        return ResponseEntity.created(new URI("/api/medicines/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /medicines : Updates an existing medicine.
     *
     * @param medicineDTO the medicineDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated medicineDTO,
     * or with status 400 (Bad Request) if the medicineDTO is not valid,
     * or with status 500 (Internal Server Error) if the medicineDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/medicines")
    @Timed
    @PreAuthorize("hasRole(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<MedicineDTO> updateMedicine(@Valid @RequestBody MedicineDTO medicineDTO) throws URISyntaxException {
        log.debug("REST request to update Medicine : {}", medicineDTO);
        if (medicineDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        MedicineDTO result = medicineService.save(medicineDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, medicineDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /medicines : get all the medicines.
     *
     * @param pageable the pagination information
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many)
     * @return the ResponseEntity with status 200 (OK) and the list of medicines in body
     */
    @GetMapping("/medicines")
    @Timed
    public ResponseEntity<List<MedicineDTO>> getAllMedicines(Pageable pageable, @RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get a page of Medicines");
        Page<MedicineDTO> page;
        if (eagerload) {
            page = medicineService.findAllWithEagerRelationships(pageable);
        } else {
            page = medicineService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, String.format("/api/medicines?eagerload=%b", eagerload));
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /medicines/:id : get the "id" medicine.
     *
     * @param id the id of the medicineDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the medicineDTO, or with status 404 (Not Found)
     */
    @GetMapping("/medicines/{id}")
    @Timed
    public ResponseEntity<MedicineDTO> getMedicine(@PathVariable Long id) {
        log.debug("REST request to get Medicine : {}", id);
        Optional<MedicineDTO> medicineDTO = medicineService.findOne(id);
        return ResponseUtil.wrapOrNotFound(medicineDTO);
    }

    /**
     * DELETE  /medicines/:id : delete the "id" medicine.
     *
     * @param id the id of the medicineDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/medicines/{id}")
    @Timed
    @PreAuthorize("hasRole(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<Void> deleteMedicine(@PathVariable Long id) {
        log.debug("REST request to delete Medicine : {}", id);
        medicineService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
