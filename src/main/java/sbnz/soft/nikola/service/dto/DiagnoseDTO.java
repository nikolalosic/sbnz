package sbnz.soft.nikola.service.dto;

import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the Diagnose entity.
 */
public class DiagnoseDTO implements Serializable {

    private Long id;

    private Date diagnoseDate;

    private Long diseaseId;

    private String diseaseName;

    private Long doctorId;

    private String doctorFirstName;

    private String doctorLastName;

    private Set<SymptomDTO> symptoms = new HashSet<>();

    private Set<MedicineDTO> medicines = new HashSet<>();

    private Long patientId;

    public String getDiseaseName() {
        return diseaseName;
    }

    public void setDiseaseName(String diseaseName) {
        this.diseaseName = diseaseName;
    }

    public String getDoctorFirstName() {
        return doctorFirstName;
    }

    public void setDoctorFirstName(String doctorFirstName) {
        this.doctorFirstName = doctorFirstName;
    }

    public String getDoctorLastName() {
        return doctorLastName;
    }

    public void setDoctorLastName(String doctorLastName) {
        this.doctorLastName = doctorLastName;
    }

    public String getPatientFirstName() {
        return patientFirstName;
    }

    public void setPatientFirstName(String patientFirstName) {
        this.patientFirstName = patientFirstName;
    }

    public String getPatientLastName() {
        return patientLastName;
    }

    public void setPatientLastName(String patientLastName) {
        this.patientLastName = patientLastName;
    }

    private String patientFirstName;

    private String patientLastName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDiagnoseDate() {
        return diagnoseDate;
    }

    public void setDiagnoseDate(Date diagnoseDate) {
        this.diagnoseDate = diagnoseDate;
    }

    public Long getDiseaseId() {
        return diseaseId;
    }

    public void setDiseaseId(Long diseaseId) {
        this.diseaseId = diseaseId;
    }

    public Long getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(Long userId) {
        this.doctorId = userId;
    }

    public Set<SymptomDTO> getSymptoms() {
        return symptoms;
    }

    public void setSymptoms(Set<SymptomDTO> symptoms) {
        this.symptoms = symptoms;
    }

    public Set<MedicineDTO> getMedicines() {
        return medicines;
    }

    public void setMedicines(Set<MedicineDTO> medicines) {
        this.medicines = medicines;
    }

    public Long getPatientId() {
        return patientId;
    }

    public void setPatientId(Long patientId) {
        this.patientId = patientId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        DiagnoseDTO diagnoseDTO = (DiagnoseDTO) o;
        if (diagnoseDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), diagnoseDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "DiagnoseDTO{" +
            "id=" + getId() +
            ", diagnoseDate='" + getDiagnoseDate() + "'" +
            ", disease=" + getDiseaseId() +
            ", doctor=" + getDoctorId() +
            ", patient=" + getPatientId() +
            "}";
    }
}
