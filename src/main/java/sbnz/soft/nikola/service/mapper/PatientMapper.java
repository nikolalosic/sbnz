package sbnz.soft.nikola.service.mapper;

import sbnz.soft.nikola.domain.*;
import sbnz.soft.nikola.service.dto.PatientDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Patient and its DTO PatientDTO.
 */
@Mapper(componentModel = "spring", uses = {MedicineMapper.class, IngredientMapper.class})
public interface PatientMapper extends EntityMapper<PatientDTO, Patient> {


    @Mapping(target = "diagnoses", ignore = true)
    Patient toEntity(PatientDTO patientDTO);

    default Patient fromId(Long id) {
        if (id == null) {
            return null;
        }
        Patient patient = new Patient();
        patient.setId(id);
        return patient;
    }
}
