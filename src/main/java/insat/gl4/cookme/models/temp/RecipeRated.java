package insat.gl4.cookme.models.temp;


import insat.gl4.cookme.models.Recipe;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RecipeRated {
    Recipe recipe;
    float score;
}
