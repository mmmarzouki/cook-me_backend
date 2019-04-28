package insat.gl4.cookme.Controller;

import insat.gl4.cookme.models.Recipe;
import insat.gl4.cookme.repositories.RecipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@CrossOrigin(origins = "*")
public class RecipeController {

    @Autowired
    RecipeRepository recipeRepository;

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
}
