package insat.gl4.cookme.controller;

import insat.gl4.cookme.models.Article;
import insat.gl4.cookme.models.User;
import insat.gl4.cookme.repositories.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@CrossOrigin(origins = "*")
public class ArticleController {

    @Autowired
    ArticleRepository articleRepository;

    @GetMapping(path = "/article")
    public Iterable<Article> findAll(){
        return this.articleRepository.findAll();
    }

    @GetMapping(path = "/article/{id}")
    public ResponseEntity<Article> findOne(@PathVariable int id){
        Optional<Article> article =  articleRepository.findById(id);
        if (article.isPresent())
            return ResponseEntity.status(HttpStatus.OK).body(article.get());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }
}
