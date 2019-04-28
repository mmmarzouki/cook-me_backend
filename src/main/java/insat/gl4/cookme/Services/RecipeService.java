package insat.gl4.cookme.Services;

import insat.gl4.cookme.models.Quantity;
import insat.gl4.cookme.models.Recipe;
import insat.gl4.cookme.models.Step;
import insat.gl4.cookme.repositories.QuantityRepository;
import insat.gl4.cookme.repositories.RecipeRepository;
import insat.gl4.cookme.repositories.StepRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class RecipeService {

    @Autowired
    QuantityRepository quantityRepository;

    @Autowired
    StepRepository stepRepository;

    @Autowired
    RecipeRepository recipeRepository;

    public ResponseEntity<Recipe> save(Recipe recipe) {
        for (Quantity quantity: recipe.getQuantities()) {
            quantityRepository.save(quantity);
        }
        for (Step step: recipe.getSteps()){
            stepRepository.save(step);
        }
        recipeRepository.save(recipe);
        return ResponseEntity.status(HttpStatus.OK).body(recipe);
    }
}