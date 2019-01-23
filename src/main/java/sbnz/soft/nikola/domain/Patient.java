package sbnz.soft.nikola.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Patient.
 */
@Entity
@Table(name = "patient")
public class Patient implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @OneToMany(mappedBy = "patient", fetch = FetchType.EAGER)
    private Set<Diagnose> diagnoses = new HashSet<>();

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "patient_allergic_medicines",
               joinColumns = @JoinColumn(name = "patients_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "allergic_medicines_id", referencedColumnName = "id"))
    private Set<Medicine> allergicMedicines = new HashSet<>();

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "patient_allergic_ingredients",
               joinColumns = @JoinColumn(name = "patients_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "allergic_ingredients_id", referencedColumnName = "id"))
    private Set<Ingredient> allergicIngredients = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public Patient firstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Patient lastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Set<Diagnose> getDiagnoses() {
        return diagnoses;
    }

    public Patient diagnoses(Set<Diagnose> diagnoses) {
        this.diagnoses = diagnoses;
        return this;
    }

    public Patient addDiagnosis(Diagnose diagnose) {
        this.diagnoses.add(diagnose);
        diagnose.setPatient(this);
        return this;
    }

    public Patient removeDiagnosis(Diagnose diagnose) {
        this.diagnoses.remove(diagnose);
        diagnose.setPatient(null);
        return this;
    }

    public void setDiagnoses(Set<Diagnose> diagnoses) {
        this.diagnoses = diagnoses;
    }

    public Set<Medicine> getAllergicMedicines() {
        return allergicMedicines;
    }

    public Patient allergicMedicines(Set<Medicine> medicines) {
        this.allergicMedicines = medicines;
        return this;
    }

    public Patient addAllergicMedicines(Medicine medicine) {
        this.allergicMedicines.add(medicine);
        return this;
    }

    public Patient removeAllergicMedicines(Medicine medicine) {
        this.allergicMedicines.remove(medicine);
        return this;
    }

    public void setAllergicMedicines(Set<Medicine> medicines) {
        this.allergicMedicines = medicines;
    }

    public Set<Ingredient> getAllergicIngredients() {
        return allergicIngredients;
    }

    public Patient allergicIngredients(Set<Ingredient> ingredients) {
        this.allergicIngredients = ingredients;
        return this;
    }

    public Patient addAllergicIngredients(Ingredient ingredient) {
        this.allergicIngredients.add(ingredient);
        return this;
    }

    public Patient removeAllergicIngredients(Ingredient ingredient) {
        this.allergicIngredients.remove(ingredient);
        return this;
    }

    public void setAllergicIngredients(Set<Ingredient> ingredients) {
        this.allergicIngredients = ingredients;
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
        Patient patient = (Patient) o;
        if (patient.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), patient.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Patient{" +
            "id=" + getId() +
            ", firstName='" + getFirstName() + "'" +
            ", lastName='" + getLastName() + "'" +
            "}";
    }
}
