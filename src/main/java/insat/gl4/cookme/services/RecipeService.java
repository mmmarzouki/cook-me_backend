package insat.gl4.cookme.services;

import insat.gl4.cookme.models.Ingredient;
import insat.gl4.cookme.models.Quantity;
import insat.gl4.cookme.models.Recipe;
import insat.gl4.cookme.models.Step;
import insat.gl4.cookme.repositories.IngredientRepository;
import insat.gl4.cookme.repositories.QuantityRepository;
import insat.gl4.cookme.repositories.RecipeRepository;
import insat.gl4.cookme.repositories.StepRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecipeService {

    @Autowired
    QuantityRepository quantityRepository;

    @Autowired
    StepRepository stepRepository;

    @Autowired
    RecipeRepository recipeRepository;

    @Autowired
    QuantityService quantityService;

    @Autowired
    IngredientRepository ingredientRepository;

    /**
     * the method saves the quantities and the steps before saving the recipe model
     * @param recipe: Recipe to save
     * @return recipe after saving in the databse ( with id )
     */
    public ResponseEntity<Recipe> save(Recipe recipe) {
        for (Quantity quantity: recipe.getQuantities()) {
            ingredientRepository.save(quantity.getIngredient());
            quantityRepository.save(quantity);
        }
        for (Step step: recipe.getSteps()){
            stepRepository.save(step);
        }
        recipeRepository.save(recipe);
        return ResponseEntity.status(HttpStatus.OK).body(recipe);
    }

    /**
     * checks if the recipe respects the quantities given
     * @param recipe: recipe to check
     * @param quantities: List of quantities
     * @return boolean indicating wether the recipe respects the given quantities
     */
    public boolean recipeRespectsQuantities(Recipe recipe, List<Quantity> quantities){
        for(Quantity quantityInRecipe: recipe.getQuantities()){
            Quantity quantity = quantityService.getQuantityOfSameIngredient(quantityInRecipe,quantities);
            if(quantity == null)
                return false;
        }
        return true;
    }
}
