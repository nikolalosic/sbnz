package sbnz.soft.nikola.service.mapper;

import sbnz.soft.nikola.domain.*;
import sbnz.soft.nikola.service.dto.MedicineDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Medicine and its DTO MedicineDTO.
 */
@Mapper(componentModel = "spring", uses = {IngredientMapper.class})
public interface MedicineMapper extends EntityMapper<MedicineDTO, Medicine> {



    default Medicine fromId(Long id) {
        if (id == null) {
            return null;
        }
        Medicine medicine = new Medicine();
        medicine.setId(id);
        return medicine;
    }
}
