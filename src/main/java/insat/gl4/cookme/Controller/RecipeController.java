package insat.gl4.cookme.Controller;

import insat.gl4.cookme.Services.RecipeService;
import insat.gl4.cookme.models.*;
import insat.gl4.cookme.models.temp.RecipeComment;
import insat.gl4.cookme.models.temp.RecipeRated;
import insat.gl4.cookme.repositories.CommentRepository;
import insat.gl4.cookme.repositories.QuantityRepository;
import insat.gl4.cookme.repositories.RecipeRepository;
import insat.gl4.cookme.repositories.StepRepository;
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

    @Autowired
    CommentRepository commentRepository;


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

}
