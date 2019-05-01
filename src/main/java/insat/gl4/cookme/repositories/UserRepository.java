package insat.gl4.cookme.repositories;

import insat.gl4.cookme.models.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User,Integer> {
    User findByEmailAndPassword(String email, String password);
    User findByName(String name);
}
