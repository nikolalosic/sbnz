package sbnz.soft.nikola.service.mapper;

import sbnz.soft.nikola.domain.*;
import sbnz.soft.nikola.service.dto.DiagnoseDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Diagnose and its DTO DiagnoseDTO.
 */
@Mapper(componentModel = "spring", uses = {DiseaseMapper.class, UserMapper.class, SymptomMapper.class, MedicineMapper.class, PatientMapper.class})
public interface DiagnoseMapper extends EntityMapper<DiagnoseDTO, Diagnose> {

    @Mapping(source = "disease.id", target = "diseaseId")
    @Mapping(source = "doctor.id", target = "doctorId")
    @Mapping(source = "patient.id", target = "patientId")
    DiagnoseDTO toDto(Diagnose diagnose);

    @Mapping(source = "diseaseId", target = "disease")
    @Mapping(source = "doctorId", target = "doctor")
    @Mapping(source = "patientId", target = "patient")
    Diagnose toEntity(DiagnoseDTO diagnoseDTO);

    default Diagnose fromId(Long id) {
        if (id == null) {
            return null;
        }
        Diagnose diagnose = new Diagnose();
        diagnose.setId(id);
        return diagnose;
    }
}
