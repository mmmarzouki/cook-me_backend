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

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "numVotes")
    private int numVotes;

    @Column(name = "score")
    private float score;

    @ManyToOne
    private User user;

    @OneToMany(mappedBy = "recipe")
    private List<Quantity> quantities;

    @OneToMany(mappedBy = "recipe")
    private List<Comment> comments;

    @OneToMany(mappedBy = "recipe")
    private List<Step> steps;

}
