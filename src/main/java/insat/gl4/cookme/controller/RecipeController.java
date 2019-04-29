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

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    UserRepository userRepository;

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

    @PutMapping(path = "/recipe/comment")
    public ResponseEntity<Recipe> comment(@RequestBody RecipeComment recipeComment){
        Recipe recipe = recipeComment.getRecipe();
        User user = recipeComment.getUser();
        String value = recipeComment.getComment();
        Comment comment = new Comment();
        comment.setRecipe(recipe);
        comment.setUser(user);
        comment.setValue(value);
        commentRepository.save(comment);
        recipe.getComments().add(comment);
        recipeRepository.save(recipe);
        return ResponseEntity.status(HttpStatus.OK).body(recipe);
    }

    @PostMapping(path = "/recipe/{id}")
    public ResponseEntity<Recipe> update(@PathVariable int id, @RequestBody Recipe recipe){
        Optional<Recipe> recipeDb = recipeRepository.findById(id);
        if (!recipeDb.isPresent())
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        return recipeService.save(recipe);
    }

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
