package insat.gl4.cookme.controller;

import insat.gl4.cookme.models.Recipe;
import insat.gl4.cookme.models.temp.RecipeRated;
import insat.gl4.cookme.repositories.RecipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*")
public class RatingController {

    @Autowired
    RecipeRepository recipeRepository;

    @PostMapping(path = "/recipe/rate")
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
