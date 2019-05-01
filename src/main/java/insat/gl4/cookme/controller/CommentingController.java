package insat.gl4.cookme.controller;

import insat.gl4.cookme.models.Comment;
import insat.gl4.cookme.models.Recipe;
import insat.gl4.cookme.models.User;
import insat.gl4.cookme.models.temp.RecipeComment;
import insat.gl4.cookme.repositories.CommentRepository;
import insat.gl4.cookme.repositories.RecipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
@CrossOrigin(origins = "*")
public class CommentingController {

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    RecipeRepository recipeRepository;

    /**
     * adds comment to the recipe
     * @param recipeComment temp model containing the recipe and a string representing the new comment
     * @return the recipe after adding the new comment
     */
    @PostMapping(path = "/recipe/comment")
    public ResponseEntity<Recipe> comment(@RequestBody RecipeComment recipeComment){
        Recipe recipe = recipeComment.getRecipe();
        User user = recipeComment.getUser();
        String value = recipeComment.getComment();
        Comment comment = new Comment();

        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        String time = dateFormat.format(date);

        comment.setRecipe(recipe);
        comment.setUser(user);
        comment.setValue(value);
        comment.setDate(time);
        commentRepository.save(comment);
        recipe.getComments().add(comment);
        recipeRepository.save(recipe);
        return ResponseEntity.status(HttpStatus.OK).body(recipe);
    }

}
