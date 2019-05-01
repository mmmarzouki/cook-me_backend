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

import java.util.ArrayList;
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

    /**
     * find all the recipes of the user whose name is given
     * @param name: name of the user to find the recipes
     * @return list of recipes of the given user
     */
    @GetMapping(path = "/recipe/search/user/{name}")
    public ResponseEntity<List<Recipe>> findByUser(@PathVariable String name){
        User user = userRepository.findByName(name);
        if(user == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ArrayList<>());
        return ResponseEntity.status(HttpStatus.OK).body(user.getRecipes());
    }

    /**
     * find all the recipes whose name contain a certain name
     * @param name: name of the recipe
     * @return list of recipes that containts the given name
     */
    @GetMapping(path = "/recipe/search/name/{name}")
    public ResponseEntity<List<Recipe>> findByName(@PathVariable String name){
        List<Recipe> allRecipes = (List<Recipe>) recipeRepository.findAll();
        List<Recipe> foundRecipes = allRecipes.stream().filter(recipe -> recipe.getName().contains(name)).collect(Collectors.toList());
        return ResponseEntity.status(HttpStatus.OK).body(foundRecipes);
    }

    /**
     * find all the recipes that can be coocked by the user of the webApp
     * @param quantities: list of quantities available to the user of the webApp
     * @return list of recipes
     */
    @PostMapping(path = "/recipe/search/ingredients")
    public ResponseEntity<List<Recipe>> findByIngredients(@RequestBody List<Quantity> quantities){
        List<Recipe> allRecipes = (List<Recipe>) recipeRepository.findAll();
        List<Recipe> foundRecipes = allRecipes.stream().filter(recipe -> recipeService.recipeRespectsQuantities(recipe,quantities)).collect(Collectors.toList());
        return ResponseEntity.status(HttpStatus.OK).body(foundRecipes);
    }
}
