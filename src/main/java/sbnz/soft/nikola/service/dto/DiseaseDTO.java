package sbnz.soft.nikola.service.dto;

import org.mvel2.util.Make;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.*;

/**
 * A DTO for the Disease entity.
 */
public class DiseaseDTO implements Serializable {

    private Long id;

    @NotNull
    private String name;

    private Set<SymptomDTO> generalSymptoms = new HashSet<>();

    private Set<SymptomDTO> specificSymptoms = new HashSet<>();

    public List<SymptomDTO> getSortedSymptoms() {
        return sortedSymptoms;
    }

    public void setSortedSymptoms(List<SymptomDTO> sortedSymptoms) {
        this.sortedSymptoms = sortedSymptoms;
    }

    private List<SymptomDTO> sortedSymptoms = new ArrayList<>();

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

    public Set<SymptomDTO> getGeneralSymptoms() {
        return generalSymptoms;
    }

    public void setGeneralSymptoms(Set<SymptomDTO> symptoms) {
        this.generalSymptoms = symptoms;
    }

    public Set<SymptomDTO> getSpecificSymptoms() {
        return specificSymptoms;
    }

    public void setSpecificSymptoms(Set<SymptomDTO> symptoms) {
        this.specificSymptoms = symptoms;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        DiseaseDTO diseaseDTO = (DiseaseDTO) o;
        if (diseaseDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), diseaseDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "DiseaseDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
