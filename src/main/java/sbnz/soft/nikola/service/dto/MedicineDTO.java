package sbnz.soft.nikola.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;
import sbnz.soft.nikola.domain.enumeration.MedicineType;

/**
 * A DTO for the Medicine entity.
 */
public class MedicineDTO implements Serializable {

    private Long id;

    @NotNull
    private String name;

    @NotNull
    private MedicineType type;

    private Set<IngredientDTO> ingredients = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public MedicineType getType() {
        return type;
    }

    public void setType(MedicineType type) {
        this.type = type;
    }

    public Set<IngredientDTO> getIngredients() {
        return ingredients;
    }

    public void setIngredients(Set<IngredientDTO> ingredients) {
        this.ingredients = ingredients;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        MedicineDTO medicineDTO = (MedicineDTO) o;
        if (medicineDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), medicineDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MedicineDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", type='" + getType() + "'" +
            "}";
    }
}
