package insat.gl4.cookme.repositories;

import insat.gl4.cookme.models.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "user", path = "user")
public interface UserRepository extends CrudRepository<User,Integer> {
    User findByUsernameAndPasswd(String username, String passwd);
}
