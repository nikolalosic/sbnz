package sbnz.soft.nikola.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Diagnose.
 */
@Entity
@Table(name = "diagnose")
public class Diagnose implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "diagnose_date", nullable = false)
    private Instant diagnoseDate;

    @ManyToOne
    @JsonIgnoreProperties("")
    private Disease disease;

    @ManyToOne
    @JsonIgnoreProperties("")
    private User doctor;

    @ManyToMany
    @JoinTable(name = "diagnose_symptoms",
               joinColumns = @JoinColumn(name = "diagnoses_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "symptoms_id", referencedColumnName = "id"))
    private Set<Symptom> symptoms = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "diagnose_medicines",
               joinColumns = @JoinColumn(name = "diagnoses_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "medicines_id", referencedColumnName = "id"))
    private Set<Medicine> medicines = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("diagnoses")
    private Patient patient;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getDiagnoseDate() {
        return diagnoseDate;
    }

    public Diagnose diagnoseDate(Instant diagnoseDate) {
        this.diagnoseDate = diagnoseDate;
        return this;
    }

    public void setDiagnoseDate(Instant diagnoseDate) {
        this.diagnoseDate = diagnoseDate;
    }

    public Disease getDisease() {
        return disease;
    }

    public Diagnose disease(Disease disease) {
        this.disease = disease;
        return this;
    }

    public void setDisease(Disease disease) {
        this.disease = disease;
    }

    public User getDoctor() {
        return doctor;
    }

    public Diagnose doctor(User user) {
        this.doctor = user;
        return this;
    }

    public void setDoctor(User user) {
        this.doctor = user;
    }

    public Set<Symptom> getSymptoms() {
        return symptoms;
    }

    public Diagnose symptoms(Set<Symptom> symptoms) {
        this.symptoms = symptoms;
        return this;
    }

    public Diagnose addSymptoms(Symptom symptom) {
        this.symptoms.add(symptom);
        return this;
    }

    public Diagnose removeSymptoms(Symptom symptom) {
        this.symptoms.remove(symptom);
        return this;
    }

    public void setSymptoms(Set<Symptom> symptoms) {
        this.symptoms = symptoms;
    }

    public Set<Medicine> getMedicines() {
        return medicines;
    }

    public Diagnose medicines(Set<Medicine> medicines) {
        this.medicines = medicines;
        return this;
    }

    public Diagnose addMedicines(Medicine medicine) {
        this.medicines.add(medicine);
        return this;
    }

    public Diagnose removeMedicines(Medicine medicine) {
        this.medicines.remove(medicine);
        return this;
    }

    public void setMedicines(Set<Medicine> medicines) {
        this.medicines = medicines;
    }

    public Patient getPatient() {
        return patient;
    }

    public Diagnose patient(Patient patient) {
        this.patient = patient;
        return this;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Diagnose diagnose = (Diagnose) o;
        if (diagnose.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), diagnose.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Diagnose{" +
            "id=" + getId() +
            ", diagnoseDate='" + getDiagnoseDate() + "'" +
            "}";
    }
}
