package insat.gl4.cookme.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Quantity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int id;

    @ManyToOne
    Ingredient ingredient;

    @Column(name = "value")
    private float value;

    @JsonIgnore
    @ManyToMany(mappedBy = "quantities")
    private ArrayList<Recipe> recipes;
}
