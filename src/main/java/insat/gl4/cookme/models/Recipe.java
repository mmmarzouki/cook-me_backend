package insat.gl4.cookme.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Recipe {

    public static final String ENTREE = "entrée";
    public static final String PLAT = "plat";
    public static final String SOUPE = "soupe";
    public static final String SALADE = "salade";
    public static final String DESSERT = "dessert";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "numVotes")
    private int numVotes;

    @Column(name = "score")
    private float score;

    @Column(name = "categorie")
    private String categorie;

    @Column(name = "img")
    private String img;

    @ManyToOne
    private User user;

    @OneToMany(mappedBy = "recipe")
    private List<Quantity> quantities;

    @OneToMany(mappedBy = "recipe")
    private List<Comment> comments;

    @OneToMany(mappedBy = "recipe")
    private List<Step> steps;

}
