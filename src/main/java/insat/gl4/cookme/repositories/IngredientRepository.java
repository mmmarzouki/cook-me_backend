package insat.gl4.cookme.repositories;

import insat.gl4.cookme.models.Ingredient;
import org.springframework.data.repository.CrudRepository;

public interface IngredientRepository extends CrudRepository<Ingredient, Integer> {
}
