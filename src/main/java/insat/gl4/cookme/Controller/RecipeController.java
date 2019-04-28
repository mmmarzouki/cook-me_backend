package insat.gl4.cookme.Controller;

import insat.gl4.cookme.models.Quantity;
import insat.gl4.cookme.models.Recipe;
import insat.gl4.cookme.models.temp.RecipeRated;
import insat.gl4.cookme.repositories.QuantityRepository;
import insat.gl4.cookme.repositories.RecipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@CrossOrigin(origins = "*")
public class RecipeController {

    @Autowired
    RecipeRepository recipeRepository;

    @Autowired
    QuantityRepository quantityRepository;

    @GetMapping(path = "/recipe")
    public Iterable<Recipe> findAll(){
        return recipeRepository.findAll();
    }

    @GetMapping(path = "/recipe/{id}")
    public ResponseEntity<Recipe> findOne(@PathVariable int id){
        Optional<Recipe> recipe =  recipeRepository.findById(id);
        if (recipe.isPresent())
            return ResponseEntity.status(HttpStatus.OK).body(recipe.get());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }

    @PostMapping(path = "/recipe")
    public ResponseEntity<Recipe> create(@RequestBody Recipe recipe){
        for (Quantity quantity: recipe.getQuantities()) {
            quantityRepository.save(quantity);
        }
        recipeRepository.save(recipe);
        return ResponseEntity.status(HttpStatus.OK).body(recipe);
    }

    @PutMapping(path = "/recipe/rate")
    public ResponseEntity<Recipe> rate(@RequestBody RecipeRated recipeRated){
        Recipe recipe = recipeRated.getRecipe();
        float rate = recipeRated.getScore();
        float score = (recipe.getScore()+rate)/(recipe.getNumVotes()+1);
        recipe.setScore(score);
        recipe.setScore(recipe.getScore()+1);
        recipeRepository.save(recipe);
        return ResponseEntity.status(HttpStatus.OK).body(recipe);
    }
}
