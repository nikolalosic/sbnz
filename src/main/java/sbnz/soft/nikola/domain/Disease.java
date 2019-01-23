package sbnz.soft.nikola.domain;


import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Disease.
 */
@Entity
@Table(name = "disease")
public class Disease implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "disease_general_symptoms",
               joinColumns = @JoinColumn(name = "diseases_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "general_symptoms_id", referencedColumnName = "id"))
    private Set<Symptom> generalSymptoms = new HashSet<>();

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "disease_specific_symptoms",
               joinColumns = @JoinColumn(name = "diseases_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "specific_symptoms_id", referencedColumnName = "id"))
    private Set<Symptom> specificSymptoms = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Disease name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Symptom> getGeneralSymptoms() {
        return generalSymptoms;
    }

    public Disease generalSymptoms(Set<Symptom> symptoms) {
        this.generalSymptoms = symptoms;
        return this;
    }

    public Disease addGeneralSymptoms(Symptom symptom) {
        this.generalSymptoms.add(symptom);
        return this;
    }

    public Disease removeGeneralSymptoms(Symptom symptom) {
        this.generalSymptoms.remove(symptom);
        return this;
    }

    public void setGeneralSymptoms(Set<Symptom> symptoms) {
        this.generalSymptoms = symptoms;
    }

    public Set<Symptom> getSpecificSymptoms() {
        return specificSymptoms;
    }

    public Disease specificSymptoms(Set<Symptom> symptoms) {
        this.specificSymptoms = symptoms;
        return this;
    }

    public Disease addSpecificSymptoms(Symptom symptom) {
        this.specificSymptoms.add(symptom);
        return this;
    }

    public Disease removeSpecificSymptoms(Symptom symptom) {
        this.specificSymptoms.remove(symptom);
        return this;
    }

    public void setSpecificSymptoms(Set<Symptom> symptoms) {
        this.specificSymptoms = symptoms;
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
        Disease disease = (Disease) o;
        if (disease.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), disease.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Disease{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
