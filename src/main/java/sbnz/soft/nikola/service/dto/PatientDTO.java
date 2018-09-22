package sbnz.soft.nikola.service.dto;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the Patient entity.
 */
public class PatientDTO implements Serializable {

    private Long id;

    private String firstName;

    private String lastName;

    private Set<MedicineDTO> allergicMedicines = new HashSet<>();

    private Set<IngredientDTO> allergicIngredients = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Set<MedicineDTO> getAllergicMedicines() {
        return allergicMedicines;
    }

    public void setAllergicMedicines(Set<MedicineDTO> medicines) {
        this.allergicMedicines = medicines;
    }

    public Set<IngredientDTO> getAllergicIngredients() {
        return allergicIngredients;
    }

    public void setAllergicIngredients(Set<IngredientDTO> ingredients) {
        this.allergicIngredients = ingredients;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PatientDTO patientDTO = (PatientDTO) o;
        if (patientDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), patientDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PatientDTO{" +
            "id=" + getId() +
            ", firstName='" + getFirstName() + "'" +
            ", lastName='" + getLastName() + "'" +
            "}";
    }
}
