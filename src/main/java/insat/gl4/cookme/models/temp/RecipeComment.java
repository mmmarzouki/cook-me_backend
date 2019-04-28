package insat.gl4.cookme.models.temp;

import insat.gl4.cookme.models.Comment;
import insat.gl4.cookme.models.Recipe;
import insat.gl4.cookme.models.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RecipeComment {
    String comment;
    User user;
    Recipe recipe;
}
