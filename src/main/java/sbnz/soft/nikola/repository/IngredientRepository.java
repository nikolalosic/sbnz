package sbnz.soft.nikola.repository;

import sbnz.soft.nikola.domain.Ingredient;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Ingredient entity.
 */
@SuppressWarnings("unused")
@Repository
public interface IngredientRepository extends JpaRepository<Ingredient, Long> {

}
