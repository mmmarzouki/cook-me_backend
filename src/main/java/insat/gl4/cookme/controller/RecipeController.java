package insat.gl4.cookme.controller;

import insat.gl4.cookme.services.RecipeService;
import insat.gl4.cookme.models.*;
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
    RecipeService recipeService;

    /**
     * method that returns all the recipes in the database
     * @return list of all the recipes
     */
    @GetMapping(path = "/recipe")
    public Iterable<Recipe> findAll(){
        return recipeRepository.findAll();
    }

    /**
     * returns the details of the recipe whose id is given in the URL
     * @param id: id of the recipe to return
     * @return Recipe whose id is given
     */
    @GetMapping(path = "/recipe/{id}")
    public ResponseEntity<Recipe> findOne(@PathVariable int id){
        Optional<Recipe> recipe =  recipeRepository.findById(id);
        if (recipe.isPresent())
            return ResponseEntity.status(HttpStatus.OK).body(recipe.get());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }

    /**
     * creates a new recipe and saves it in the database
     * @param recipe to add
     * @return recipe after saving in the database
     */
    @PostMapping(path = "/recipe")
    public ResponseEntity<Recipe> create(@RequestBody Recipe recipe){
        return recipeService.save(recipe);
    }

    /**
     * method to modify a recipe
     * @param id : id of the recipe
     * @param recipe: recipe to modify
     * @return the recipe after modification and saving in the database
     */
    @PutMapping(path = "/recipe/{id}")
    public ResponseEntity<Recipe> update(@PathVariable int id, @RequestBody Recipe recipe){
        Optional<Recipe> recipeDb = recipeRepository.findById(id);
        if (!recipeDb.isPresent())
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        return recipeService.save(recipe);
    }

    /**
     * deletes a recipe
     * @param id: id of the recipe to delete
     * @return an empty response, with HTTP status 200 if the delete was successul or HTTP status 203 if the recipe to delete is not found
     */
    @DeleteMapping(path = "/recipe/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id){
        Optional<Recipe> recipe =  recipeRepository.findById(id);
        if (recipe.isPresent())
            return ResponseEntity.status(HttpStatus.OK).build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

}
