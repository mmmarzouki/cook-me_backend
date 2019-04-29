package insat.gl4.cookme.controller;

import insat.gl4.cookme.models.Quantity;
import insat.gl4.cookme.models.Recipe;
import insat.gl4.cookme.models.User;
import insat.gl4.cookme.repositories.RecipeRepository;
import insat.gl4.cookme.repositories.UserRepository;
import insat.gl4.cookme.services.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "*")
public class SearchController {


    @Autowired
    RecipeRepository recipeRepository;

    @Autowired
    RecipeService recipeService;

    @Autowired
    UserRepository userRepository;

    @GetMapping(path = "/recipe/search/user/{id}")
    public ResponseEntity<List<Recipe>> findByUser(@PathVariable int id){
        Optional<User> user = userRepository.findById(id);
        if(!user.isPresent())
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        return ResponseEntity.status(HttpStatus.OK).body(user.get().getRecipes());
    }

    @GetMapping(path = "/recipe/search/name/{name}")
    public ResponseEntity<List<Recipe>> findByName(@PathVariable String name){
        List<Recipe> allRecipes = (List<Recipe>) recipeRepository.findAll();
        List<Recipe> foundRecipes = allRecipes.stream().filter(recipe -> recipe.getName().contains(name)).collect(Collectors.toList());
        return ResponseEntity.status(HttpStatus.OK).body(foundRecipes);
    }
    @PostMapping(path = "/recipe/search/ingredients")
    public ResponseEntity<List<Recipe>> findByIngredients(@RequestBody List<Quantity> quantities){
        List<Recipe> allRecipes = (List<Recipe>) recipeRepository.findAll();
        List<Recipe> foundRecipes = allRecipes.stream().filter(recipe -> recipeService.recipeRespectsQuantities(recipe,quantities)).collect(Collectors.toList());
        return ResponseEntity.status(HttpStatus.OK).body(foundRecipes);
    }
}
