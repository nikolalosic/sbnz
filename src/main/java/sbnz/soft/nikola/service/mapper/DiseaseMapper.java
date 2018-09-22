package sbnz.soft.nikola.service.mapper;

import sbnz.soft.nikola.domain.*;
import sbnz.soft.nikola.service.dto.DiseaseDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Disease and its DTO DiseaseDTO.
 */
@Mapper(componentModel = "spring", uses = {SymptomMapper.class})
public interface DiseaseMapper extends EntityMapper<DiseaseDTO, Disease> {



    default Disease fromId(Long id) {
        if (id == null) {
            return null;
        }
        Disease disease = new Disease();
        disease.setId(id);
        return disease;
    }
}
