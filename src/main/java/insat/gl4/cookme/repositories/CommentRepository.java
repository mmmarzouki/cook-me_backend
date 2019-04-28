package insat.gl4.cookme.repositories;

import insat.gl4.cookme.models.Comment;
import org.springframework.data.repository.CrudRepository;

public interface CommentRepository extends CrudRepository<Comment,Integer> {
}
