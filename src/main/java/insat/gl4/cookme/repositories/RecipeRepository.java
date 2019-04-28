package insat.gl4.cookme.repositories;

import insat.gl4.cookme.models.Recipe;
import org.springframework.data.repository.CrudRepository;

public interface RecipeRepository extends CrudRepository<Recipe,Integer> {
}
