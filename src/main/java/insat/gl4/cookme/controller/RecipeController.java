package insat.gl4.cookme.controller;

import insat.gl4.cookme.services.RecipeService;
import insat.gl4.cookme.models.*;
import insat.gl4.cookme.models.temp.RecipeComment;
import insat.gl4.cookme.models.temp.RecipeRated;
import insat.gl4.cookme.repositories.CommentRepository;
import insat.gl4.cookme.repositories.RecipeRepository;
import insat.gl4.cookme.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "*")
public class RecipeController {

    @Autowired
    RecipeRepository recipeRepository;

    @Autowired
    RecipeService recipeService;


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
        return recipeService.save(recipe);
    }

    @PutMapping(path = "/recipe/{id}")
    public ResponseEntity<Recipe> update(@PathVariable int id, @RequestBody Recipe recipe){
        Optional<Recipe> recipeDb = recipeRepository.findById(id);
        if (!recipeDb.isPresent())
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        return recipeService.save(recipe);
    }

}
