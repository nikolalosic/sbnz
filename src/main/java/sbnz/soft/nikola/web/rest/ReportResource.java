package sbnz.soft.nikola.web.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sbnz.soft.nikola.security.AuthoritiesConstants;
import sbnz.soft.nikola.security.SecurityUtils;
import sbnz.soft.nikola.service.DiagnoseService;
import sbnz.soft.nikola.service.dto.PatientDTO;

import java.util.List;

@RestController
@RequestMapping("/api/reports")
public class ReportResource {

    private final Logger log = LoggerFactory.getLogger(getClass());

    private final DiagnoseService diagnoseService;

    public ReportResource(DiagnoseService diagnoseService) {
        this.diagnoseService = diagnoseService;
    }

    @PreAuthorize("hasRole(\"" + AuthoritiesConstants.DOCTOR + "\")")
    @GetMapping("/chronicsReport")
    public ResponseEntity<List<PatientDTO>> chronicsReport() {
        List<PatientDTO> patients = this.diagnoseService.chronicsReport(SecurityUtils.getCurrentUserLogin().get());
        return new ResponseEntity<>(patients, HttpStatus.OK);
    }

    @PreAuthorize("hasRole(\"" + AuthoritiesConstants.DOCTOR + "\")")
    @GetMapping("/addictsReport")
    public ResponseEntity<List<PatientDTO>> addictsReport() {
        List<PatientDTO> patients = this.diagnoseService.addictsReport(SecurityUtils.getCurrentUserLogin().get());
        return new ResponseEntity<>(patients, HttpStatus.OK);
    }

    @PreAuthorize("hasRole(\"" + AuthoritiesConstants.DOCTOR + "\")")
    @GetMapping("/immunityReport")
    public ResponseEntity<List<PatientDTO>> immunityReport() {
        List<PatientDTO> patients = this.diagnoseService.immunityReport(SecurityUtils.getCurrentUserLogin().get());
        return new ResponseEntity<>(patients, HttpStatus.OK);
    }

}
